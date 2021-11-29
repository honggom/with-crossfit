package hong.gom.withcrossfit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.ReservationTimeDto;
import hong.gom.withcrossfit.entity.Reservation;
import hong.gom.withcrossfit.entity.ReservationTime;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.jwt.TokenUtils;
import hong.gom.withcrossfit.repository.ReservationRepository;
import hong.gom.withcrossfit.repository.ReservationTimeRepository;
import hong.gom.withcrossfit.repository.SpUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {
	
	private final TokenUtils tokenUtils;
	private final ModelMapper modelMapper;
	private final SpUserRepository userRepository;
	private final ReservationRepository reservationRepository;
	private final ReservationTimeRepository reservationTimeRepository;
 	
	public ResponseEntity<List<ReservationTimeDto>> getReservationService(String jwt) {
		String email = tokenUtils.getEmail(jwt);
		SpUser user = userRepository.findByEmail(email);
			
		LocalDate now = LocalDate.now();
		Reservation reservation = reservationRepository.findByDateAndBox(now, user.getBox());
		List<ReservationTime> times = reservationTimeRepository.findByReservation(reservation);
		
		return ResponseEntity.ok().body(times.stream()
									         .map(time -> modelMapper.map(time, ReservationTimeDto.class))
			                                 .collect(Collectors.toList()));
	}
}
