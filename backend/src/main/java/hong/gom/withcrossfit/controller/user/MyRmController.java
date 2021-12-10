package hong.gom.withcrossfit.controller.user;

import java.util.List;
import java.util.Optional;

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
import hong.gom.withcrossfit.entity.MyRm;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.response.ResponseDto;
import hong.gom.withcrossfit.service.MyRmService;
import hong.gom.withcrossfit.service.SpUserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MyRmController {
	
	private final MyRmService myRmService;
	private final SpUserService spUserService;
	
	@GetMapping("/api/my-rm")
	public ResponseEntity getMyRm(@CookieValue(name="refresh") String jwt) {
		SpUser user = spUserService.findUserByJwt(jwt);
		List<MyRm> rms = myRmService.getMyRms(user);
		
		if (rms.isEmpty()) {
			return new ResponseEntity(new ResponseDto(200, "RM 기록이 없습니다."), HttpStatus.OK);
		}
		return new ResponseEntity(myRmService.convertToDto(rms), HttpStatus.OK);
	}
	
	@GetMapping("/api/my-rm/{id}")
	public ResponseEntity getMyRmById(@PathVariable Long id) {
		Optional<MyRm> rm = myRmService.getMyRmById(id);
		
		if (rm.isPresent()) {
			return new ResponseEntity(myRmService.convertToDto(rm.get()), HttpStatus.OK);
		}
		return new ResponseEntity(new ResponseDto(404, "RM 기록이 없습니다."), HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/api/my-rm")
	public ResponseEntity updateMyRm(@RequestBody MyRmDto newRmDto) {
		Optional<MyRm> oldRm = myRmService.getMyRmById(newRmDto.getId());
		
		if (oldRm.isPresent()) {
			myRmService.updateMyRm(oldRm.get(), newRmDto);	
			return new ResponseEntity(new ResponseDto(200, "RM이 정상적으로 수정되었습니다."), HttpStatus.OK);
		}
		return new ResponseEntity(new ResponseDto(404, "RM 기록이 없습니다."), HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/api/my-rm/{id}")
	public ResponseEntity deleteMyRmById(@PathVariable Long id) {
		myRmService.deleteMyRmByIdService(id);
		return new ResponseEntity(new ResponseDto(200, "RM이 정상적으로 삭제되었습니다."), HttpStatus.OK);
	}
	
	@PostMapping("/api/my-rm")
	public ResponseEntity insertMyRm(@RequestBody MyRmDto myRmDto,
							         @CookieValue(name="refresh") String jwt) {
		SpUser user = spUserService.findUserByJwt(jwt);
		myRmService.insertMyRmService(user, myRmDto);
		return new ResponseEntity(new ResponseDto(200, "RM이 정상적으로 추가되었습니다."), HttpStatus.OK);
	}
}