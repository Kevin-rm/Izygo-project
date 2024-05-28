package mg.motus.izygo.repository;

import mg.motus.izygo.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
