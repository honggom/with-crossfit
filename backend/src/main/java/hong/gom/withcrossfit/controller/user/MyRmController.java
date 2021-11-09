package hong.gom.withcrossfit.controller.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.MyRmDto;
import hong.gom.withcrossfit.service.MyRmService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MyRmController {
	
	private final MyRmService myRmService;
	
	@GetMapping("/api/my-rm")
	public String getMyRm() throws IOException {
		return "hi";
	}
	
	@PostMapping("/api/my-rm")
	public ResponseEntity insertMyRm(@RequestBody MyRmDto myRmDto,
							 @CookieValue(name="jwt") String jwt) {
		return myRmService.insertMyRmService(myRmDto, jwt);
	}
}