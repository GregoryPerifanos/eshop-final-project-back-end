package gr.aueb.cf.eshopfinalproject.controllers;

import gr.aueb.cf.eshopfinalproject.dto.ProductsDTO;
import gr.aueb.cf.eshopfinalproject.service.IProductsService;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final IProductsService productsService;

    public ProductsController(IProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/get/{productsId}")
    public ResponseEntity<ProductsDTO> getProductsById(@PathVariable("productsId") Long productsId) {
        try {
            ProductsDTO products = productsService.getProductsById(productsId);
            return ResponseEntity.ok(products);
        } catch (IdNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<ProductsDTO>> getAllProducts() {
        try {
            List<ProductsDTO> productsDTOs = productsService.getAllProducts();
            return ResponseEntity.ok(productsDTOs);
        } catch (IdNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
