package es.miguelgsi.springboottestingprimer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "PRICE")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceJPA {
    @Id
    @Column(name = "PRICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceId;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "CURRENCY", nullable = false, length = 10)
    private String currency;
}
