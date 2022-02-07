package es.miguelgsi.springboottestingprimer;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Price {
    private final BigDecimal amount;
    private final Currency currency;
    private final Short discount;
}
