package hong.gom.withcrossfit.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.WodDto;
import hong.gom.withcrossfit.service.WodService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class WodController {
	
	private final WodService wodService;
	
	@PostMapping("/wod")
	public ResponseEntity insertWod(@CookieValue(name="refresh") String jwt, @RequestBody WodDto wodDto) {
		System.out.println(wodDto.toString());
		return wodService.insertWodService(jwt, wodDto);
	}

}
