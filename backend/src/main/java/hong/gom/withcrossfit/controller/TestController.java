package hong.gom.withcrossfit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	// FIXME 쿠키 전부 삭제하고 api 요청하면 에러남 
	
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