package hong.gom.withcrossfit.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.BoxIdAndUserEmailDto;
import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.repository.BoxRepository;
import hong.gom.withcrossfit.repository.SpUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAdminApiService {
	
	private final SpUserRepository userRepository;
	private final BoxRepository boxRepository;
	
	public List<SpUser> getNotRegisteredUserService() {
		return userRepository.findByBoxIsNull();
	}
	
	public void insertNewBoxToUser(BoxIdAndUserEmailDto boxIdAndUserEmailDto) {
		Box box = boxRepository.findById(boxIdAndUserEmailDto.getBoxId()).get();
		SpUser user = userRepository.findByEmail(boxIdAndUserEmailDto.getEmail());
		
		user.setBox(box);
		userRepository.save(user);
	}

}
