package hong.gom.withcrossfit.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.WodDto;
import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.entity.Wod;
import hong.gom.withcrossfit.repository.WodRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WodService {

	private final WodRepository wodRepository;
	
	public Page<Wod> getWod(Box box, Pageable pageable) {
		return wodRepository.findBybox(box, pageable);
	}
	
	public Wod getWodById(String email, Long id) {
		Optional<Wod> wod = wodRepository.findById(id);
		
		if (wod.isPresent()) {
			if (wod.get().getWriter().getEmail().equals(email)) {
				wod.get().setEditable(true);
			} else {
				wod.get().setEditable(false);
			}
			return wod.get();
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
	
	public boolean isWriter(String email, Long id) {
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
