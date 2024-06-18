package mg.motus.izygo.service;

import mg.motus.izygo.model.ReservationbyUser;
import mg.motus.izygo.repository.ReservationbyUserRepository;
import mg.motus.izygo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import java.util.List;
import java.util.Vector;

@Service
public class ReservationbyUserService {
    private final ReservationRepository reservationRepository;
    private final ReservationbyUserRepository reservationViewRepository;

    @Autowired
    public ReservationbyUserService(ReservationRepository reservationRepository, ReservationbyUserRepository reservationViewRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationViewRepository = reservationViewRepository;
    }

    public List<ReservationbyUser> getReservationsByUserId(Long userId) {
        return reservationViewRepository.findByUserId(userId);
    }

}

