package hong.gom.withcrossfit.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto {
	
	private int statusCode;
	private String message;

}
