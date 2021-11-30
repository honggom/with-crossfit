package hong.gom.withcrossfit.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.ReservationStatusDto;
import hong.gom.withcrossfit.dto.ReservationTimeDto;
import hong.gom.withcrossfit.dto.ReservationTimeIdDto;
import hong.gom.withcrossfit.service.ReservationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReservationController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ReservationService reservationService;
	
	@GetMapping("/api/reservation")
	public ResponseEntity<List<ReservationTimeDto>> getReservation(@CookieValue(name = "refresh") String jwt) {
		return reservationService.getReservationService(jwt);
	}
	
	@GetMapping("/api/reservation/status")
	public ResponseEntity<ReservationStatusDto> getReservationStatus(@CookieValue(name = "refresh") String jwt) {
		return reservationService.getReservationStatusService(jwt);
	}
	
	@PostMapping("/api/reservation-time-relation")
	public ResponseEntity insertReservationTimeRelation(@CookieValue(name = "refresh") String jwt, @RequestBody ReservationTimeIdDto dto) {
		return reservationService.insertReservationTimeRelationService(jwt, dto.getReservationTimeId());
	}
	
	@DeleteMapping("/api/reservation-time-relation/{reservationTimeId}") 
	public ResponseEntity deleteReservationTimeRelation(@CookieValue(name = "refresh") String jwt, @PathVariable Long reservationTimeId) {
		return reservationService.deleteReservationTimeRelationService(jwt, reservationTimeId);
	}
		
}