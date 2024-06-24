package gr.aueb.cf.eshopfinalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InsertOrderDTO {
    private List<ProductsDTO> productsDTOList = new ArrayList<ProductsDTO>();
}
