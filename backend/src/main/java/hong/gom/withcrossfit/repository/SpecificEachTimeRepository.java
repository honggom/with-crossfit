package hong.gom.withcrossfit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.entity.SpecificEachTime;
import hong.gom.withcrossfit.entity.SpecificSchedule;

public interface SpecificEachTimeRepository extends JpaRepository<SpecificEachTime, Long> {
	
	List<SpecificEachTime> findBySpecificSchedule(SpecificSchedule specificSchedule);
	
	void deleteBySpecificSchedule(SpecificSchedule specificSchedule);

}
