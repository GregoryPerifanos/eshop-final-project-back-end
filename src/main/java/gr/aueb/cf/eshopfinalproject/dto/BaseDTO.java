package gr.aueb.cf.eshopfinalproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@NoArgsConstructor
@Getter
@Setter
public abstract class BaseDTO {

    @NotNull
    private Long id;

}
