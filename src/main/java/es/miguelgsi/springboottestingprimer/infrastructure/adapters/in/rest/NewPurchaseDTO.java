package es.miguelgsi.springboottestingprimer.infrastructure.adapters.in.rest;

import es.miguelgsi.springboottestingprimer.domain.values.Currency;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewPurchaseDTO {
    @NotBlank(message = "Username field is mandatory")
    @Length(min = 5, max = 50, message = "Username field must have a size between 5 and 50")
    private String username;
    @NotNull(message = "Currency field is mandatory")
    private Currency currency;
}
