package hong.gom.withcrossfit.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.BoxDto;
import hong.gom.withcrossfit.response.ResponseDto;
import hong.gom.withcrossfit.service.BoxService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class BoxController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final BoxService boxService;
	
	private void logging(String message) {
		LOGGER.info("BoxController INFO : " + message);
	}
	
	@GetMapping("/box")
	public ResponseEntity getBox() {
		List<BoxDto> boxes = boxService.getBoxService();
		
		if (boxes.isEmpty()) {
			logging("기본 박스가 존재하지 않음");
			return new ResponseEntity<>(new ResponseDto(404, "기본 박스가 존재하지 않습니다. 개발자에게 문의하세요."), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(boxes, HttpStatus.OK);
		}
	}
	
}