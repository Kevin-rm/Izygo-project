package mg.motus.izygo.service;

import mg.motus.izygo.model.ReservationView;
import mg.motus.izygo.repository.ReservationViewRepository;
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
public class ReservationViewService {
    private final ReservationRepository reservationRepository;
    private final ReservationViewRepository reservationViewRepository;

    @Autowired
    public ReservationViewService(ReservationRepository reservationRepository, ReservationViewRepository reservationViewRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationViewRepository = reservationViewRepository;
    }

    public List<ReservationView> getReservationsByUserId(Long userId) {
        return reservationViewRepository.findByUserId(userId);
    }
    public Vector listseatreservation(Long user_id, Long reservation_id) {
        Vector result = new Vector();

        try {
            // Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/votre_base_de_donnees", "votre_nom_utilisateur", "votre_mot_de_passe");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/izygo", "postgres", "1602fy");
            String query = "SELECT user_id, reservation_id, reservation_date, bus_line, seat_label FROM v_list_reservation_seat WHERE user_id = ? AND reservation_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, user_id);
            statement.setLong(2, reservation_id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String reservationId = resultSet.getString("reservation_id");
                String reservationDate = resultSet.getString("reservation_date");
                String busLine = resultSet.getString("bus_line");
                String seatLabel = resultSet.getString("seat_label");

                String reservationInfo = "User ID: " + userId + ", Reservation ID: " + reservationId + ", Reservation Date: " + reservationDate + ", Bus Line: " + busLine + ", Seat Label: " + seatLabel;
                result.add(reservationInfo);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
