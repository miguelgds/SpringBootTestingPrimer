package es.miguelgsi.springboottestingprimer.infrastructure.adapters.out.inmemory;

import es.miguelgsi.springboottestingprimer.application.ports.out.DiscountRepository;
import org.springframework.stereotype.Component;

@Component
public class DiscountRepositoryInMemoryImpl implements DiscountRepository {
    @Override
    public Short discount(Long articleId) {
        return 55;
    }
}
