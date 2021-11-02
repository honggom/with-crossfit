package hong.gom.withcrossfit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.jwt.TokenService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TokenController {
    private final TokenService tokenService;
    
    @GetMapping("/token/generated")
    public String generated() {
    	return "generated";
    }

}
