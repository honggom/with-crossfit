package hong.gom.withcrossfit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import hong.gom.withcrossfit.repository.EachTimeRepository;
import hong.gom.withcrossfit.repository.ScheduleRepository;
import hong.gom.withcrossfit.repository.ScheduleSetRepository;
import hong.gom.withcrossfit.repository.SpecificEachTimeRepository;
import hong.gom.withcrossfit.repository.SpecificScheduleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final ScheduleRepository scheduleRepository;
	private final SpecificScheduleRepository specificScheduleRepository;
	private final SpecificEachTimeRepository specificEachTimeRepository;
	private final EachTimeRepository eachTimeRepository;
	private final ScheduleSetRepository scheduleSetRepository;
	
	public List<SpecificSchedule> getSpecificSchedule(Box box, LocalDate startDate, LocalDate endDate) {
		return specificScheduleRepository.findByBoxAndDateBetween(box, startDate, endDate);
	}
	
	public Optional<SpecificSchedule> getSpecificScheduleByDate(Box box, LocalDate date) {
		return specificScheduleRepository.findByBoxAndDate(box, date);
	}
	
	public void deleteSpecificScheduleById(Long id) {
		SpecificSchedule specificSchedule = specificScheduleRepository.findById(id).get();
		specificEachTimeRepository.deleteBySpecificSchedule(specificSchedule);
		specificScheduleRepository.delete(specificSchedule);
	}
	
	public void insertSchedule(Box box, ScheduleDto scheduleDto) {
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
	}
	
	public List<Schedule> getScheduleByBoxService(Box box) {
		return scheduleRepository.findByBox(box);
	}
	
	public List<EachTime> getEachTimeByScheduleId(Long id) {
		return eachTimeRepository.findBySchedule(scheduleRepository.findById(id).get());
	}
	
	public void deleteScheduleByIdService(Long id) {
		Schedule schedule = scheduleRepository.findById(id).get();
		eachTimeRepository.deleteBySchedule(schedule);
		scheduleRepository.delete(schedule);
	}
	
	public ScheduleSet getScheduleSetByBox(Box box) {
		Optional<ScheduleSet> scheduleSet =	scheduleSetRepository.findByBox(box);
		
		if(scheduleSet.isPresent()) {
			logging("scheduleSet 존재함");
			return scheduleSet.get();
		} else {
			logging("scheduleSet 존재하지 않음");
			return scheduleSetRepository.save(ScheduleSet.builder()
                                                         .box(box)
					                                     .build());
		}
	}
	
	public void updateScheduleSetService(UpdateScheduleSetDto dto) {
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
	}
	
	public void insertSpecificSchedule(Box box, SpecificScheduleDto dto, LocalDate date) {
		SpecificSchedule savedSpecificSchedule = specificScheduleRepository.save(SpecificSchedule.builder()
				                                   .name(dto.getName())
				                                   .box(box)
				                                   .date(date)
				                                   .build());
		
		
		for (SpecificEachTimeDto time : dto.getTimes()) {
			specificEachTimeRepository.save(SpecificEachTime.builder()
					                                        .start(time.getStart())
					                                        .end(time.getEnd())
					                                        .specificSchedule(savedSpecificSchedule)
					                                        .build());
		}
	}
	
	public void insertDayOffSpecificSchedule(Box box, LocalDate date) {
		specificScheduleRepository.save(SpecificSchedule.builder()
                                  .box(box)
                                  .date(date)
                                  .isDayOff(true)
                                  .build());
	}
	
	private void logging(String message) {
		LOGGER.info("ScheduleService INFO : " + message);
	}
}













