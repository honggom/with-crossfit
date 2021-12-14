package hong.gom.withcrossfit.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpecificScheduleResponseDto {
	
	private Long id;
	
	private String name;
	
	private LocalDate date;
	
	private boolean isDayOff;
	
	private List<SpecificEachTimeDto> times;
	
}
