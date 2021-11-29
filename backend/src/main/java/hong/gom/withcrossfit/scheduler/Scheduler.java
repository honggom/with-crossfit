package hong.gom.withcrossfit.scheduler;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.EachTime;
import hong.gom.withcrossfit.entity.Reservation;
import hong.gom.withcrossfit.entity.ReservationTime;
import hong.gom.withcrossfit.entity.Schedule;
import hong.gom.withcrossfit.entity.ScheduleSet;
import hong.gom.withcrossfit.entity.SpecificEachTime;
import hong.gom.withcrossfit.entity.SpecificSchedule;
import hong.gom.withcrossfit.repository.BoxRepository;
import hong.gom.withcrossfit.repository.EachTimeRepository;
import hong.gom.withcrossfit.repository.ReservationRepository;
import hong.gom.withcrossfit.repository.ReservationTimeRepository;
import hong.gom.withcrossfit.repository.ScheduleSetRepository;
import hong.gom.withcrossfit.repository.SpecificEachTimeRepository;
import hong.gom.withcrossfit.repository.SpecificScheduleRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Scheduler {

	private final BoxRepository boxRepository;
	private final SpecificScheduleRepository specificScheduleRepository;
	private final ScheduleSetRepository scheduleSetRepository;
	private final ReservationRepository reservationRepository;
	private final ReservationTimeRepository reservationTimeRepository;
	private final EachTimeRepository eachTimeRepository;
	private final SpecificEachTimeRepository specificEachTimeRepository;
	
	// 매일 00시에 예약 스케쥴 작성 
	@Scheduled(cron = "0 0 0 1/1 * ?")
	public void cronRun() {
		LocalDate now = LocalDate.now().plusDays(1L);
		DayOfWeek dayOfWeek = now.getDayOfWeek();

		List<Box> boxes = boxRepository.findAll();

		for (Box box : boxes) {
			Optional<SpecificSchedule> specificSchedule = specificScheduleRepository.findByBoxAndDate(box, now);

			if (specificSchedule.isPresent()) {
				if (!specificSchedule.get().isDayOff()) {
					Reservation reservation = save(box, now);
					
					List<SpecificEachTime> eachTimes = specificEachTimeRepository.findBySpecificSchedule(specificSchedule.get());
					
					for (SpecificEachTime eachTime : eachTimes) {
						saveSpecificTime(eachTime, reservation, box);
					}
				}
			} else {
				Optional<ScheduleSet> scheduleSet = scheduleSetRepository.findByBox(box);

				if (scheduleSet.isPresent()) {
					Reservation reservation = save(box, now);
					Schedule schedule = setDay(scheduleSet.get(), dayOfWeek);
					
					List<EachTime> eachTimes = eachTimeRepository.findBySchedule(schedule);
					
					for (EachTime eachTime : eachTimes) {
						saveTime(eachTime, reservation, box);
					}
				}
			}
		}
	}
	
	
	private Reservation save(Box box, LocalDate now) {
		return reservationRepository.save(Reservation.builder()
                                    .box(box)
                                    .date(now)
                                    .build());
	}
	
	private Schedule setDay(ScheduleSet scheduleSet, DayOfWeek dayOfWeek) {
		Schedule schedule = null;
		
		if (dayOfWeek.name().equals("MONDAY")) {
			schedule = scheduleSet.getMonday();
		} else if (dayOfWeek.name().equals("TUESDAY")) {
			schedule = scheduleSet.getTuesday();
		} else if (dayOfWeek.name().equals("WEDNESDAY")) {
			schedule = scheduleSet.getWednesday();
		} else if (dayOfWeek.name().equals("THURSDAY")) {
			schedule = scheduleSet.getThursday();
		} else if (dayOfWeek.name().equals("FRIDAY")) {
			schedule = scheduleSet.getFriday();
		} else if (dayOfWeek.name().equals("SATURDAY")) {
			schedule = scheduleSet.getSaturady();
		} else if (dayOfWeek.name().equals("SUNDAY")) {
			schedule = scheduleSet.getSunday();
		}
		
		return schedule;
	}
	
	private void saveTime(EachTime eachTime, Reservation reservation, Box box) {
		reservationTimeRepository.save(ReservationTime.builder()
                                 .start(eachTime.getStart())
                                 .end(eachTime.getEnd())
                                 .maxReservation(box.getMaxReservation())
                                 .reservation(reservation)
                                 .build());
	}
	
	private void saveSpecificTime(SpecificEachTime eachTime, Reservation reservation, Box box) {
		reservationTimeRepository.save(ReservationTime.builder()
                                 .start(eachTime.getStart())
                                 .end(eachTime.getEnd())
                                 .maxReservation(box.getMaxReservation())
                                 .reservation(reservation)
                                 .build());
    }
}
