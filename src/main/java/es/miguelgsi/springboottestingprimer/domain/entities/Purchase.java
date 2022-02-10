package es.miguelgsi.springboottestingprimer.domain.entities;

import es.miguelgsi.springboottestingprimer.domain.values.ArticleId;
import es.miguelgsi.springboottestingprimer.domain.values.Currency;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class Purchase {
    private final ArticleId articleId;
    private final String username;
    private final Currency currency;
    private final LocalDateTime occurredAt;
}
