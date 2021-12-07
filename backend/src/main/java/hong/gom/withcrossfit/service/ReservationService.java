package hong.gom.withcrossfit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.ReservationStatusDto;
import hong.gom.withcrossfit.dto.ReservationTimeDto;
import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.Reservation;
import hong.gom.withcrossfit.entity.ReservationTime;
import hong.gom.withcrossfit.entity.ReservationTimeRelation;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.jwt.TokenUtil;
import hong.gom.withcrossfit.repository.ReservationRepository;
import hong.gom.withcrossfit.repository.ReservationTimeRelationRepository;
import hong.gom.withcrossfit.repository.ReservationTimeRepository;
import hong.gom.withcrossfit.repository.SpUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {
	
	private final TokenUtil tokenUtils;
	private final ModelMapper modelMapper;
	private final SpUserRepository userRepository;
	private final ReservationRepository reservationRepository;
	private final ReservationTimeRepository reservationTimeRepository;
	private final ReservationTimeRelationRepository reservationTimeRelationRepository;
 	
	public ResponseEntity<List<ReservationTimeDto>> getReservationService(String jwt) {
		String email = tokenUtils.getEmail(jwt);
		SpUser user = userRepository.findByEmail(email);
			
		LocalDate now = LocalDate.now();
		Reservation reservation = reservationRepository.findByDateAndBox(now, user.getBox());
		List<ReservationTime> times = reservationTimeRepository.findByReservation(reservation);
		
		for (ReservationTime time : times) {
			time.setReservationCount(reservationTimeRelationRepository.countByReservationTime(time));
		}
		
		return ResponseEntity.ok().body(times.stream()
									         .map(time -> modelMapper.map(time, ReservationTimeDto.class))
			                                 .collect(Collectors.toList()));
	}
	
	public ResponseEntity<ReservationStatusDto> getReservationStatusService(String jwt) {
		String email = tokenUtils.getEmail(jwt);
		SpUser user = userRepository.findByEmail(email);
		LocalDate now = LocalDate.now();
		
		int count = reservationTimeRelationRepository.countByUserAndDate(user, now);
		
		ReservationStatusDto dto = new ReservationStatusDto();
		
		if (count > 0) {
			dto.setReserved(true);
			ReservationTimeRelation reservationTimeRelation = reservationTimeRelationRepository.findByUserAndDate(user, now);
			ReservationTime reservationTime = reservationTimeRelation.getReservationTime();
			
			dto.setReservationTimeId(reservationTime.getId());
			dto.setStart(reservationTime.getStart());
			dto.setEnd(reservationTime.getEnd());
		} else {
			dto.setReserved(false);
		}
		
		return ResponseEntity.ok().body(dto);
	}
	
	public ResponseEntity insertReservationTimeRelationService(String jwt, Long reservationTimeId) {
		String email = tokenUtils.getEmail(jwt);
		SpUser user = userRepository.findByEmail(email);
		Box box = user.getBox();
		LocalDate now = LocalDate.now();
		
		Optional<ReservationTime> reservationTime = reservationTimeRepository.findById(reservationTimeId);
		
		int count = reservationTimeRelationRepository.countByReservationTime(reservationTime.get());
		
		if (box.getMaxReservation() <= count) {
			return new ResponseEntity("예약 인원이 가득찼습니다.", HttpStatus.BAD_REQUEST);
		}
		
		Optional<ReservationTimeRelation> reservationTimeRelation = reservationTimeRelationRepository
				                                                      .findByReservationTimeAndUserAndDate(reservationTime.get(), user, now);
		
		if (reservationTimeRelation.isPresent()) {
			return new ResponseEntity("이미 예약하셨습니다.", HttpStatus.BAD_REQUEST);
		} else {
			reservationTimeRelationRepository.save(ReservationTimeRelation.builder()
						                                                  .reservationTime(reservationTime.get())
						                                                  .date(now)
					                                                      .user(user)
					                                                      .build());
			return new ResponseEntity(HttpStatus.OK);
		}
	}
	
	public ResponseEntity deleteReservationTimeRelationService(String jwt, Long reservationTimeId) {
		ReservationTime reservationTime = reservationTimeRepository.findById(reservationTimeId).get();
		
		String email = tokenUtils.getEmail(jwt);
		SpUser user = userRepository.findByEmail(email);
		
		LocalDate now = LocalDate.now();
		
		reservationTimeRelationRepository.deleteByReservationTimeAndUserAndDate(reservationTime, user, now);
		
		return new ResponseEntity(HttpStatus.OK);
	}
}
