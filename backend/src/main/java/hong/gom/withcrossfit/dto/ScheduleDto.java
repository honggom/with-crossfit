package hong.gom.withcrossfit.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScheduleDto {
	
	private Long id;
	
	private String name;
	
	private List<EachTimeDto> times;
	
}
