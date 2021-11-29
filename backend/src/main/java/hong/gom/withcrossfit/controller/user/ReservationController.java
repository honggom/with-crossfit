package hong.gom.withcrossfit.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.ReservationTimeDto;
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
	
	
}
