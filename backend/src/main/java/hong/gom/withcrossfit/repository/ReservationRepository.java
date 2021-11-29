package hong.gom.withcrossfit.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.entity.Box;
import hong.gom.withcrossfit.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	Reservation findByDateAndBox(LocalDate date, Box box); 
	
}
