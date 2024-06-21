package mg.motus.izygo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import mg.motus.izygo.dto.ReservationDTO;
import mg.motus.izygo.service.ReservationSeatService;
import mg.motus.izygo.service.TicketService;

@SpringBootApplication
public class IzygoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(IzygoApplication.class, args);
        ReservationSeatService reservationSeatService = configurableApplicationContext.getBean(ReservationSeatService.class);
        TicketService ticketService = configurableApplicationContext.getBean(TicketService.class);

        List<ReservationDTO> reservation = reservationSeatService.getReservationById(2L);
        ReservationDTO reservationDTO = reservation.get(1);

        ticketService.setReservationDTO(reservationDTO);
        ticketService.addTicketInfo();
    }

}
