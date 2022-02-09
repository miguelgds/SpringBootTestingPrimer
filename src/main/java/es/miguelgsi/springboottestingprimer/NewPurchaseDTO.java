package es.miguelgsi.springboottestingprimer;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewPurchaseDTO {
    @NotBlank
    @Length(min = 5, max = 50, message = "Username field must have a size between 5 and 50")
    private String username;
    @NotNull
    private Currency currency;
}
