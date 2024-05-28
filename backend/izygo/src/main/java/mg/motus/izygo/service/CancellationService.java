package mg.motus.izygo.service;

import mg.motus.izygo.repository.CancellationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancellationService {
    private CancellationRepository cancellationRepository;

    @Autowired
    public CancellationService(CancellationRepository cancellationRepository) {
        this.cancellationRepository = cancellationRepository;
    }
}
