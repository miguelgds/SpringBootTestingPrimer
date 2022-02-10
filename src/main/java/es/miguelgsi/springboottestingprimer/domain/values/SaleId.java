package es.miguelgsi.springboottestingprimer.domain.values;

import lombok.Value;

import java.util.UUID;

@Value
public class SaleId {
    private final UUID id;
}
