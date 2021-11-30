package hong.gom.withcrossfit.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.entity.ReservationTime;
import hong.gom.withcrossfit.entity.ReservationTimeRelation;
import hong.gom.withcrossfit.entity.SpUser;

public interface ReservationTimeRelationRepository extends JpaRepository<ReservationTimeRelation, Long> {
	
	Optional<ReservationTimeRelation> findByReservationTimeAndUserAndDate(ReservationTime reservationTime, SpUser user, LocalDate date);
	
	int countByReservationTime(ReservationTime reservationTime);
	
	int countByUserAndDate(SpUser user, LocalDate date);
	
	ReservationTimeRelation findByUserAndDate(SpUser user, LocalDate date);
	
	void deleteByReservationTimeAndUserAndDate(ReservationTime reservationTime, SpUser user, LocalDate date);
}
