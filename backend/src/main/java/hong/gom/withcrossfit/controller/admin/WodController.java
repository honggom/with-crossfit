package hong.gom.withcrossfit.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.WodDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class WodController {
	
	@PostMapping("/wod")
	public ResponseEntity insertWod(@RequestBody WodDto wodDto) {
		
		// TODO 해당 날짜에 적힌 wod가 있는지 확인
		
		return null;
	}

}
