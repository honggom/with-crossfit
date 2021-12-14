package hong.gom.withcrossfit.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.repository.BoxRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoxService {

	private final BoxRepository boxRepository;
	
	public List<Box> getBoxes() {
		return boxRepository.findAll(); 
	}
	
}