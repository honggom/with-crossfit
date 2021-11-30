package hong.gom.withcrossfit.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationStatusDto {

	private boolean reserved;
	private Long reservationTimeId;
	private LocalTime start;
	private LocalTime end;
}
