package hong.gom.withcrossfit.service;

import java.time.LocalDate;
import java.util.Optional;

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
import hong.gom.withcrossfit.jwt.TokenUtil;
import hong.gom.withcrossfit.repository.SpUserRepository;
import hong.gom.withcrossfit.repository.WodRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WodService {

	private final SpUserRepository userRepository;
	private final TokenUtil tokenUtils;
	private final WodRepository wodRepository;
	private final ModelMapper modelMapper;
	
	public Page<Wod> getWodService(String jwt, Pageable pageable) {
		String email = tokenUtils.getEmail(jwt);
		Box box = userRepository.findByEmail(email).getBox();
		return wodRepository.findBybox(box, pageable);
	}
	
	public WodDto getWodByIdService(String jwt, Long id) {
		String email = tokenUtils.getEmail(jwt);
		Optional<Wod> wod = wodRepository.findById(id);
		
		if (wod.isPresent()) {
			WodDto dto = modelMapper.map(wod.get(), WodDto.class);
			if (wod.get().getWriter().getEmail().equals(email)) {
				dto.setEditable(true);
			} else {
				dto.setEditable(false);
			}
			return dto;
		} 
		return null;
	}

	public void insertWodService(SpUser user, WodDto wodDto) {
		wodRepository.save(Wod.builder()
					          .writer(user)
					          .date(wodDto.getDate())
					          .title(wodDto.getTitle())
					          .content(wodDto.getContent())
					          .box(user.getBox())
					          .build());
	}
	
	public boolean isAlreadyExistWodByBoxAndDate(Box box, LocalDate date) {
		Wod oldWod = wodRepository.findByDateAndBox(date, box);
		
		if (oldWod == null) {
			return false;	
		}
		return true;
	}
	
	public void updateWodService(WodDto wodDto) {
		Wod newWod = wodRepository.findById(wodDto.getId()).get();
		
		newWod.setDate(wodDto.getDate());
		newWod.setContent(wodDto.getContent());
		newWod.setTitle(wodDto.getTitle());
		
		wodRepository.save(newWod);
	}
	
	public boolean isWriter(String jwt, Long id) {
		String email = tokenUtils.getEmail(jwt);
		Wod wod = wodRepository.findById(id).get();
		
		if (email.equals(wod.getWriter().getEmail())) {
			return true;
		}
		return false;
	}
	
	public void deleteWodByIdService(Long id) {
		wodRepository.deleteById(id);
	}
}
