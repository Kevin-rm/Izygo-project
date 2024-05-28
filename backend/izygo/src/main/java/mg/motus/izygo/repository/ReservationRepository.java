package mg.motus.izygo.repository;

import mg.motus.izygo.model.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
}
