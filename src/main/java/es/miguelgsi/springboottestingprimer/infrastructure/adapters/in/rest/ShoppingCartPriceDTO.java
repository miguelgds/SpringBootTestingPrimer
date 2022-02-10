package es.miguelgsi.springboottestingprimer.infrastructure.adapters.in.rest;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ShoppingCartPriceDTO {
    private BigDecimal price;
    private Short discount;
    private String currency;
}
