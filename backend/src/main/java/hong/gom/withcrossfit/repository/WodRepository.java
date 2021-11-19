package hong.gom.withcrossfit.repository;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.Wod;

public interface WodRepository extends JpaRepository<Wod, Long> {
	
	Wod findByDateAndBox(LocalDate date, Box box); 
	
	Page<Wod> findBybox(Box box, Pageable pageable);

}
