package hong.gom.withcrossfit.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import hong.gom.withcrossfit.entity.Wod;
import hong.gom.withcrossfit.service.WodService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class WodController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final WodService wodService;
	
	// TODO wod -> wod dto
	@GetMapping("/wod")
	public ResponseEntity<Page<Wod>> getWod(@CookieValue(name="refresh") String jwt, Pageable pageable) {
		return wodService.getWodService(jwt, pageable);
	}
	
	@GetMapping("/wod/{id}")
	public ResponseEntity<WodDto> getWodById(@CookieValue(name="refresh") String jwt, @PathVariable Long id) {
		return wodService.getWodByIdService(jwt, id);
	}
	
	@PostMapping("/wod")
	public ResponseEntity insertWod(@CookieValue(name="refresh") String jwt, @RequestBody WodDto wodDto) {
		return wodService.insertWodService(jwt, wodDto);
	}
	
	@PutMapping("/wod")
	public ResponseEntity updateWod(@CookieValue(name="refresh") String jwt, @RequestBody WodDto wodDto) {
		return wodService.updateWodService(jwt, wodDto);
	}
	
	@DeleteMapping("/wod/{id}")
	public ResponseEntity deleteWodById(@CookieValue(name="refresh") String jwt, @PathVariable Long id) {
		return wodService.deleteWodByIdService(jwt, id);
	}

}