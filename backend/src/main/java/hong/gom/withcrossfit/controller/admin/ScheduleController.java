package hong.gom.withcrossfit.controller.admin;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

import hong.gom.withcrossfit.dto.DayOffSpecificScheduleDto;
import hong.gom.withcrossfit.dto.ScheduleDto;
import hong.gom.withcrossfit.dto.SpecificEachTimeDto;
import hong.gom.withcrossfit.dto.SpecificScheduleDto;
import hong.gom.withcrossfit.dto.SpecificScheduleResponseDto;
import hong.gom.withcrossfit.dto.UpdateScheduleSetDto;
import hong.gom.withcrossfit.entity.EachTime;
import hong.gom.withcrossfit.entity.Schedule;
import hong.gom.withcrossfit.entity.ScheduleSet;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.entity.SpecificEachTime;
import hong.gom.withcrossfit.entity.SpecificSchedule;
import hong.gom.withcrossfit.response.ResponseDto;
import hong.gom.withcrossfit.service.ScheduleService;
import hong.gom.withcrossfit.service.SpUserService;
import hong.gom.withcrossfit.service.SpecificEachTimeService;
import hong.gom.withcrossfit.util.Converter;
import hong.gom.withcrossfit.util.DateUtil;
import hong.gom.withcrossfit.util.RegexValidator;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class ScheduleController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final ScheduleService scheduleService;
	private final SpecificEachTimeService specificEachTimeService;
	private final RegexValidator regexValidator;
	private final SpUserService spUserService;
	private final Converter converter;
	private final DateUtil dateUtil;
	private static final String YEAR_MONTH_DATE_REGEX = "[0-9]{4}:[0-9]{2}:[0-9]{2}";
	
	@GetMapping("/now")
	public ResponseEntity<LocalDate> getNow() {
		logging("now ==> " + LocalDate.now());
		return ResponseEntity.ok().body(LocalDate.now().plusDays(1L));
	}
	
	@GetMapping("/specific-schedule/{start}/{end}")
	public ResponseEntity getSpecificSchedule(@CookieValue(name = "refresh") String jwt,
									          @PathVariable String start, 
									          @PathVariable String end) {
		
		if (regexValidator.isRightValue(YEAR_MONTH_DATE_REGEX, start) && regexValidator.isRightValue(YEAR_MONTH_DATE_REGEX, end)) {
			SpUser user = spUserService.findUserByJwt(jwt);
			LocalDate startDate = dateUtil.makeToLocalDate(start);
			LocalDate endDate = dateUtil.makeToLocalDate(end);
			return new ResponseEntity(converter.convertToSpecificScheduleResponseDtoList(scheduleService.getSpecificSchedule(user.getBox(), startDate, endDate)), HttpStatus.OK);
		}
		return new ResponseEntity(new ResponseDto(400, String.format("시작날짜 : [%s] 또는 종료날짜 : [%s]의 형식이 잘못되었습니다.", start, end)), HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/specific-schedule/{date}")
	public ResponseEntity getSpecificScheduleByDate(@CookieValue(name = "refresh") String jwt, 
                                                    @PathVariable String date) {
		SpUser user = spUserService.findUserByJwt(jwt);
		
		Optional<SpecificSchedule> specificSchedule = scheduleService.getSpecificScheduleByDate(user.getBox(), dateUtil.makeToLocalDate(date));
		
		if (specificSchedule.isPresent()) {
		    SpecificScheduleResponseDto responseDto = converter.convertToSpecificScheduleResponseDto(specificSchedule.get());
		
		    List<SpecificEachTime> times = specificEachTimeService.findBySpecificSchedule(specificSchedule.get());
		    List<SpecificEachTimeDto> timesDto = converter.convertToSpecificEachTimeDtoList(times);
		
		    responseDto.setTimes(timesDto);
		
		    return new ResponseEntity(responseDto, HttpStatus.OK);
	    } else {
		    logging("getSpecificScheduleByDate ==> 별도 일정이 존재하지 않음");
		    return new ResponseEntity(HttpStatus.OK);
	    }
	}
	
	@DeleteMapping("/specific-schedule/{id}")
	public ResponseEntity deleteSpecificScheduleById(@PathVariable Long id) {
        scheduleService.deleteSpecificScheduleById(id);
        return new ResponseEntity(new ResponseDto(200, "별도 일정이 정상적으로 삭제되었습니다."), HttpStatus.OK);
	}
	
	@PostMapping("/schedule")
	public ResponseEntity insertSchedule(@CookieValue(name = "refresh") String jwt, 
			                             @RequestBody ScheduleDto scheduleDto) {
		
		if (scheduleDto == null) {
			logging("insertSchedule ==> 파라미터 scheduleDto가 null");
			new ResponseEntity(new ResponseDto(400, "오류가 발생하였습니다."), HttpStatus.BAD_REQUEST);
		}
		SpUser user = spUserService.findUserByJwt(jwt);
		scheduleService.insertSchedule(user.getBox(), scheduleDto);
		return new ResponseEntity(new ResponseDto(200, "별도 일정이 정상적으로 추가되었습니다."), HttpStatus.OK);
	}
	
	@GetMapping("/schedule")
	public ResponseEntity getScheduleByBox(@CookieValue(name = "refresh") String jwt) {
		SpUser user = spUserService.findUserByJwt(jwt);
		
		List<Schedule> schedules = scheduleService.getScheduleByBoxService(user.getBox());
		
		if (schedules == null) {
			logging("getScheduleByBox ==> results가 null");
			return new ResponseEntity(new ResponseDto(404, "기본 일정이 존재하지 않습니다."), HttpStatus.NOT_FOUND);
		} else if (schedules.isEmpty()) {
			logging("getScheduleByBox ==> results가 empty");
			return new ResponseEntity(new ResponseDto(404, "기본 일정이 존재하지 않습니다."), HttpStatus.NOT_FOUND);
		} 
		return new ResponseEntity(converter.convertToScheduleDtoList(schedules), HttpStatus.OK);
	}
	
	@GetMapping("/schedule/{id}")
	public ResponseEntity getEachTimeByScheduleId(@PathVariable Long id) {
		List<EachTime> eachTimes = scheduleService.getEachTimeByScheduleId(id);
		
		if (eachTimes == null) {
			logging("getEachTimeByScheduleId ==> results가 null");
			return new ResponseEntity(new ResponseDto(404, "기본 일정의 시간이 존재하지 않습니다."), HttpStatus.NOT_FOUND);
		} else if (eachTimes.isEmpty()) {
			logging("getEachTimeByScheduleId ==> results가 empty");
			return new ResponseEntity(new ResponseDto(404, "기본 일정의 시간이 존재하지 않습니다."), HttpStatus.NOT_FOUND);
		} 
		return new ResponseEntity(converter.convertToEachTimeDtoList(eachTimes), HttpStatus.OK); 
	}
	
	@DeleteMapping("/schedule/{id}")
	public ResponseEntity deleteScheduleById(@PathVariable Long id) {
		try {
			scheduleService.deleteScheduleByIdService(id);
			return new ResponseEntity(new ResponseDto(200, "기본 일정이 정상적으로 삭제되었습니다."), HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity(new ResponseDto(400, "해당 기본 시간표로 설정된 요일이 있습니다.(기본 시간표를 삭제하고 싶으시면 설정된 요일의 시간표를 변경해주세요.)"), HttpStatus.BAD_REQUEST);
		}
	}
	
	// TODO entity -> dto
	@GetMapping("/schedule-set")
	public ResponseEntity getScheduleSetByBox(@CookieValue(name = "refresh") String jwt) {
		SpUser user = spUserService.findUserByJwt(jwt);
		ScheduleSet result = scheduleService.getScheduleSetByBox(user.getBox());
		return new ResponseEntity(result, HttpStatus.OK);
	}
	
	@PutMapping("/schedule-set")
	public ResponseEntity updateScheduleSet(@RequestBody UpdateScheduleSetDto dto) {
		if (dto != null && dto.getDay() != null && dto.getScheduleId() != null && dto.getScheduleSetId() != null) {
			scheduleService.updateScheduleSetService(dto);
			return new ResponseEntity(new ResponseDto(200, "기본 일정이 정상적으로 변경되었습니다."), HttpStatus.OK);
		}
		logging("updateScheduleSet ==> dto가 유효하지 않음 (dto : " + dto.toString() + " )");
		return new ResponseEntity(new ResponseDto(400, "요청이 유효하지 않습니다."), HttpStatus.BAD_REQUEST);
		
	}
	
	@PostMapping("/specific-schedule")
	public ResponseEntity insertSpecificSchedule(@CookieValue(name = "refresh") String jwt, @RequestBody SpecificScheduleDto dto) {
		SpUser user = spUserService.findUserByJwt(jwt);
		scheduleService.insertSpecificSchedule(user.getBox(), dto, dateUtil.makeToLocalDate(dto.getDateStr()));
		return new ResponseEntity(new ResponseDto(200, "별도 일정이 정상적으로 저장되었습니다."), HttpStatus.OK);
	}
	
	@PostMapping("/specific-schedule/day-off")
	public ResponseEntity insertDayOffSpecificSchedule(@CookieValue(name = "refresh") String jwt, @RequestBody DayOffSpecificScheduleDto dto) {
		if (dto != null && regexValidator.isRightValue(YEAR_MONTH_DATE_REGEX, dto.getDateStr())) {
			SpUser user = spUserService.findUserByJwt(jwt);
			
			scheduleService.insertDayOffSpecificSchedule(user.getBox(), dateUtil.makeToLocalDate(dto.getDateStr()));
			return new ResponseEntity(new ResponseDto(200, "별도 휴일이 정상적으로 저장되었습니다."), HttpStatus.OK);
		}
		logging("insertDayOffSpecificSchedule ==> dto가 유효하지 않음 (dto : " + dto.toString() + " )");
		return new ResponseEntity(new ResponseDto(400, "요청이 유효하지 않습니다."), HttpStatus.BAD_REQUEST);
	}
	
	private void logging(String message) {
		LOGGER.info("ScheduleController INFO : " + message);
	}

}