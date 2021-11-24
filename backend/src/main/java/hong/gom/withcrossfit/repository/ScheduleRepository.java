package hong.gom.withcrossfit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	
	List<Schedule> findByBox(Box box);

}
