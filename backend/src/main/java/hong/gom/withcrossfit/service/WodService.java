package hong.gom.withcrossfit.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.WodDto;
import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.entity.Wod;
import hong.gom.withcrossfit.jwt.TokenUtils;
import hong.gom.withcrossfit.repository.SpUserRepository;
import hong.gom.withcrossfit.repository.WodRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WodService {

	private final SpUserRepository userRepository;
	private final TokenUtils tokenUtils;
	private final WodRepository wodRepository;
	private final ModelMapper modelMapper;
	
	public ResponseEntity<Page<Wod>> getWodService(String jwt, Pageable pageable) {
		String email = tokenUtils.getEmail(jwt);
		Box box = userRepository.findByEmail(email).getBox();
		return ResponseEntity.ok().body(wodRepository.findBybox(box, pageable));
	}
	
	public ResponseEntity<WodDto> getWodByIdService(String jwt, Long id) {
		String email = tokenUtils.getEmail(jwt);
		Wod wod = wodRepository.findById(id).get();
		WodDto dto = modelMapper.map(wod, WodDto.class);
		
		if (wod.getWriter().getEmail().equals(email)) {
			dto.setEditable(true);
		} else {
			dto.setEditable(false);
		}
		
		return ResponseEntity.ok().body(dto);
	}

	public ResponseEntity insertWodService(String jwt, WodDto wodDto) {
		String email = tokenUtils.getEmail(jwt);
		SpUser user = userRepository.findByEmail(email);
		Box box = user.getBox();
		
		Wod oldWod = wodRepository.findByDateAndBox(wodDto.getDate(), box);
		
		if (oldWod == null) {
			wodRepository.save(Wod.builder()
					              .writer(user)
					              .date(wodDto.getDate())
					              .title(wodDto.getTitle())
					              .content(wodDto.getContent())
					              .box(box)
					              .build());
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity("이미 해당 날짜에 작성된 WOD가 존재합니다.", HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity updateWodService(String jwt, WodDto wodDto) {
		String email = tokenUtils.getEmail(jwt);
		SpUser user = userRepository.findByEmail(email);
		Box box = user.getBox();
		
		Wod oldWod = wodRepository.findByDateAndBox(wodDto.getDate(), box);
		
		if (oldWod == null) {
			updateWod(wodDto);
			return new ResponseEntity(HttpStatus.OK);
		} else if (wodDto.getId() == oldWod.getId()) {
			updateWod(wodDto);
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity("이미 해당 날짜에 작성된 WOD가 존재합니다.", HttpStatus.BAD_REQUEST);
		}
	}
	
	private Wod updateWod(WodDto wodDto) {
		Wod newWod = wodRepository.findById(wodDto.getId()).get();
		
		newWod.setDate(wodDto.getDate());
		newWod.setContent(wodDto.getContent());
		newWod.setTitle(wodDto.getTitle());
		
		return wodRepository.save(newWod);
	}
	
	public ResponseEntity deleteWodByIdService(String jwt, Long id) {
		String email = tokenUtils.getEmail(jwt);
		Wod wod = wodRepository.findById(id).get();
		
		if (email.equals(wod.getWriter().getEmail())) {
			wodRepository.delete(wod);
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity("권한이 없습니다.", HttpStatus.BAD_REQUEST);
		}
	}
}
