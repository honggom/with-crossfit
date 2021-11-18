package hong.gom.withcrossfit.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WodDto {
	
	private Long id;
	
    private LocalDate date;
    
    private String title;
    
    private String content;
    
    private LocalDateTime createdAt;
}
