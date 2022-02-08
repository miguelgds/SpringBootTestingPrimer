package es.miguelgsi.springboottestingprimer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;

import static es.miguelgsi.springboottestingprimer.Currency.DOLLAR;
import static es.miguelgsi.springboottestingprimer.Currency.EURO;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShoppingCartController.class)
class ShoppingCartControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartUseCase shoppingCartUseCase;

    @Test
    void price_for_existing_article_should_return_the_article() throws Exception {
        Mockito.when(shoppingCartUseCase.price(new ArticleId(1L), DOLLAR))
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
        Mockito.when(shoppingCartUseCase.price(new ArticleId(1L), EURO))
                .thenThrow(new ResourceNotFoundException());
        mockMvc.perform(get("/v1/shopping-cart/1/price"))
                .andExpect(status().isNotFound());
    }

    @Test
    void price_with_globally_handled_exception_should_return_internal_server_error() throws Exception {
        Mockito.when(shoppingCartUseCase.price(new ArticleId(1L), EURO))
                .thenThrow(new IllegalArgumentException());
        mockMvc.perform(get("/v1/shopping-cart/1/price"))
                .andExpect(status().isInternalServerError());
    }
}