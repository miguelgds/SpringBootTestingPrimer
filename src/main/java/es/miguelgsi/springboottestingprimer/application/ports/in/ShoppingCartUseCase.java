package es.miguelgsi.springboottestingprimer.application.ports.in;

import es.miguelgsi.springboottestingprimer.domain.entities.Price;
import es.miguelgsi.springboottestingprimer.domain.entities.Purchase;
import es.miguelgsi.springboottestingprimer.domain.values.ArticleId;
import es.miguelgsi.springboottestingprimer.domain.values.Currency;
import es.miguelgsi.springboottestingprimer.domain.values.SaleId;

public interface ShoppingCartUseCase {
    Price price(ArticleId articleId, Currency currency);
    SaleId purchase(Purchase purchase);
}
