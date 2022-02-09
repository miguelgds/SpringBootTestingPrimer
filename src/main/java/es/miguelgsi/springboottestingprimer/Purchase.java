package es.miguelgsi.springboottestingprimer;

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
