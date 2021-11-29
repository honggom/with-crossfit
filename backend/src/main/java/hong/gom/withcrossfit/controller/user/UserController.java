package hong.gom.withcrossfit.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.UserDto;
import hong.gom.withcrossfit.service.UserApiService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private	final UserApiService userService; 
	
	@GetMapping("/api/user")
	public ResponseEntity<UserDto> getUser(@CookieValue(name = "refresh") String jwt) {
		return userService.getUserService(jwt);
	}
}
