package es.miguelgsi.springboottestingprimer;

public interface ShoppingCartUseCase {
    Price price(ArticleId articleId, Currency currency);
    SaleId purchase(ArticleId articleId);
}
