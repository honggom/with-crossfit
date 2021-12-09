package hong.gom.withcrossfit.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.UserDto;
import hong.gom.withcrossfit.entity.SpAuthority;
import hong.gom.withcrossfit.entity.SpOAuth2User;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.enums.Grade;
import hong.gom.withcrossfit.jwt.TokenUtil;
import hong.gom.withcrossfit.repository.SpOAuth2UserRepository;
import hong.gom.withcrossfit.repository.SpUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SpUserService implements UserDetailsService {

	private final SpUserRepository userRepository;
	private final SpOAuth2UserRepository oAuth2UserRepository;
	private final TokenUtil tokenUtil;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findSpUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}

	public Optional<SpUser> findUser(String email) {
		return userRepository.findSpUserByEmail(email);
	}

	public SpUser save(SpUser user) {
		return userRepository.save(user);
	}

	public void addAuthority(Long userId, String authority) {
		userRepository.findById(userId).ifPresent(user -> {
			SpAuthority newRole = new SpAuthority(user.getUserId(), authority); // 해당 id의 새로운 권한을 부여
			if (user.getAuthorities() == null) {
				HashSet<SpAuthority> authorities = new HashSet<>();
				authorities.add(newRole);
				user.setAuthorities(authorities);
				save(user);
			} else if (!user.getAuthorities().contains(newRole)) {
				HashSet<SpAuthority> authorities = new HashSet<>();
				authorities.addAll(user.getAuthorities());
				authorities.add(newRole);
				user.setAuthorities(authorities);
				save(user);
			}
		});
	}

	public SpUser load(SpOAuth2User oAuth2User) {
		SpOAuth2User dbUser = oAuth2UserRepository.findById(oAuth2User.getOauth2UserId()).orElseGet(() -> {
			SpUser user = new SpUser();
			user.setEmail(oAuth2User.getEmail());
			user.setName(oAuth2User.getName());
			user.setEnabled(true);
			user.setGrade(Grade.RAINBOW);
			user = userRepository.save(user);
			addAuthority(user.getUserId(), "ROLE_USER");

			oAuth2User.setUserId(user.getUserId());
			return oAuth2UserRepository.save(oAuth2User);
		});
		return userRepository.findById(dbUser.getUserId()).get();
	}
	
	public SpUser findUserByJwt(String jwt) {
		String email = tokenUtil.getEmail(jwt);
		return userRepository.findByEmail(email);
	}

}
