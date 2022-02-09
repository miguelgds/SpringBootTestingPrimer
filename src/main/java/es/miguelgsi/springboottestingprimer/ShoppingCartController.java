package es.miguelgsi.springboottestingprimer;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.Clock;
import java.time.LocalDateTime;


@RestController
@AllArgsConstructor
@Validated
public class ShoppingCartController {

    private final ShoppingCartUseCase shoppingCartUseCase;

    private final Clock clock;

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
    public ResponseEntity<Void> purchaseArticle(@PathVariable Long articleId,
                                                @RequestBody @Valid NewPurchaseDTO purchase) {
        SaleId saleId = shoppingCartUseCase.purchase(Purchase.builder()
                        .articleId(new ArticleId(articleId))
                        .username(purchase.getUsername())
                        .currency(purchase.getCurrency())
                        .occurredAt(LocalDateTime.now(clock))
                        .build());
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/shopping-cart/{articleId}/sales/{saleId}")
                .build(articleId, saleId.getId());
        return ResponseEntity
                .created(location)
                .build();
    }
}
