package es.miguelgsi.springboottestingprimer;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartUseCase shoppingCartUseCase;

    @GetMapping("/v1/shopping-cart/{articleId}/price")
    public ResponseEntity<ShoppingCartPriceDTO> price(@PathVariable Long articleId,
            @RequestParam(required = false, defaultValue = "EURO") Currency currency) {
        Price price = shoppingCartUseCase.price(new ArticleId(articleId), currency);
        return ResponseEntity.ok(ShoppingCartPriceDTO.builder()
                .currency(price.getCurrency().name())
                .discount(price.getDiscount())
                .price(price.getAmount())
                .build());
    }
}
