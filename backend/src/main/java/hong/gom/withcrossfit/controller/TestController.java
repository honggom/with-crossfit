package hong.gom.withcrossfit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/err")
	public String hello() {
		System.out.println("aaaaaaaaaaaaaaaa");
		return "hello";
	}

}