package hong.gom.withcrossfit.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.MyRmDto;
import hong.gom.withcrossfit.entity.MyRm;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.repository.MyRmRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MyRmService {
	
	private final MyRmRepository myRmRepository;
	private final ModelMapper modelMapper;
	
	public List<MyRmDto> convertToDto(List<MyRm> rms) {
		return rms.stream()
				  .map(rm -> modelMapper.map(rm, MyRmDto.class))
				  .collect(Collectors.toList());
	}
	
	public MyRmDto convertToDto(MyRm rm) {
		return modelMapper.map(rm, MyRmDto.class);
	}
	
	public List<MyRm> getMyRms(SpUser user) {
		return myRmRepository.findByUser(user);
	}
	
	public Optional<MyRm> getMyRmById(Long id) {
		return myRmRepository.findById(id);
	}
	
	public void updateMyRm(MyRm oldRm, MyRmDto newRmDto) {
		oldRm.setName(newRmDto.getName());
		oldRm.setRepetition(newRmDto.getRepetition());
		oldRm.setLb(newRmDto.getLb());
		
		myRmRepository.save(oldRm);
	}
	
	public void deleteMyRmByIdService(Long id) {
		myRmRepository.deleteById(id);
	}
	
	public void insertMyRmService(SpUser user, MyRmDto myRmDto) {
		myRmRepository.save(MyRm.builder()
				      .name(myRmDto.getName())
				      .repetition(myRmDto.getRepetition())
				      .lb(myRmDto.getLb())
				      .user(user)
				      .build());
	}
}