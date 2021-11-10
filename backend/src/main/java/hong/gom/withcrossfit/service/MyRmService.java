package hong.gom.withcrossfit.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
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
	
	private final ModelMapper modelMapper;
	
	public List<MyRmDto> getMyRmService(String jwt) {
		String email = tokenUtils.getEmail(jwt);
		SpUser user = userRepository.findByEmail(email);
		
		List<MyRm> rms = myRmRepository.findByUser(user);
		
		return rms.stream()
				  .map(rm -> modelMapper.map(rm, MyRmDto.class))
				  .collect(Collectors.toList());
	}
	
	public MyRmDto getMyRmByIdService(Long id) {
		MyRm rm = myRmRepository.findById(id).get();
		return modelMapper.map(rm, MyRmDto.class);
	}
	
	public ResponseEntity updateMyRmService(MyRmDto myRmDto) {
		MyRm rm = myRmRepository.findById(myRmDto.getId()).get();
		
		rm.setName(myRmDto.getName());
		rm.setRepetition(myRmDto.getRepetition());
		rm.setLb(myRmDto.getLb());
		
		myRmRepository.save(rm);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	public ResponseEntity deleteMyRmByIdService(Long id) {
		myRmRepository.deleteById(id);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	public ResponseEntity insertMyRmService(MyRmDto myRmDto, String jwt) {
		String email = tokenUtils.getEmail(jwt);
		SpUser user = userRepository.findByEmail(email);
		
		myRmRepository.save(MyRm.builder()
				      .name(myRmDto.getName())
				      .repetition(myRmDto.getRepetition())
				      .lb(myRmDto.getLb())
				      .user(user)
				      .build());
		
		return new ResponseEntity(HttpStatus.OK);
	}
}
