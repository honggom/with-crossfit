package hong.gom.withcrossfit.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	
	private int status;
	private String message;

}
