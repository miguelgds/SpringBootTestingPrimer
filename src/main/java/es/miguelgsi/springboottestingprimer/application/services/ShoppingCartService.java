package es.miguelgsi.springboottestingprimer.application.services;

import es.miguelgsi.springboottestingprimer.domain.entities.Price;
import es.miguelgsi.springboottestingprimer.domain.entities.Purchase;
import es.miguelgsi.springboottestingprimer.application.ports.out.DiscountRepository;
import es.miguelgsi.springboottestingprimer.application.ports.out.ProductRepository;
import es.miguelgsi.springboottestingprimer.application.ports.in.ShoppingCartUseCase;
import es.miguelgsi.springboottestingprimer.domain.values.ArticleId;
import es.miguelgsi.springboottestingprimer.domain.values.Currency;
import es.miguelgsi.springboottestingprimer.domain.values.SaleId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ShoppingCartService implements ShoppingCartUseCase {

    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    public Price price(ArticleId articleId, Currency currency) {
        BigDecimal price = productRepository.price(articleId.getId(), currency.name());
        Short discount = discountRepository.discount(articleId.getId());
        return Price.builder()
                .amount(price)
                .currency(currency)
                .discount(discount)
                .build();
    }

    @Transactional
    public SaleId purchase(Purchase purchase) {
        return productRepository.sale(purchase);
    }
}
