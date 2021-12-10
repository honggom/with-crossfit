package hong.gom.withcrossfit.controller.user;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import hong.gom.withcrossfit.response.ResponseDto;
import hong.gom.withcrossfit.service.MyRmService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MyRmController {
	
	private final MyRmService myRmService;
	
	@GetMapping("/api/my-rm")
	public ResponseEntity getMyRm(@CookieValue(name="refresh") String jwt) {
		List<MyRmDto> results = myRmService.getMyRmService(jwt);
		
		if (results.isEmpty()) {
			new ResponseEntity(new ResponseDto(200, "RM 기록이 없습니다."), HttpStatus.OK);
		}
		return new ResponseEntity(results, HttpStatus.OK);
	}
	
	@GetMapping("/api/my-rm/{id}")
	public ResponseEntity getMyRmById(@PathVariable Long id) {
		MyRmDto result = myRmService.getMyRmByIdService(id);
		
		if (result == null) {
			new ResponseEntity(new ResponseDto(404, "RM 기록이 없습니다."), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(result, HttpStatus.OK);
	}
	
	@PutMapping("/api/my-rm")
	public ResponseEntity updateMyRm(@RequestBody MyRmDto myRmDto) {
		if (myRmDto != null) {
			myRmService.updateMyRmService(myRmDto);
			return new ResponseEntity(new ResponseDto(200, "RM이 정상적으로 수정되었습니다."), HttpStatus.OK);
		} 
		return new ResponseEntity(new ResponseDto(400, "잘못된 요청입니다."), HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/api/my-rm/{id}")
	public ResponseEntity deleteMyRmById(@PathVariable Long id) {
		myRmService.deleteMyRmByIdService(id);
		return new ResponseEntity(new ResponseDto(200, "RM이 정상적으로 삭제되었습니다."), HttpStatus.OK);
	}
	
	@PostMapping("/api/my-rm")
	public ResponseEntity insertMyRm(@RequestBody MyRmDto myRmDto,
							         @CookieValue(name="refresh") String jwt) {
		myRmService.insertMyRmService(myRmDto, jwt);
		return new ResponseEntity(new ResponseDto(200, "RM이 정상적으로 추가되었습니다."), HttpStatus.OK);
	}
}