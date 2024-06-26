package mg.motus.izygo.service;

import mg.motus.izygo.repository.ProfileUserRepository;
import mg.motus.izygo.dto.ProfileReservationDTO;
import mg.motus.izygo.dto.ProfileReservationSeatDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileUserService {

    @Autowired
    private ProfileUserRepository profileUserRepository;

    public List<ProfileReservationDTO> getReservationsByUserId(Long userId) {
        return profileUserRepository.findReservationsByUserId(userId);
    }

    public List<ProfileReservationSeatDTO> getReservationSeatByReservationId(Long reservationId){
        return profileUserRepository.findReservationSeatByReservationId(reservationId);
    }
}
