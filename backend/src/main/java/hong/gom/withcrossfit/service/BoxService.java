package hong.gom.withcrossfit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.BoxDto;
import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.repository.BoxRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoxService {

	private final BoxRepository boxRepository;
	
	private final ModelMapper modelMapper;
	
	public List<BoxDto> getBoxService() {
		
		List<Box> boxes = boxRepository.findAll();
		
		return boxes.stream()
				.map(box -> modelMapper.map(box, BoxDto.class))
				.collect(Collectors.toList());
	}
	
}
