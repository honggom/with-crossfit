package hong.gom.withcrossfit.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.BoxIdAndUserEmailDto;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.response.ResponseDto;
import hong.gom.withcrossfit.service.UserAdminApiService;
import hong.gom.withcrossfit.util.Converter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/user")
public class UserAdminController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final UserAdminApiService userService;
	private final Converter converter;
	
	@GetMapping("/not-registered")
	public ResponseEntity getNotRegisteredUser() {
		List<SpUser> users = userService.getNotRegisteredUserService();
		
		if (users.isEmpty()) {
			new ResponseEntity(new ResponseDto(204, "미등록된 사용자가 존재하지 않습니다."), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity(converter.convertToUserDtoList(users), HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity insertNewBoxToUser(@RequestBody BoxIdAndUserEmailDto boxIdAndUserEmailDto) {
		if (boxIdAndUserEmailDto != null) {
			userService.insertNewBoxToUser(boxIdAndUserEmailDto);
			return new ResponseEntity(new ResponseDto(200, "회원이 정상적으로 등록되었습니다."), HttpStatus.OK);
		}
		logging("insertNewBoxToUser ==> 요청이 유효하지 않음 boxIdAndUserEmailDto : null");
		return new ResponseEntity(new ResponseDto(400, "요청이 유효하지 않습니다."), HttpStatus.BAD_REQUEST); 
	}
	
	private void logging(String message) {
		LOGGER.info("UserAdminController INFO : " + message);
	}

}