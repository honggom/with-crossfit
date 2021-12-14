package hong.gom.withcrossfit.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.response.ResponseDto;
import hong.gom.withcrossfit.service.SpUserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final SpUserService userService;
	
	@GetMapping("/api/user")
	public ResponseEntity getUser(@CookieValue(name = "refresh") String jwt) {
		SpUser user = userService.findUserByJwt(jwt);
		if (user == null) {
            return new ResponseEntity(new ResponseDto(404, "정보를 불러올 수 없습니다."), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(HttpStatus.OK);
	}
}
