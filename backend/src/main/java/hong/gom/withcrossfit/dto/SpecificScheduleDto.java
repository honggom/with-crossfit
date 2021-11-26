package hong.gom.withcrossfit.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpecificScheduleDto {
	
	private String name;
	
	private String dateStr;
	
	private List<SpecificEachTimeDto> times;
	
}
