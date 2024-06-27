package gr.aueb.cf.eshopfinalproject.controllers;

import gr.aueb.cf.eshopfinalproject.dto.ProductsDTO;
import gr.aueb.cf.eshopfinalproject.dto.UserDTO;
import gr.aueb.cf.eshopfinalproject.service.IProductsService;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ProductsController handles product-related HTTP requests and responses.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {

    private final IProductsService productsService;

    /**
     * Constructs a ProductsController with the specified IProductsService.
     *
     * @param productsService the products service to be used
     */
    public ProductsController(IProductsService productsService) {
        this.productsService = productsService;
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productsId the ID of the product to be retrieved
     * @return the product with the specified ID
     */
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

    /**
     * Retrieves all products.
     *
     * @param authentication the authentication object containing the user's authentication details
     * @return a list of all products
     */
    @GetMapping("/get_all")
    public ResponseEntity<List<ProductsDTO>> getAllProducts(Authentication authentication) {
        try {
            List<ProductsDTO> productsDTOs = productsService.getAllProducts();
            System.out.println(authentication.getName());
            UserDTO userDTO = (UserDTO) authentication.getPrincipal();
            System.out.println(userDTO.getUsername());
            return ResponseEntity.ok(productsDTOs);
        } catch (IdNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
