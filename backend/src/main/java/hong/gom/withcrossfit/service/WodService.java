package hong.gom.withcrossfit.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.WodDto;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.entity.Wod;
import hong.gom.withcrossfit.jwt.TokenUtils;
import hong.gom.withcrossfit.repository.SpUserRepository;
import hong.gom.withcrossfit.repository.WodRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WodService {

	private final SpUserRepository userRepository;
	private final TokenUtils tokenUtils;
	private final WodRepository wodRepository;

	public ResponseEntity insertWodService(String jwt, WodDto wodDto) {
		String email = tokenUtils.getEmail(jwt);
		SpUser user = userRepository.findByEmail(email);
		Wod oldWod = wodRepository.findByDate(wodDto.getDate());

		if (oldWod == null) {
			wodRepository.save(Wod.builder()
					              .writer(user)
					              .date(wodDto.getDate())
					              .title(wodDto.getTitle())
					              .content(wodDto.getContent())
					              .build());
			return new ResponseEntity(HttpStatus.OK);
		} else {
			System.out.println("중복");
			System.out.println("중복");
			System.out.println("중복");
			return new ResponseEntity("이미 해당 날짜에 작성된 WOD가 존재합니다.", HttpStatus.BAD_REQUEST);
		}

	}

}
