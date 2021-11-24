package hong.gom.withcrossfit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.EachTimeDto;
import hong.gom.withcrossfit.dto.MyRmDto;
import hong.gom.withcrossfit.dto.ScheduleDto;
import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.EachTime;
import hong.gom.withcrossfit.entity.Schedule;
import hong.gom.withcrossfit.jwt.TokenUtils;
import hong.gom.withcrossfit.repository.EachTimeRepository;
import hong.gom.withcrossfit.repository.ScheduleRepository;
import hong.gom.withcrossfit.repository.SpUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
	
	private final SpUserRepository userRepository;
	private final TokenUtils tokenUtils;
	private final ScheduleRepository scheduleRepository;
	private final EachTimeRepository eachTimeRepository;
	private final ModelMapper modelMapper;
	
	public ResponseEntity insertScheduleService(String jwt, ScheduleDto scheduleDto) {
		String email = tokenUtils.getEmail(jwt);
		Box box = userRepository.findByEmail(email).getBox();
		
		Schedule savedSchedule = scheduleRepository.save(Schedule.builder()
				                                   .name(scheduleDto.getName())
				                                   .box(box)
				                                   .build());
		
	    List<EachTime> times = new ArrayList<>();
		
		for (EachTimeDto time : scheduleDto.getTimes()) {
			times.add(eachTimeRepository.save(EachTime.builder()
					                                  .start(time.getStart())
					                                  .end(time.getEnd())
					                                  .schedule(savedSchedule)
					                                  .build()));
		}
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	public ResponseEntity<List<ScheduleDto>> getScheduleByBox(String jwt) {
		String email = tokenUtils.getEmail(jwt);
		Box box = userRepository.findByEmail(email).getBox();
		
		List<Schedule> schedules = scheduleRepository.findByBox(box);
		
		return ResponseEntity.ok().body(schedules.stream()
				                           .map(schedule -> modelMapper.map(schedule, ScheduleDto.class))
				                           .collect(Collectors.toList()));
	}
}
