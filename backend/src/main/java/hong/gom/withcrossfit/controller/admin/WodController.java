package hong.gom.withcrossfit.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.WodDto;
import hong.gom.withcrossfit.entity.Wod;
import hong.gom.withcrossfit.service.WodService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class WodController {
	
	private final WodService wodService;
	
	// TODO wod -> wod dto
	@GetMapping("/wod")
	public ResponseEntity<Page<Wod>> getWod(Pageable pageable) {
		return wodService.getWodService(pageable);
	}
	
	@GetMapping("/wod/{id}")
	public ResponseEntity<WodDto> getWodById(@PathVariable Long id) {
		return wodService.getWodByIdService(id);
	}
	
	@PostMapping("/wod")
	public ResponseEntity insertWod(@CookieValue(name="refresh") String jwt, @RequestBody WodDto wodDto) {
		System.out.println(wodDto.toString());
		return wodService.insertWodService(jwt, wodDto);
	}

}