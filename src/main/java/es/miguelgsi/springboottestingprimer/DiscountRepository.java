package es.miguelgsi.springboottestingprimer;

public interface DiscountRepository {
    Short discount(Long articleId);
}
