package hong.gom.withcrossfit.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.entity.Wod;

public interface WodRepository extends JpaRepository<Wod, Long> {
	
	Wod findByDate(LocalDate date); 

}
