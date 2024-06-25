package mg.motus.izygo.repository;

import mg.motus.izygo.dto.SeatDTO;
import mg.motus.izygo.model.Bus;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BusRepository extends CrudRepository<Bus, Long> {
    @Query("SELECT * FROM seat")
    List<SeatDTO> findAllBusSeats();
}
