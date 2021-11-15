package hong.gom.withcrossfit.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.BoxDto;
import hong.gom.withcrossfit.service.BoxService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class BoxController {
	
	private final BoxService boxService;
	
	@GetMapping("/box")
	public ResponseEntity<List<BoxDto>> getBox() {
		return ResponseEntity.ok()
		        .body(boxService.getBoxService());
	}
	
}
