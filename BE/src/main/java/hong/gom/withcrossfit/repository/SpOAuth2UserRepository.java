package hong.gom.withcrossfit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.entity.SpOAuth2User;

public interface SpOAuth2UserRepository extends JpaRepository<SpOAuth2User, String> {

}
