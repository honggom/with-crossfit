package hong.gom.withcrossfit.controller.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<List<MyRmDto>> getMyRm(@CookieValue(name="refresh") String jwt) {
		return ResponseEntity.ok()
		        .body(myRmService.getMyRmService(jwt));
	}
	
	@GetMapping("/api/my-rm/{id}")
	public ResponseEntity<MyRmDto> getMyRmById(@PathVariable Long id) {
		return ResponseEntity.ok()
		        .body(myRmService.getMyRmByIdService(id));
	}
	
	@PutMapping("/api/my-rm")
	public ResponseEntity updateMyRm(@RequestBody MyRmDto myRmDto) {
		return myRmService.updateMyRmService(myRmDto);
	}
	
	@DeleteMapping("/api/my-rm/{id}")
	public ResponseEntity deleteMyRmById(@PathVariable Long id) {
		return myRmService.deleteMyRmByIdService(id);
	}
	
	@PostMapping("/api/my-rm")
	public ResponseEntity insertMyRm(@RequestBody MyRmDto myRmDto,
							 @CookieValue(name="refresh") String jwt) {
		return myRmService.insertMyRmService(myRmDto, jwt);
	}
}