package hong.gom.withcrossfit.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import hong.gom.withcrossfit.dto.EachTimeDto;
import hong.gom.withcrossfit.dto.ScheduleDto;
import hong.gom.withcrossfit.dto.SpecificEachTimeDto;
import hong.gom.withcrossfit.dto.SpecificScheduleResponseDto;
import hong.gom.withcrossfit.entity.EachTime;
import hong.gom.withcrossfit.entity.Schedule;
import hong.gom.withcrossfit.entity.SpecificEachTime;
import hong.gom.withcrossfit.entity.SpecificSchedule;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Converter {
	
	private final ModelMapper modelMapper;
	
	public SpecificScheduleResponseDto convertToSpecificScheduleResponseDto(SpecificSchedule specificSchedule) {
	    return modelMapper.map(specificSchedule, SpecificScheduleResponseDto.class);	
	}
	
	public List<SpecificScheduleResponseDto> convertToSpecificScheduleResponseDtoList(List<SpecificSchedule> schedules) {
		return schedules.stream()
			            .map(schedule -> modelMapper.map(schedule, SpecificScheduleResponseDto.class))
			            .collect(Collectors.toList());
	}
	
	public List<SpecificEachTimeDto> convertToSpecificEachTimeDtoList(List<SpecificEachTime> times) {
		return times.stream()
	                .map(time -> modelMapper.map(time, SpecificEachTimeDto.class))
	                .collect(Collectors.toList());
	}
	
	public List<ScheduleDto> convertToScheduleDtoList(List<Schedule> schedules) {
		return schedules.stream()
	                    .map(schedule -> modelMapper.map(schedule, ScheduleDto.class))
	                    .collect(Collectors.toList());
	}
	
	public List<EachTimeDto> convertToEachTimeDtoList(List<EachTime> eachTimes) {
		return eachTimes.stream()
	                    .map(eachTime -> modelMapper.map(eachTime, EachTimeDto.class))
	                    .collect(Collectors.toList());
	}
	

}
