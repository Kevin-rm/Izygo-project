package mg.motus.izygo.repository;

import mg.motus.izygo.model.ReservationSeat;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ReservationSeatRepository extends CrudRepository<ReservationSeat, Long> {
    List<ReservationSeat> findByIsActiveTrue();
}
