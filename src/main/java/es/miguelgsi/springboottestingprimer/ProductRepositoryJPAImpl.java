package es.miguelgsi.springboottestingprimer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ProductRepositoryJPAImpl implements ProductRepository {

    private final ProductJPARepository productJPARepository;

    @Override
    public BigDecimal price(Long productId, String currency) {
        return productJPARepository.findById(productId)
                .map(ProductJPA::getPrices)
                .stream()
                .flatMap(x -> x.stream())
                .filter(price -> Objects.equals(price.getCurrency(), currency))
                .map(PriceJPA::getAmount)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    @Override
    public SaleId sale(Purchase purchase) {
        UUID saleId = UUID.randomUUID();
        SalesJPA newSale = new SalesJPA();
        newSale.setSaleId(saleId);
        newSale.setProductId(purchase.getArticleId().getId());
        newSale.setUsername(purchase.getUsername());
        newSale.setOccurredAt(purchase.getOccurredAt());
        productJPARepository.findById(purchase.getArticleId().getId()).ifPresentOrElse(
                    product -> product.getSales().add(newSale),
                    () -> {
                        throw new ResourceNotFoundException();
                    }
                );
        return new SaleId(saleId);
    }
}
