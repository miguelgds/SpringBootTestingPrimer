package es.miguelgsi.springboottestingprimer;

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
