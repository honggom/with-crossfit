package hong.gom.withcrossfit.dto;

import java.time.LocalDateTime;

import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.enums.Grade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	
	private Box box;
	
	private Long userId;
	
	private String email;
	
	private String name;
	
	private LocalDateTime createdAt;
	
	private Grade grade;

}
