package es.miguelgsi.springboottestingprimer.infrastructure.adapters.out.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = NONE)
class ProductMySqlDbJPARepositoryTestIT {

    @Container
    static MySQLContainer database = new MySQLContainer("mysql:8.0.28")
            .withDatabaseName("shop")
            .withUsername("root")
            .withPassword("root");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.flyway.enabled", () -> true);
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
    }

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