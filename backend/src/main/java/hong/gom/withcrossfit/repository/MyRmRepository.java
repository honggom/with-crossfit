package hong.gom.withcrossfit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.entity.MyRm;
import hong.gom.withcrossfit.entity.SpUser;

public interface MyRmRepository extends JpaRepository<MyRm, Long> {
	
	List<MyRm> findByUser(SpUser user);
	
	void deleteById(Long id);

}
