package hong.gom.withcrossfit.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.UserDto;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.repository.SpUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserApiService {
	
	private final SpUserRepository userRepository;
	
	private final ModelMapper modelMapper;
	
	public List<UserDto> getNotRegisteredUserService() {
		
		List<SpUser> users = userRepository.findByBoxIsNull();
		
		return users.stream()
				.map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
	}

}
