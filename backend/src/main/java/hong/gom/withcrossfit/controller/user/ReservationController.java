package hong.gom.withcrossfit.controller.user;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.ReservationStatusDto;
import hong.gom.withcrossfit.dto.ReservationTimeIdDto;
import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.ReservationTime;
import hong.gom.withcrossfit.entity.ReservationTimeRelation;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.response.ResponseDto;
import hong.gom.withcrossfit.service.ReservationService;
import hong.gom.withcrossfit.service.SpUserService;
import hong.gom.withcrossfit.util.Converter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReservationController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ReservationService reservationService;
	private final SpUserService spUserService;
	private final Converter converter;
	
	@GetMapping("/api/reservation")
	public ResponseEntity getReservation(@CookieValue(name = "refresh") String jwt) {
		SpUser user = spUserService.findUserByJwt(jwt);
		
		List<ReservationTime> results = reservationService.getReservation(user);
		
		if (results.isEmpty()) {
			new ResponseEntity(new ResponseDto(404, "예약 시간이 존재하지 않습니다."), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(converter.convertToReservationTimeDtoList(results), HttpStatus.OK);
	}
	
	@GetMapping("/api/reservation/status")
	public ResponseEntity getReservationStatus(@CookieValue(name = "refresh") String jwt) {
		SpUser user = spUserService.findUserByJwt(jwt);
		ReservationStatusDto result = reservationService.getReservationStatus(user); 
		return new ResponseEntity(result, HttpStatus.OK);
	}
	
	@PostMapping("/api/reservation-time-relation")
	public ResponseEntity insertReservationTimeRelation(@CookieValue(name = "refresh") String jwt, @RequestBody ReservationTimeIdDto dto) {
		SpUser user = spUserService.findUserByJwt(jwt);
		Box box = user.getBox();
		LocalDate now = LocalDate.now();
		
		Optional<ReservationTime> reservationTime = reservationService.findByIdService(dto.getReservationTimeId());
		
		int count = reservationService.countByReservationTimeService(reservationTime.get());
		
		if (box.getMaxReservation() <= count) {
			return new ResponseEntity(new ResponseDto(400, "예약인원이 가득 찼습니다."), HttpStatus.BAD_REQUEST);
		}
		
		Optional<ReservationTimeRelation> reservationTimeRelation = reservationService.findByReservationTimeAndUserAndDateService(reservationTime.get(), user, now);
		
		if (reservationTimeRelation.isPresent()) {
			return new ResponseEntity(new ResponseDto(400, "이미 예약하셨습니다."), HttpStatus.BAD_REQUEST);
		} else {
			reservationService.insertReservationTimeRelationService(reservationTime.get(), user, now);
			return new ResponseEntity(new ResponseDto(200, "예약 되었습니다."), HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/api/reservation-time-relation/{reservationTimeId}") 
	public ResponseEntity deleteReservationTimeRelation(@CookieValue(name = "refresh") String jwt, @PathVariable Long reservationTimeId) {
		SpUser user = spUserService.findUserByJwt(jwt);
		reservationService.deleteReservationTimeRelationService(user, reservationTimeId);
		return new ResponseEntity(new ResponseDto(200, "예약이 정상적으로 취소되었습니다."), HttpStatus.OK);
	}
		
}