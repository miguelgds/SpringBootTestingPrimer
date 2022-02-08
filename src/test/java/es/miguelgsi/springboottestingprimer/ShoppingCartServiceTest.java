package es.miguelgsi.springboottestingprimer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static es.miguelgsi.springboottestingprimer.Currency.EURO;
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
        ArticleId articleId = new ArticleId(1L);
        ShoppingCartService sut = new ShoppingCartService(productRepository, discountRepository);

        sut.purchase(articleId);

        verify(productRepository).sale(articleId.getId());
    }
}