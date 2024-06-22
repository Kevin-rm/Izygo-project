// package mg.motus.izygo.model;

// import lombok.*;

// import jakarta.persistence.*;

// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// @ToString
// @Table(name = "v_reservationbyuser")
// public class ProfileUser {

//     @Id
//     @Column(name = "reservation_id")
//     private Long reservationId;

//     @Column(name = "user_id")
//     private Long userId;

//     @Column(name = "reservation_date")
//     private String reservationDate;

//     @Column(name = "number_of_seats")
//     private Long numberOfSeats;

//     @Column(name = "bus_line")
//     private String busLine;

//     @Column(name = "bus_id")
//     private Long busId;
// }
// package mg.motus.izygo.repository;

// import mg.motus.izygo.model.ProfileUser;
// import org.springframework.data.repository.CrudRepository;
// import org.springframework.stereotype.Repository;

// import java.util.List;

// @Repository
// public interface ProfileUserRepository extends CrudRepository<ProfileUser, Long> {
//     List<ProfileUser> findByUserId(Long userId);
// }

