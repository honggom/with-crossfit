package hong.gom.withcrossfit.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.MyRmDto;
import hong.gom.withcrossfit.jwt.TokenUtils;
import hong.gom.withcrossfit.repository.MyRmRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyRmService {
	
	private final MyRmRepository myRmRepository;
	private final TokenUtils tokenUtils;
	
	public ResponseEntity insertMyRmService(MyRmDto myRmDto, String jwt) {
		System.out.println(tokenUtils.getEmail(jwt));
		return new ResponseEntity(HttpStatus.OK);
	}
}
