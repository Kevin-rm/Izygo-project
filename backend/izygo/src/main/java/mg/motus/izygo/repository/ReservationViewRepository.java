package mg.motus.izygo.repository;

import mg.motus.izygo.model.ReservationView;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationViewRepository extends CrudRepository<ReservationView, Long> {
    List<ReservationView> findByUserId(Long userId);
}
