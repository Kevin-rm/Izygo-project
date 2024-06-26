package mg.motus.izygo.service;

import mg.motus.izygo.model.Cancellation;
import mg.motus.izygo.repository.CancellationRepository;
import org.springframework.stereotype.Service;


@Service
public class CancellationService {
    private CancellationRepository cancellationRepository;
    private ReservationSeatService reservationSeatService;

    public CancellationService(CancellationRepository cancellationRepository,ReservationSeatService reservationSeatService) {
        this.cancellationRepository = cancellationRepository;
        this.reservationSeatService = reservationSeatService;
    }

    public Cancellation cancelReservation(Long reservationSeatId) {

        Cancellation cancellation = Cancellation.builder()
        .reservationSeatId(reservationSeatId)
        .build();
        cancellation = cancellationRepository.save(cancellation);
        // Calculate refund date (e.g., 5 days after cancellation)
        // LocalDate refundDate = LocalDate.now().plusDays(5);
        reservationSeatService.ticketCancelled(reservationSeatId);
        return cancellation;
    }
}
