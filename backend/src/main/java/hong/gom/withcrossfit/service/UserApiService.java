package hong.gom.withcrossfit.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.UserDto;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.jwt.TokenUtil;
import hong.gom.withcrossfit.repository.SpUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserApiService {
	
	private final TokenUtil tokenUtils;
	private final ModelMapper modelMapper;
	private final SpUserRepository userRepository;
	
	public UserDto getUserService(String jwt) {
		String email = tokenUtils.getEmail(jwt);
		SpUser user = userRepository.findByEmail(email);
		
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
		return userDto;
	}
	
	
}
