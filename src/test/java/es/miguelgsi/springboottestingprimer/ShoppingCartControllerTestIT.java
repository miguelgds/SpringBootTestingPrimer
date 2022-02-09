package es.miguelgsi.springboottestingprimer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import static es.miguelgsi.springboottestingprimer.Currency.DOLLAR;
import static es.miguelgsi.springboottestingprimer.Currency.EURO;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShoppingCartController.class)
@Import(FixedClockConfig.class)
class ShoppingCartControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartUseCase shoppingCartUseCase;

    @Autowired
    private Clock clock;

    @Nested
    @DisplayName("Test related with price use case")
    class CartControllerPriceTest {
        @Test
        void price_for_existing_article_should_return_the_article() throws Exception {
            when(shoppingCartUseCase.price(new ArticleId(1L), DOLLAR))
                    .thenReturn(Price.builder()
                            .currency(DOLLAR)
                            .discount(Short.valueOf("0"))
                            .amount(new BigDecimal("50.5"))
                            .build());
            mockMvc.perform(get("/v1/shopping-cart/1/price")
                            .param("currency", "DOLLAR"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.price", is(50.5)))
                    .andExpect(jsonPath("$.discount", is(0)))
                    .andExpect(jsonPath("$.currency", is("DOLLAR")));
        }

        @Test
        void price_with_not_found_managed_exception_should_return_not_found() throws Exception {
            when(shoppingCartUseCase.price(new ArticleId(1L), EURO))
                    .thenThrow(new ResourceNotFoundException());
            mockMvc.perform(get("/v1/shopping-cart/1/price"))
                    .andExpect(status().isNotFound());
        }

        @Test
        void price_with_globally_handled_exception_should_return_internal_server_error() throws Exception {
            when(shoppingCartUseCase.price(new ArticleId(1L), EURO))
                    .thenThrow(new IllegalArgumentException());
            mockMvc.perform(get("/v1/shopping-cart/1/price"))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    @DisplayName("Test related with purchaseArticle use case")
    class CartControllerPurchaseArticleTest {
        @Test
        void purchase_article_with_valid_data_should_create_resource() throws Exception {
            Purchase purchase = Purchase.builder()
                    .articleId(new ArticleId(1L))
                    .username("username")
                    .currency(DOLLAR)
                    .occurredAt(LocalDateTime.now(clock))
                    .build();
            SaleId saleId = new SaleId(UUID.randomUUID());
            when(shoppingCartUseCase.purchase(purchase))
                    .thenReturn(saleId);

            mockMvc.perform(post("/v1/shopping-cart/1/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"username\", \"currency\": \"DOLLAR\"}")
                        .with(csrf()))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("location", "http://localhost/v1/shopping-cart/1/sales/" + saleId.getId().toString()));

            verify(shoppingCartUseCase).purchase(purchase);
        }
    }
}