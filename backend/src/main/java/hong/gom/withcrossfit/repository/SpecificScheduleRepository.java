package hong.gom.withcrossfit.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.SpecificSchedule;


public interface SpecificScheduleRepository extends JpaRepository<SpecificSchedule, Long> {
	
	List<SpecificSchedule> findByBoxAndDateBetween(Box box, LocalDate start, LocalDate end);

}
