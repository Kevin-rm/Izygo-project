package mg.motus.izygo.service;

import mg.motus.izygo.model.Cancellation;
import mg.motus.izygo.repository.CancellationRepository;
import org.springframework.stereotype.Service;

@Service
public class CancellationService {
    private CancellationRepository cancellationRepository;

    public CancellationService(CancellationRepository cancellationRepository) {
        this.cancellationRepository = cancellationRepository;
    }

    public Cancellation cancelReservation(Long reservationSeatId) {

        Cancellation cancellation = Cancellation.builder()
        .reservationSeatId(reservationSeatId)
        .build();
        cancellation = cancellationRepository.save(cancellation);
        // Calculate refund date (e.g., 5 days after cancellation)
        // LocalDate refundDate = LocalDate.now().plusDays(5);
        return cancellation;
    }

    public void sendNotification(Cancellation c) {
        // call to the insertNotification function
        // call to the asynchronous function that checks the reaction values
    }
}
