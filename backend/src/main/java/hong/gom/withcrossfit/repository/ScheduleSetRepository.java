package hong.gom.withcrossfit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.ScheduleSet;

public interface ScheduleSetRepository extends JpaRepository<ScheduleSet, Long> {
	
	Optional<ScheduleSet> findByBox(Box box);

}