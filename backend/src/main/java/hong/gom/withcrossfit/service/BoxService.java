package hong.gom.withcrossfit.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.BoxDto;
import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.repository.BoxRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoxService {

	private final BoxRepository boxRepository;
	private final ModelMapper modelMapper;
	
	public List<Box> getBoxes() {
		return boxRepository.findAll(); 
	}
	
	public List<BoxDto> convertToDto(List<Box> boxes) {
		return boxes.stream()
			        .map(box -> modelMapper.map(box, BoxDto.class))
			        .collect(Collectors.toList());
	}
	
}