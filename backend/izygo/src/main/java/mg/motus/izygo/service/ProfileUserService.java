package mg.motus.izygo.service;

import mg.motus.izygo.repository.ProfileUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileUserService {

    @Autowired
    private ProfileUserRepository repository;

    public List<Object> getReservationsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
