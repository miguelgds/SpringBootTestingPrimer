package es.miguelgsi.springboottestingprimer.infrastructure.adapters.out.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "PRODUCT")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductJPA {
    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
    private List<PriceJPA> prices;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
    private List<SalesJPA> sales;
}
