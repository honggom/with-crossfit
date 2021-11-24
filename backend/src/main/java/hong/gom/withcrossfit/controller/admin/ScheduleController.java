package hong.gom.withcrossfit.controller.admin;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hong.gom.withcrossfit.dto.EachTimeDto;
import hong.gom.withcrossfit.dto.ScheduleDto;
import hong.gom.withcrossfit.entity.SpecificSchedule;
import hong.gom.withcrossfit.service.ScheduleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api")
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	@GetMapping("/now")
	public ResponseEntity<LocalDate> getNow() {
		System.out.println(LocalDate.now());
		return ResponseEntity.ok().body(LocalDate.now().plusDays(1L));
	}
	
	// TODO 메서드 완성
	@GetMapping("/specific-schedule/{start}/{end}")
	public ResponseEntity<List<SpecificSchedule>> getSpecificSchedule(@CookieValue(name = "refresh") String jwt,
									                                  @PathVariable String start, 
									                                  @PathVariable String end) {
		
		System.out.println(start);
		System.out.println(end);
		return null;
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
		return scheduleService.deleteScheduleByIdService(id);
	}


}