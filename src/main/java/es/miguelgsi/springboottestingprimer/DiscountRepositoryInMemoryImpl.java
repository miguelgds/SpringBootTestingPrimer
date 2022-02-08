package es.miguelgsi.springboottestingprimer;

import org.springframework.stereotype.Component;

@Component
public class DiscountRepositoryInMemoryImpl implements DiscountRepository {
    @Override
    public Short discount(Long articleId) {
        return 55;
    }
}
