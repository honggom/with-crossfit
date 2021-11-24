package hong.gom.withcrossfit.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EachTimeDto {
	
    private LocalTime start;
    
    private LocalTime end;
    
}
