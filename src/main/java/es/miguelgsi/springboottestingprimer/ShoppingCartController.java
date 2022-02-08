package es.miguelgsi.springboottestingprimer;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


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

    @PostMapping("/v1/shopping-cart/{articleId}/purchases")
    public ResponseEntity<Void> purchaseArticle(@PathVariable Long articleId) {
        SaleId saleId = shoppingCartUseCase.purchase(new ArticleId(articleId));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/shopping-cart/{articleId}/sales/{saleId}")
                .build(articleId, saleId.getId());
        return ResponseEntity
                .created(location)
                .build();
    }
}
