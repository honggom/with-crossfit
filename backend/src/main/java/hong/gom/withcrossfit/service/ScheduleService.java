package hong.gom.withcrossfit.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hong.gom.withcrossfit.dto.EachTimeDto;
import hong.gom.withcrossfit.dto.ScheduleDto;
import hong.gom.withcrossfit.dto.SpecificEachTimeDto;
import hong.gom.withcrossfit.dto.SpecificScheduleDto;
import hong.gom.withcrossfit.dto.UpdateScheduleSetDto;
import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.EachTime;
import hong.gom.withcrossfit.entity.Schedule;
import hong.gom.withcrossfit.entity.ScheduleSet;
import hong.gom.withcrossfit.entity.SpecificEachTime;
import hong.gom.withcrossfit.entity.SpecificSchedule;
import hong.gom.withcrossfit.jwt.TokenUtils;
import hong.gom.withcrossfit.repository.EachTimeRepository;
import hong.gom.withcrossfit.repository.ScheduleRepository;
import hong.gom.withcrossfit.repository.ScheduleSetRepository;
import hong.gom.withcrossfit.repository.SpUserRepository;
import hong.gom.withcrossfit.repository.SpecificEachTimeRepository;
import hong.gom.withcrossfit.repository.SpecificScheduleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final TokenUtils tokenUtils;
	private final ModelMapper modelMapper;
	
	private final SpUserRepository userRepository;
	private final ScheduleRepository scheduleRepository;
	private final SpecificScheduleRepository specificScheduleRepository;
	private final SpecificEachTimeRepository specificEachTimeRepository;
	private final EachTimeRepository eachTimeRepository;
	private final ScheduleSetRepository scheduleSetRepository;
	
	public ResponseEntity<List<SpecificSchedule>> getSpecificScheduleService(String jwt, String start, String end) {
		String email = tokenUtils.getEmail(jwt);
		Box box = userRepository.findByEmail(email).getBox();
		LocalDate startDate = makeToLocalDate(start);
		LocalDate endDate = makeToLocalDate(end);
		
		return ResponseEntity.ok()
				             .body(specificScheduleRepository.findByBoxAndDateBetween(box, startDate, endDate));
	}
	
	public ResponseEntity insertScheduleService(String jwt, ScheduleDto scheduleDto) {
		String email = tokenUtils.getEmail(jwt);
		Box box = userRepository.findByEmail(email).getBox();
		
		Schedule savedSchedule = scheduleRepository.save(Schedule.builder()
				                                   .name(scheduleDto.getName())
				                                   .box(box)
				                                   .build());
		
		
		for (EachTimeDto time : scheduleDto.getTimes()) {
			eachTimeRepository.save(EachTime.builder()
					                        .start(time.getStart())
					                        .end(time.getEnd())
					                        .schedule(savedSchedule)
					                        .build());
		}
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	public ResponseEntity<List<ScheduleDto>> getScheduleByBoxService(String jwt) {
		String email = tokenUtils.getEmail(jwt);
		Box box = userRepository.findByEmail(email).getBox();
		
		List<Schedule> schedules = scheduleRepository.findByBox(box);
		
		return ResponseEntity.ok().body(schedules.stream()
				                                 .map(schedule -> modelMapper.map(schedule, ScheduleDto.class))
				                                 .collect(Collectors.toList()));
	}
	
	public ResponseEntity<List<EachTimeDto>> getEachTimeByScheduleIdService(Long id) {
		List<EachTime> eachTimes = eachTimeRepository.findBySchedule(scheduleRepository.findById(id).get());
		
		return ResponseEntity.ok().body(eachTimes.stream()
				                                 .map(eachTime -> modelMapper.map(eachTime, EachTimeDto.class))
				                                 .collect(Collectors.toList()));
	}
	
	public ResponseEntity deleteScheduleByIdService(Long id) {
		Schedule schedule = scheduleRepository.findById(id).get();
		int result = eachTimeRepository.deleteBySchedule(schedule);
		scheduleRepository.delete(schedule);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	public ResponseEntity<ScheduleSet> getScheduleSetByBoxService(String jwt) {
		String email = tokenUtils.getEmail(jwt);
		Box box = userRepository.findByEmail(email).getBox();
		
		Optional<ScheduleSet> scheduleSet =	scheduleSetRepository.findByBox(box);
		
		if(scheduleSet.isPresent()) {
			logger.info("scheduleSet 존재함");
			return ResponseEntity.ok().body(scheduleSet.get());
		} else {
			logger.info("scheduleSet 존재하지 않음");
			return ResponseEntity.ok().body(scheduleSetRepository.save(ScheduleSet.builder()
					                                                              .box(box)
					                                                              .build()));
		}
	}
	
	public ResponseEntity updateScheduleSetService(UpdateScheduleSetDto dto) {
		ScheduleSet scheduleSet = scheduleSetRepository.findById(dto.getScheduleSetId()).get();
		
		Schedule newSchedule = null;
		
		String day = dto.getDay();
		
		if (dto.getScheduleId() != -1) { // 휴일이 아닌 경우
			newSchedule = scheduleRepository.findById(dto.getScheduleId()).get();
		}
		
		if (day.equals("monday")) {
			scheduleSet.setMonday(newSchedule);
		} else if (day.equals("tuesday")) {
			scheduleSet.setTuesday(newSchedule);
		} else if (day.equals("wednesday")) {
			scheduleSet.setWednesday(newSchedule);
		} else if (day.equals("thursday")) {
			scheduleSet.setThursday(newSchedule);
		} else if (day.equals("friday")) {
			scheduleSet.setFriday(newSchedule);
		} else if (day.equals("saturady")) {
			scheduleSet.setSaturady(newSchedule);
		} else if (day.equals("sunday")) {
			scheduleSet.setSunday(newSchedule);
		}
		
		scheduleSetRepository.save(scheduleSet);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	public ResponseEntity insertSpecificScheduleService(String jwt, SpecificScheduleDto dto) {
		String email = tokenUtils.getEmail(jwt);
		Box box = userRepository.findByEmail(email).getBox();
		
		
		SpecificSchedule savedSpecificSchedule = specificScheduleRepository.save(SpecificSchedule.builder()
				                                   .name(dto.getName())
				                                   .box(box)
				                                   .date(makeToLocalDate(dto.getDateStr()))
				                                   .build());
		
		
		for (SpecificEachTimeDto time : dto.getTimes()) {
			specificEachTimeRepository.save(SpecificEachTime.builder()
					                                        .start(time.getStart())
					                                        .end(time.getEnd())
					                                        .specificSchedule(savedSpecificSchedule)
					                                        .build());
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	
	private LocalDate makeToLocalDate(String dateStr) {
		String[] dates = dateStr.split("-");
		
		int year = Integer.parseInt(dates[0]);
		int month = Integer.parseInt(dates[1]);
		int date = Integer.parseInt(dates[2]);
		
		return LocalDate.of(year, month, date);
	}
}
