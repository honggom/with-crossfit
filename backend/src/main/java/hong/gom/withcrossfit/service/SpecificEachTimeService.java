package hong.gom.withcrossfit.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.entity.SpecificEachTime;
import hong.gom.withcrossfit.entity.SpecificSchedule;
import hong.gom.withcrossfit.repository.SpecificEachTimeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SpecificEachTimeService {
	
	private final SpecificEachTimeRepository specificEachTimeRepository;
	
	public List<SpecificEachTime> findBySpecificSchedule(SpecificSchedule specificSchedule) {
		return specificEachTimeRepository.findBySpecificSchedule(specificSchedule);
	}

}