package gr.aueb.cf.eshopfinalproject.service;

import gr.aueb.cf.eshopfinalproject.dto.ProductsDTO;
import gr.aueb.cf.eshopfinalproject.model.Products;
import gr.aueb.cf.eshopfinalproject.repository.ProductsRepository;
import gr.aueb.cf.eshopfinalproject.service.exceptions.IdNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the IProductsService interface, providing services related to products.
 */
@Service
@Slf4j
public class ProductsServiceImpl implements IProductsService {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return the retrieved product as a ProductsDTO
     * @throws IdNotFoundException if no product with the specified ID is found
     */
    @Transactional
    @Override
    public ProductsDTO getProductsById(Long id) throws IdNotFoundException {
        try {
            Optional<Products> products = productsRepository.findById(id);
            if (products.isPresent()) {
                Products product = products.get();
                return convertToProductsDTO(product);
            } else {
                throw new IdNotFoundException(Products.class, id);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all products as ProductsDTOs
     */
    @Transactional
    @Override
    public List<ProductsDTO> getAllProducts() {
        List<Products> products = productsRepository.findAll();
        List<ProductsDTO> productsDTOs = new ArrayList<>();

        for (Products product : products) {
            productsDTOs.add(convertToProductsDTO(product));
        }
        return productsDTOs;
    }

    /**
     * Converts a ProductsDTO to a Products entity.
     *
     * @param productsDTO the DTO to convert
     * @return the converted Products entity
     */
    private Products convertToProducts(ProductsDTO productsDTO) {
        Products products = new Products();
        products.setName(productsDTO.getName());
        products.setPrice(productsDTO.getPrice());
        products.setDescription(productsDTO.getDescription());
        products.setQuantity(productsDTO.getQuantity());
        products.setSerialNumber(productsDTO.getSerialNumber());
        return products;
    }

    /**
     * Converts a Products entity to a ProductsDTO.
     *
     * @param products the entity to convert
     * @return the converted ProductsDTO
     */
    private ProductsDTO convertToProductsDTO(Products products) {
        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.setId(products.getId());
        productsDTO.setName(products.getName());
        productsDTO.setPrice(products.getPrice());
        productsDTO.setDescription(products.getDescription());
        productsDTO.setQuantity(products.getQuantity());
        productsDTO.setSerialNumber(products.getSerialNumber());
        return productsDTO;
    }
}
