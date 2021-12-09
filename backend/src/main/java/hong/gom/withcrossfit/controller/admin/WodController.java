package hong.gom.withcrossfit.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.WodDto;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.entity.Wod;
import hong.gom.withcrossfit.response.ResponseDto;
import hong.gom.withcrossfit.service.SpUserService;
import hong.gom.withcrossfit.service.WodService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class WodController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final WodService wodService;
	private final SpUserService spUserService;
	
	// TODO wod -> wod dto
	@GetMapping("/wod")
	public ResponseEntity getWod(@CookieValue(name="refresh") String jwt, Pageable pageable) {
		Page<Wod> results = wodService.getWodService(jwt, pageable);
		
		if (results.getContent().isEmpty()) {
			return new ResponseEntity(new ResponseDto(204, "작성된 와드가 존재하지 않습니다."), HttpStatus.NO_CONTENT);
		} 
		return new ResponseEntity(results, HttpStatus.OK);
	}
	
	@GetMapping("/wod/{id}")
	public ResponseEntity getWodById(@CookieValue(name="refresh") String jwt, @PathVariable Long id) {
		WodDto result = wodService.getWodByIdService(jwt, id);
		
		if (result != null) {
			return new ResponseEntity(result, HttpStatus.OK);
		}
		return new ResponseEntity(new ResponseDto(404, "와드가 존재하지 않습니다."), HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/wod")
	public ResponseEntity insertWod(@CookieValue(name="refresh") String jwt, @RequestBody WodDto wodDto) {
		SpUser user = spUserService.findUserByJwt(jwt);
		
		if (wodService.isAlreadyExistWodByBoxAndDate(user.getBox(), wodDto.getDate())) {
			return new ResponseEntity(new ResponseDto(400, "해당 날짜에 작성된 와드가 이미 존재합니다."), HttpStatus.BAD_REQUEST);
		}
        wodService.insertWodService(user, wodDto);
        return new ResponseEntity(new ResponseDto(200, "와드가 정상적으로 작성되었습니다."), HttpStatus.OK);
	}
	
	@PutMapping("/wod")
	public ResponseEntity updateWod(@CookieValue(name="refresh") String jwt, @RequestBody WodDto wodDto) {
		SpUser user = spUserService.findUserByJwt(jwt);
		
		if (wodService.isAlreadyExistWodByBoxAndDate(user.getBox(), wodDto.getDate())) {
			return new ResponseEntity(new ResponseDto(400, "해당 날짜에 작성된 와드가 이미 존재합니다."), HttpStatus.BAD_REQUEST);
		}
		wodService.updateWodService(wodDto);
		return new ResponseEntity(new ResponseDto(200, "와드가 정상적으로 수정되었습니다."), HttpStatus.OK);
	}
	
	@DeleteMapping("/wod/{id}")
	public ResponseEntity deleteWodById(@CookieValue(name="refresh") String jwt, @PathVariable Long id) {
		if (wodService.isWriter(jwt, id)) {
			wodService.deleteWodByIdService(id);
			new ResponseEntity(new ResponseDto(200, "와드가 정상적으로 삭제되었습니다."), HttpStatus.OK);
		}
		return new ResponseEntity(new ResponseDto(403, "삭제 권한이 없습니다."), HttpStatus.FORBIDDEN);
	}
	
	private void logging(String message) {
		LOGGER.info("WodController INFO : " + message);
	}

}