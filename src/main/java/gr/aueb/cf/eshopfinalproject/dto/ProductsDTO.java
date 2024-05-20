package gr.aueb.cf.eshopfinalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductsDTO extends BaseDTO{
    private Long quantity;
    private String description;
    private String name;
    private String serialNumber;
    private Double price;
}
