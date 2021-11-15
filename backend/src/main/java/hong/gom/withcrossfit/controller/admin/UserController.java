package hong.gom.withcrossfit.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.UserDto;
import hong.gom.withcrossfit.service.UserApiService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class UserController {

	private final UserApiService userService;
	
	// TODO api 테스트
	
	@GetMapping("/user/not-registered")
	public ResponseEntity<List<UserDto>> getNotRegisteredUser() {
		return ResponseEntity.ok().body(userService.getNotRegisteredUserService());
	}

}