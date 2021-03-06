package es.miguelgsi.springboottestingprimer.infrastructure.adapters.out.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "SALES")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesJPA {
    @Id
    @Column(name = "SALE_ID")
    private UUID saleId;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;

    @Column(name = "OCCURRED_AT", nullable = false)
    private LocalDateTime occurredAt;
}
