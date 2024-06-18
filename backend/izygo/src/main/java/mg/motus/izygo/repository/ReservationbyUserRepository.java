package mg.motus.izygo.repository;

import mg.motus.izygo.model.ReservationbyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationbyUserRepository extends CrudRepository<ReservationbyUser, Long> {
    List<ReservationbyUser> findByUserId(Long userId);
}
