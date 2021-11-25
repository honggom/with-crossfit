package hong.gom.withcrossfit.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateScheduleSetDto {
	
	private Long scheduleSetId;
	private String day;
	private Long scheduleId;
	
}
