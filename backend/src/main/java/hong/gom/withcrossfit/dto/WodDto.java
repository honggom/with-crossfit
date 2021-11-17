package hong.gom.withcrossfit.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WodDto {
	
	private Long id;
	
    private LocalDateTime date;
    
    private String content;
}
