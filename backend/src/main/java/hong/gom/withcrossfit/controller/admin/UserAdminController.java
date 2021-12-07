package hong.gom.withcrossfit.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.BoxIdAndUserEmailDto;
import hong.gom.withcrossfit.dto.UserDto;
import hong.gom.withcrossfit.service.UserAdminApiService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/user")
public class UserAdminController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final UserAdminApiService userService;
	
	@GetMapping("/not-registered")
	public ResponseEntity<List<UserDto>> getNotRegisteredUser() {
		return ResponseEntity.ok().body(userService.getNotRegisteredUserService());
	}
	
	@PostMapping("/register")
	public ResponseEntity insertNewBoxToUser(@RequestBody BoxIdAndUserEmailDto boxIdAndUserEmailDto) {
		return userService.insertNewBoxToUser(boxIdAndUserEmailDto);
	}

}