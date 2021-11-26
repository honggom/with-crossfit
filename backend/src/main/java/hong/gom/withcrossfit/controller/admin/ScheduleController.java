package hong.gom.withcrossfit.controller.admin;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import hong.gom.withcrossfit.dto.DayOffSpecificScheduleDto;
import hong.gom.withcrossfit.dto.EachTimeDto;
import hong.gom.withcrossfit.dto.ScheduleDto;
import hong.gom.withcrossfit.dto.SpecificScheduleDto;
import hong.gom.withcrossfit.dto.SpecificScheduleResponseDto;
import hong.gom.withcrossfit.dto.UpdateScheduleSetDto;
import hong.gom.withcrossfit.entity.ScheduleSet;
import hong.gom.withcrossfit.entity.SpecificSchedule;
import hong.gom.withcrossfit.service.ScheduleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class ScheduleController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final ScheduleService scheduleService;
	
	@GetMapping("/now")
	public ResponseEntity<LocalDate> getNow() {
		logger.info("now : " + LocalDate.now());
		return ResponseEntity.ok().body(LocalDate.now().plusDays(1L));
	}
	
	@GetMapping("/specific-schedule/{start}/{end}")
	public ResponseEntity<List<SpecificSchedule>> getSpecificSchedule(@CookieValue(name = "refresh") String jwt,
									                                  @PathVariable String start, 
									                                  @PathVariable String end) {
		
		return scheduleService.getSpecificScheduleService(jwt, start, end);
	}
	
	@GetMapping("/specific-schedule/{date}")
	public ResponseEntity<SpecificScheduleResponseDto> getSpecificScheduleByDate(@CookieValue(name = "refresh") String jwt, @PathVariable String date) {
		return scheduleService.getSpecificScheduleByDateService(jwt, date);
	}
	
	@DeleteMapping("/specific-schedule/{id}")
	public ResponseEntity deleteSpecificScheduleById(@PathVariable Long id) {
		return scheduleService.deleteSpecificScheduleByIdService(id);
	}
	
	@PostMapping("/schedule")
	public ResponseEntity insertSchedule(@CookieValue(name = "refresh") String jwt, @RequestBody ScheduleDto scheduleDto) {
		return scheduleService.insertScheduleService(jwt, scheduleDto);
	}
	
	@GetMapping("/schedule")
	public ResponseEntity<List<ScheduleDto>> getScheduleByBox(@CookieValue(name = "refresh") String jwt) {
		return scheduleService.getScheduleByBoxService(jwt);
	}
	
	@GetMapping("/schedule/{id}")
	public ResponseEntity<List<EachTimeDto>> getEachTimeByScheduleId(@PathVariable Long id) {
		return scheduleService.getEachTimeByScheduleIdService(id);
	}
	
	@DeleteMapping("/schedule/{id}")
	public ResponseEntity deleteScheduleById(@PathVariable Long id) {
		try {
			ResponseEntity reponse = scheduleService.deleteScheduleByIdService(id);
			return reponse;
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity("해당 기본 시간표로 설정된 요일이 있습니다.(기본 시간표를 삭제하고 싶으시면 설정된 요일의 시간표를 변경해주세요.)", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/schedule-set")
	public ResponseEntity<ScheduleSet> getScheduleSetByBox(@CookieValue(name = "refresh") String jwt) {
		return scheduleService.getScheduleSetByBoxService(jwt);
	}
	
	@PutMapping("/schedule-set")
	public ResponseEntity updateScheduleSet(@RequestBody UpdateScheduleSetDto dto) {
		return scheduleService.updateScheduleSetService(dto);
	}
	
	@PostMapping("/specific-schedule")
	public ResponseEntity insertSpecificSchedule(@CookieValue(name = "refresh") String jwt, @RequestBody SpecificScheduleDto dto) {
		return scheduleService.insertSpecificScheduleService(jwt, dto);
	}
	
	@PostMapping("/specific-schedule/day-off")
	public ResponseEntity insertDayOffSpecificSchedule(@CookieValue(name = "refresh") String jwt, @RequestBody DayOffSpecificScheduleDto dto) {
		return scheduleService.insertDayOffSpecificScheduleService(jwt, dto.getDateStr());
	}


}