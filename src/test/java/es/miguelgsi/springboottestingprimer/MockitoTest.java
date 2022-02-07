package es.miguelgsi.springboottestingprimer;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
class MockitoTest {

    @Mock
    private ProductAPI productAPI;

    @Test
    void mockito_stub_assertion() {
        String article = "iPhone 12";
        ShoppingCartService sut = new ShoppingCartService(productAPI);
        when(productAPI.hasDiscount(article))
                .thenReturn(false);

        BigDecimal price = sut.price(article);

        assertThat(price).isEqualTo(new BigDecimal("50.5"));
    }

    @Test
    void mockito_mock_assertion() {
        String article = "iPhone 12";
        ShoppingCartService sut = new ShoppingCartService(productAPI);

        sut.purchase(article);

        verify(productAPI).saleNotify(article);
    }

    @AllArgsConstructor
    public static class ShoppingCartService {

        private final ProductAPI productAPI;

        public BigDecimal price(String article) {
            if(productAPI.hasDiscount(article)) {
                return new BigDecimal("33.5");
            }
            return new BigDecimal("50.5");
        }

        public void purchase(String article) {
            productAPI.saleNotify(article);
        }
    }

    public interface ProductAPI {
        boolean hasDiscount(String article);
        void saleNotify(String article);
    }
}
