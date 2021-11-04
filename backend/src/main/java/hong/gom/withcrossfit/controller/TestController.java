package hong.gom.withcrossfit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/api/hello")
	public String hello() {
		return "hello";
	}
	
	
	/*
	 * 1. 로그인 페이지로 진입하면 쿠키 무조건 삭제하고 시작
	 * 
	 * 
	 * 
	 */
}