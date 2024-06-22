// package mg.motus.izygo.service;

// import mg.motus.izygo.model.ProfileUser;
// import mg.motus.izygo.repository.ProfileUserRepository;
// import mg.motus.izygo.repository.ReservationRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.Vector;

// import java.util.List;
// import java.util.Vector;

// @Service
// public class ProfileUserService {
//     private final ReservationRepository reservationRepository;
//     private final ProfileUserRepository profileUserRepository;

//     @Autowired
//     public ProfileUserService(ReservationRepository reservationRepository,  ProfileUserRepository  profileUserRepository) {
//         this.reservationRepository = reservationRepository;
//         this.profileUserRepository = profileUserRepository;
//     }

//     public List<ProfileUser> getProfileUserId(Long userId) {
//         return profileUserRepository.findByUserId(userId);
//     }

// }

