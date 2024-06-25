package mg.motus.izygo.service;

import mg.motus.izygo.repository.ProfileUserRepository;
import mg.motus.izygo.dto.ReservationbyuserDTO;
import mg.motus.izygo.dto.ListeSeatbyuserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileUserService {

    @Autowired
    private ProfileUserRepository repository;

    public List<ReservationbyuserDTO> getReservationsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public List<ListeSeatbyuserDTO> getListeSeatsByUserId(Long userId,Long reservationId) {
        return repository.listeSeatsByUserId(userId, reservationId);
    }
}
