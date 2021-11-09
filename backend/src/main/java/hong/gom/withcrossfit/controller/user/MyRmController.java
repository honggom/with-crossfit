package hong.gom.withcrossfit.controller.user;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.MyRmDto;
import hong.gom.withcrossfit.service.MyRmService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MyRmController {
	
	private final MyRmService myRmService;
	
	// TODO 수정, 삭제
	// myrm 아이디로 변경 또는 삭제 요청시 본인이 맞는지 검증이 필요함
	
	@GetMapping("/api/my-rm")
	public ResponseEntity<List<MyRmDto>> getMyRm(@CookieValue(name="jwt") String jwt) throws IOException {
		return ResponseEntity.ok()
		        .body(myRmService.getMyRmService(jwt));
	}
	
	@PostMapping("/api/my-rm")
	public ResponseEntity insertMyRm(@RequestBody MyRmDto myRmDto,
							 @CookieValue(name="jwt") String jwt) {
		return myRmService.insertMyRmService(myRmDto, jwt);
	}
}