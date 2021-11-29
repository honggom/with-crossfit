package hong.gom.withcrossfit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hong.gom.withcrossfit.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
