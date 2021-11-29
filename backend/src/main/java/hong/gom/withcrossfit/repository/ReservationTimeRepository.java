package hong.gom.withcrossfit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.entity.Reservation;
import hong.gom.withcrossfit.entity.ReservationTime;

public interface ReservationTimeRepository extends JpaRepository<ReservationTime, Long> {
	
	List<ReservationTime> findByReservation(Reservation reservation);

}
