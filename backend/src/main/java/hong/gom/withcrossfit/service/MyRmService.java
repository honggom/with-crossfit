package hong.gom.withcrossfit.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.MyRmDto;
import hong.gom.withcrossfit.entity.MyRm;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.jwt.TokenUtils;
import hong.gom.withcrossfit.repository.MyRmRepository;
import hong.gom.withcrossfit.repository.SpUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyRmService {
	
	private final MyRmRepository myRmRepository;
	private final SpUserRepository userRepository;
	private final TokenUtils tokenUtils;
	
	public ResponseEntity insertMyRmService(MyRmDto myRmDto, String jwt) {
		String email = tokenUtils.getEmail(jwt);
		
		SpUser user = userRepository.findByEmail(email);
		
		myRmRepository.save(MyRm.builder()
				      .name(myRmDto.getName())
				      .repetition(myRmDto.getRepetition())
				      .lb(myRmDto.getLb())
				      .spUser(user)
				      .build());
		
		return new ResponseEntity(HttpStatus.OK);
	}
}
