package mg.motus.izygo.service;

import mg.motus.izygo.dto.ReservationDTO;
import mg.motus.izygo.model.ReservationSeat;
import mg.motus.izygo.repository.ReservationSeatRepository;
import mg.motus.izygo.utilities.Hashing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ReservationSeatService {
    private ReservationSeatRepository reservationSeatRepository;

    public ReservationSeatService(ReservationSeatRepository reservationSeatRepository) {
        this.reservationSeatRepository = reservationSeatRepository;
    }

    public List<ReservationDTO> getReservationById(Long id) {
        return reservationSeatRepository.findReservationsById(id);
    }

    public void checkReservationState(Long reservationSeatId) {
        ReservationSeat reservationSeat = reservationSeatRepository.findById(reservationSeatId).get();
        if (!reservationSeat.getIsActive()) {
            System.out.println("Aza tady ho fetsy bro");
            return;
        }
        if (reservationSeat.getIsActive() && reservationSeat.getOnBus()) {
            reservationSeat.setOnBus(false);
            reservationSeat.setIsActive(false);
        } else if (reservationSeat.getIsActive() && !reservationSeat.getOnBus()) {
            reservationSeat.setOnBus(true);
        }
        reservationSeatRepository.save(reservationSeat);
    }

    public void ticketCancelled(Long reservationSeatId){
        ReservationSeat reservationSeat = reservationSeatRepository.findById(reservationSeatId).get();
        reservationSeat.setIsActive(false);
        reservationSeatRepository.save(reservationSeat);
    }

    public Map<String, String> getTicket(Long reservationSeatId){
        Path imageDirectory = Paths.get("src/main/resources/qr_ressources/Ticket");
        String nameimg = Hashing.encodeBase64(reservationSeatId.toString()) + ".png"; // Ajoutez l'extension appropriée
        Path imagePath = imageDirectory.resolve(nameimg);
        Map<String, String> imageMap = new HashMap<>();
        if (Files.exists(imagePath)) {
            try {
                byte[] imageBytes = Files.readAllBytes(imagePath);
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                imageMap.put(nameimg, base64Image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageMap;
            
    }
}
