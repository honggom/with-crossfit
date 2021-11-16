package hong.gom.withcrossfit.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.BoxIdAndUserEmailDto;
import hong.gom.withcrossfit.dto.UserDto;
import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.repository.BoxRepository;
import hong.gom.withcrossfit.repository.SpUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserApiService {
	
	private final SpUserRepository userRepository;
	private final BoxRepository boxRepository;
	
	private final ModelMapper modelMapper;
	
	public List<UserDto> getNotRegisteredUserService() {
		
		List<SpUser> users = userRepository.findByBoxIsNull();
		
		return users.stream()
				.map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
	}
	
	public ResponseEntity insertNewBoxToUser(BoxIdAndUserEmailDto boxIdAndUserEmailDto) {
		
		Box box = boxRepository.findById(boxIdAndUserEmailDto.getBoxId()).get();
		SpUser user = userRepository.findByEmail(boxIdAndUserEmailDto.getEmail());
		
		user.setBox(box);
		userRepository.save(user);
		
		return new ResponseEntity(HttpStatus.OK);
	}

}
