package es.miguelgsi.springboottestingprimer.infrastructure.adapters.in.rest;

import es.miguelgsi.springboottestingprimer.application.ports.in.ShoppingCartUseCase;
import es.miguelgsi.springboottestingprimer.domain.entities.Price;
import es.miguelgsi.springboottestingprimer.domain.entities.Purchase;
import es.miguelgsi.springboottestingprimer.domain.values.ArticleId;
import es.miguelgsi.springboottestingprimer.domain.values.Currency;
import es.miguelgsi.springboottestingprimer.domain.values.SaleId;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
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
    @RolesAllowed("ACTIVE_USER")
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
