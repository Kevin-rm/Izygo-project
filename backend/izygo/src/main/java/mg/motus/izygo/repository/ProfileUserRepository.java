package mg.motus.izygo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileUserRepository extends CrudRepository<Object, Long> {
    @Query(value = "SELECT * FROM v_reservationbyuser WHERE user_id = ?1", nativeQuery = true)
    List<Object> findByUserId(Long userId);
}
