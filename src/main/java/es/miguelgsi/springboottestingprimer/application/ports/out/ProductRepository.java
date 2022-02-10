package es.miguelgsi.springboottestingprimer.application.ports.out;

import es.miguelgsi.springboottestingprimer.domain.entities.Purchase;
import es.miguelgsi.springboottestingprimer.domain.values.SaleId;

import java.math.BigDecimal;

public interface ProductRepository {
    BigDecimal price(Long productId, String currency);
    SaleId sale(Purchase purchase);
}
