package es.miguelgsi.springboottestingprimer.application.ports.out;

public interface DiscountRepository {
    Short discount(Long articleId);
}
