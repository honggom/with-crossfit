package hong.gom.withcrossfit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.ReservationStatusDto;
import hong.gom.withcrossfit.dto.ReservationTimeDto;
import hong.gom.withcrossfit.entity.Reservation;
import hong.gom.withcrossfit.entity.ReservationTime;
import hong.gom.withcrossfit.entity.ReservationTimeRelation;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.repository.ReservationRepository;
import hong.gom.withcrossfit.repository.ReservationTimeRelationRepository;
import hong.gom.withcrossfit.repository.ReservationTimeRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {
	
	private final ModelMapper modelMapper;
	private final ReservationRepository reservationRepository;
	private final ReservationTimeRepository reservationTimeRepository;
	private final ReservationTimeRelationRepository reservationTimeRelationRepository;
	private final SpUserService spUserService;
 	
	// TODO 리팩토링
	// 아래 함수 별로 안 좋은 함수 같음
	// 왜냐 ?
	// 1. 의존을 너무 많이 함 (reservationRepository를 3번이나 사용함)
	// 2. 의존을 너무 많이 해서 유닛 테스트 작성이 어려움 (getReservationService의 유닛 테스트 코드를 작성한다고 가정하면
	// Mock을 많이 사용해야 됨..)
	public List<ReservationTimeDto> getReservationService(String jwt) {
		SpUser user = spUserService.findUserByJwt(jwt);
			
		LocalDate now = LocalDate.now();
		Reservation reservation = reservationRepository.findByDateAndBox(now, user.getBox());
		List<ReservationTime> times = reservationTimeRepository.findByReservation(reservation);
		
		for (ReservationTime time : times) {
			time.setReservationCount(reservationTimeRelationRepository.countByReservationTime(time));
		}
		
		return times.stream()
				    .map(time -> modelMapper.map(time, ReservationTimeDto.class))
			        .collect(Collectors.toList());
	}
	
	public ReservationStatusDto getReservationStatusService(String jwt) {
		SpUser user = spUserService.findUserByJwt(jwt);
		
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
		return dto;
	}
	
	public void insertReservationTimeRelationService(ReservationTime reservationTime, SpUser user, LocalDate now) {
        reservationTimeRelationRepository.save(ReservationTimeRelation.builder()
						                                              .reservationTime(reservationTime)
						                                              .date(now)
					                                                  .user(user)
					                                                  .build());
	}
	
	public Optional<ReservationTime> findByIdService(Long reservationTimeId) {
		return reservationTimeRepository.findById(reservationTimeId);
	}
	
	public int countByReservationTimeService(ReservationTime reservationTime) {
		return reservationTimeRelationRepository.countByReservationTime(reservationTime);
	}
	
	public Optional<ReservationTimeRelation> findByReservationTimeAndUserAndDateService(ReservationTime reservationTime, SpUser user, LocalDate now) {
		return reservationTimeRelationRepository.findByReservationTimeAndUserAndDate(reservationTime, user, now);
	}
	
	public void deleteReservationTimeRelationService(String jwt, Long reservationTimeId) {
		ReservationTime reservationTime = reservationTimeRepository.findById(reservationTimeId).get();
		
		SpUser user = spUserService.findUserByJwt(jwt);
		
		LocalDate now = LocalDate.now();
		
		reservationTimeRelationRepository.deleteByReservationTimeAndUserAndDate(reservationTime, user, now);
	}
}
