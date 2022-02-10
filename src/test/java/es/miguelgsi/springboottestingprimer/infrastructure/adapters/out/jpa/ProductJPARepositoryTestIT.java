package es.miguelgsi.springboottestingprimer.infrastructure.adapters.out.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProductJPARepositoryTestIT {

    @Autowired
    private ProductJPARepository productJPARepository;

    @Test
    void find_by_id_should_return_the_expected_product() {
        Optional<ProductJPA> product = productJPARepository.findById(1L);

        assertThat(product.isPresent()).isTrue();
        assertThat(product.get().getProductId()).isEqualTo(1L);
        assertThat(product.get().getName()).isEqualTo("XIAOMI EARPHONES");
        assertThat(product.get().getPrices().size()).isEqualTo(2);
        assertThat(product.get().getPrices().get(0)).isEqualTo(PriceJPA.builder()
                .priceId(100L)
                .productId(1L)
                .amount(new BigDecimal("50.50"))
                .currency("DOLLAR")
                .build());
        assertThat(product.get().getPrices().get(1)).isEqualTo(PriceJPA.builder()
                .priceId(101L)
                .productId(1L)
                .amount(new BigDecimal("50.50"))
                .currency("EURO")
                .build());
        assertThat(product.get().getSales()).isEmpty();
    }
}