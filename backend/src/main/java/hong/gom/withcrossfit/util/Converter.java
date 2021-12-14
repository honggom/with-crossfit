package hong.gom.withcrossfit.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import hong.gom.withcrossfit.dto.BoxDto;
import hong.gom.withcrossfit.dto.EachTimeDto;
import hong.gom.withcrossfit.dto.MyRmDto;
import hong.gom.withcrossfit.dto.ReservationTimeDto;
import hong.gom.withcrossfit.dto.ScheduleDto;
import hong.gom.withcrossfit.dto.SpecificEachTimeDto;
import hong.gom.withcrossfit.dto.SpecificScheduleResponseDto;
import hong.gom.withcrossfit.dto.UserDto;
import hong.gom.withcrossfit.dto.WodDto;
import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.EachTime;
import hong.gom.withcrossfit.entity.MyRm;
import hong.gom.withcrossfit.entity.ReservationTime;
import hong.gom.withcrossfit.entity.Schedule;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.entity.SpecificEachTime;
import hong.gom.withcrossfit.entity.SpecificSchedule;
import hong.gom.withcrossfit.entity.Wod;
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
	
	public List<BoxDto> convertToBoxDtoList(List<Box> boxes) {
		return boxes.stream()
			        .map(box -> modelMapper.map(box, BoxDto.class))
			        .collect(Collectors.toList());
	}
	
	public List<MyRmDto> convertToMyRmDtoList(List<MyRm> rms) {
		return rms.stream()
				  .map(rm -> modelMapper.map(rm, MyRmDto.class))
				  .collect(Collectors.toList());
	}
	
	public MyRmDto convertToMyRmDto(MyRm rm) {
		return modelMapper.map(rm, MyRmDto.class);
	}
	
	public List<ReservationTimeDto> convertToReservationTimeDtoList(List<ReservationTime> times) {
		return times.stream()
			    .map(time -> modelMapper.map(time, ReservationTimeDto.class))
		        .collect(Collectors.toList());
	}
	
	public List<UserDto> convertToUserDtoList(List<SpUser> users) {
		return users.stream()
				.map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
	}
	
	public UserDto convertToUserDto(SpUser user) {
		return modelMapper.map(user, UserDto.class);
	}
	
	public WodDto convertToWodDto(Wod wod) {
		return modelMapper.map(wod, WodDto.class);
	}

}