package hong.gom.withcrossfit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.entity.SpUser;

public interface SpUserRepository extends JpaRepository<SpUser, Long> {

    Optional<SpUser> findSpUserByEmail(String email);
    
    SpUser findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<SpUser> findByBoxIsNull();

}
