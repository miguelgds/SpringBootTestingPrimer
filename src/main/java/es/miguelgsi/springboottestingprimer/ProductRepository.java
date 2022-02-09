package es.miguelgsi.springboottestingprimer;

import java.math.BigDecimal;

public interface ProductRepository {
    BigDecimal price(Long productId, String currency);
    SaleId sale(Purchase purchase);
}
