package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.dto.ProductsDTO;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;

import java.util.List;

public interface IProductsService {
    ProductsDTO getProductsById(Long id) throws IdNotFoundException;
    List<ProductsDTO> getAllProducts() throws IdNotFoundException;
}
