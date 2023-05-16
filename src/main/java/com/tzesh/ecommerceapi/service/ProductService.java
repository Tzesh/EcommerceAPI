package com.tzesh.ecommerceapi.service;

import com.tzesh.ecommerceapi.base.error.GenericErrorMessage;
import com.tzesh.ecommerceapi.base.exception.NotFoundException;
import com.tzesh.ecommerceapi.base.service.BaseService;
import com.tzesh.ecommerceapi.dto.ProductDTO;
import com.tzesh.ecommerceapi.dto.UserDTO;
import com.tzesh.ecommerceapi.entity.Product;
import com.tzesh.ecommerceapi.mapper.ProductMapper;
import com.tzesh.ecommerceapi.repository.ProductRepository;
import com.tzesh.ecommerceapi.request.product.SaveProductRequest;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tzesh
 */
@Service
public class ProductService extends BaseService<Product, ProductDTO, ProductRepository, ProductMapper> {

    /**
     * Constructor for the service
     *
     * @param repository Repository for the service
     * @param service    UserDetailsService for the service
     */
    @Autowired
    public ProductService(ProductRepository repository, UserDetailsService service) {
        super(repository, service);
    }

    @Override
    protected ProductMapper initializeMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }

    /**
     * Creates a product
     * @param request Create product request
     *                @see SaveProductRequest
     * @return ProductDTO
     */
    public ProductDTO save(SaveProductRequest request, UserDTO ownerDTO) {
        // map request to dto
        ProductDTO productDTO = this.mapper.toDTO(request);

        // set owner
        productDTO.setOwner(ownerDTO);

        // save product
        return this.save(productDTO);
    }

    /**
     * Update product price by id
     * @param id Product id
     * @param price Product price
     * @return ProductDTO
     */
    public ProductDTO updatePriceById(Long id, Double price) {
        // get product by id
        ProductDTO productDTO = this.findById(id);

        // set price
        productDTO.setPrice(price);

        // save product
        return this.save(productDTO);
    }

    /**
     * Find all products by owner id
     * @param id user id
     * @return List of ProductDTO
     */
    public List<ProductDTO> findALlByOwner(Long id) {
        return this.mapper.toDTO(this.repository.findProductsByOwnerId(id).orElse(null));
    }

    /**
     * Find by id and owner id
     * @param id product id
     * @param ownerId owner id
     * @return List of ProductDTO
     */
    public ProductDTO findByIdAndOwnerId(Long id, Long ownerId) {
        return this.mapper.toDTO(this.repository.findByIdAndOwnerId(id, ownerId).orElse(null));
    }

    /**
     * Check if product exist and belongs to owner
     * @param id product id
     * @param ownerId owner id
     * @return boolean
     * @throws RuntimeException if product does not exist or does not belong to owner
     */
    public void checkIfProductExistAndBelongsToOwner(Long id, Long ownerId) {
        this.repository.findByIdAndOwnerId(id, ownerId).orElseThrow(
                () -> new NotFoundException(
                        GenericErrorMessage.builder()
                                .message("Product does not exist or does not belong to owner")
                                .build()
                )
        );
    }
}
