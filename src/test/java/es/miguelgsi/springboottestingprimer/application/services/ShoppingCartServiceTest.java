package es.miguelgsi.springboottestingprimer.application.services;

import es.miguelgsi.springboottestingprimer.domain.entities.Price;
import es.miguelgsi.springboottestingprimer.domain.entities.Purchase;
import es.miguelgsi.springboottestingprimer.application.ports.out.DiscountRepository;
import es.miguelgsi.springboottestingprimer.application.ports.out.ProductRepository;
import es.miguelgsi.springboottestingprimer.application.services.ShoppingCartService;
import es.miguelgsi.springboottestingprimer.domain.values.ArticleId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static es.miguelgsi.springboottestingprimer.domain.values.Currency.DOLLAR;
import static es.miguelgsi.springboottestingprimer.domain.values.Currency.EURO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
class ShoppingCartServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DiscountRepository discountRepository;

    @Test
    void mockito_stub_assertion() {
        ArticleId articleId = new ArticleId(1L);
        ShoppingCartService sut = new ShoppingCartService(productRepository, discountRepository);
        when(productRepository.price(articleId.getId(), EURO.name()))
                .thenReturn(new BigDecimal("50.5"));
        when(discountRepository.discount(articleId.getId()))
                .thenReturn(Short.valueOf("0"));

        Price price = sut.price(articleId, EURO);

        assertThat(price).isEqualTo(Price.builder()
                        .amount(new BigDecimal("50.5"))
                        .discount(Short.valueOf("0"))
                        .currency(EURO)
                        .build());
    }

    @Test
    void mockito_mock_assertion() {
        Purchase purchase = Purchase.builder()
                .articleId(new ArticleId(1L))
                .username("username")
                .currency(DOLLAR)
                .occurredAt(LocalDateTime.of(2022, 2, 9, 11, 30, 30))
                .build();
        ShoppingCartService sut = new ShoppingCartService(productRepository, discountRepository);

        sut.purchase(purchase);

        verify(productRepository).sale(purchase);
    }
}
