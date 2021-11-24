package hong.gom.withcrossfit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.dto.ScheduleDto;
import hong.gom.withcrossfit.entity.EachTime;
import hong.gom.withcrossfit.entity.Schedule;

public interface EachTimeRepository extends JpaRepository<EachTime, Long> {
	
	List<EachTime> findBySchedule(Schedule schedule);
	
	int deleteBySchedule(Schedule schedulea);

}
