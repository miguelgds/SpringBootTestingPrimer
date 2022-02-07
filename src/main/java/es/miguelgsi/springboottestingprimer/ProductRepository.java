package es.miguelgsi.springboottestingprimer;

import java.math.BigDecimal;

public interface ProductRepository {
    default BigDecimal price(Long articleId, String currency) {
        return new BigDecimal("50.5");
    }
    void sale(Long articleId);
}
