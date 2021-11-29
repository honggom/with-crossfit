package hong.gom.withcrossfit.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationTimeDto {
	
	private Long id;
	private LocalTime start;
	private LocalTime end;
	private int maxReservation;

}
