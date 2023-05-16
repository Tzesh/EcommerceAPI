package com.tzesh.ecommerceapi.controller;

import com.tzesh.ecommerceapi.base.response.BaseResponse;
import com.tzesh.ecommerceapi.dto.ProductDTO;
import com.tzesh.ecommerceapi.dto.UserDTO;
import com.tzesh.ecommerceapi.request.product.SaveProductRequest;
import com.tzesh.ecommerceapi.request.user.CreateUserRequest;
import com.tzesh.ecommerceapi.service.ProductService;
import com.tzesh.ecommerceapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author tzesh
 */
@RestController
@RequestMapping("/products")
@Tag(name = "3. Product Controller", description = "Product operations")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final UserService userService;
    private final ProductService productService;

    /**
     * Get all products
     * @return the response entity
     * @see ProductService#findAll()
     */
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all products (ADMIN)", description = "Get all products and return the products")
    public ResponseEntity<BaseResponse<Iterable<ProductDTO>>> getAllProducts() {
        // call the get all method in the product service
        Iterable<ProductDTO> productDTOList = productService.findAll();

        // return the response
        return BaseResponse.ok(productDTOList).message("Products retrieved successfully").build();
    }

    /**
     * Get product by id
     * @param id the product id
     * @return the response entity
     * @see ProductService#findAll()
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get product by id (ADMIN)", description = "Get product by id and return the product")
    public ResponseEntity<BaseResponse<ProductDTO>> getProductById(@PathVariable Long id) {
        // call the get by id method in the product service
        ProductDTO productDTO = productService.findById(id);

        // return the response
        return BaseResponse.ok(productDTO).message("Product retrieved successfully").build();
    }

    /**
     * Create a new product
     * @param request the request body
     * @param ownerId the owner id
     * @return the response entity
     */
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new product (ADMIN)", description = "Create a new product with the given details and return the product")
    public ResponseEntity<BaseResponse<ProductDTO>> createProduct(@RequestBody @Valid SaveProductRequest request, Long ownerId) {
        // get user by id
        UserDTO user = userService.findById(ownerId);

        // call the save method in the product service
        ProductDTO productDTO = productService.save(request, user);

        // return the response
        return BaseResponse.create(productDTO, HttpStatus.CREATED).message("Product created successfully").build();
    }

    /**
     * Update price of a product
     * @param id the product id
     * @param price the new price
     * @return the response entity
     */
    @PutMapping("/{id}/price")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update price of a product (ADMIN)", description = "Update price of a product and return the product")
    public ResponseEntity<BaseResponse<ProductDTO>> updateProductPrice(@PathVariable Long id, @NotNull @RequestParam Double price) {
        // call the update price method in the product service
        ProductDTO productDTO = productService.updatePriceById(id, price);

        // return the response
        return BaseResponse.ok(productDTO).message("Product price updated successfully").build();
    }

    /**
     * Delete a product by id
     * @param id the product id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a product by id (ADMIN)", description = "Delete a product by id and return the product")
    public ResponseEntity<BaseResponse<ProductDTO>> deleteProductById(@PathVariable @NotNull Long id) {
        // call the delete by id method in the product service
        ProductDTO productDTO = productService.deleteById(id);

        // return the response
        return BaseResponse.ok(productDTO).message("Product deleted successfully").build();
    }

    /**
     * Create a new product owned by the current user
     * @param request the request body
     * @return the response entity
     */
    @PostMapping("/owned")
    @Operation(summary = "Create a product in the name of current user", description = "Create a product in the name of current user and return the product")
    public ResponseEntity<BaseResponse<ProductDTO>> createOwnedProduct(@RequestBody @Valid SaveProductRequest request) {
        // get the current user
        UserDTO currentUser = userService.getCurrentUserDTO();

        // call the save method in the product service
        ProductDTO productDTO = productService.save(request, currentUser);

        // return the response
        return BaseResponse.created(productDTO).message("Product created successfully").build();
    }

    /**
     * Get all products owned by the current user
     * @return the response entity
     */
    @GetMapping("/owned")
    @Operation(summary = "Get all products owned by the current user", description = "Get all products owned by the current user and return the products")
    public ResponseEntity<BaseResponse<Iterable<ProductDTO>>> getAllOwnedProducts() {
        // get the current user
        UserDTO currentUser = userService.getCurrentUserDTO();

        // call the get all method in the product service
        Iterable<ProductDTO> productDTOList = productService.findALlByOwner(currentUser.getId());

        // return the response
        return BaseResponse.ok(productDTOList).message("Products retrieved successfully").build();
    }

    /**
     * Get product by id owned by the current user
     * @param id the product id
     * @return the response entity
     */
    @GetMapping("/owned/{id}")
    @Operation(summary = "Get product by id owned by the current user", description = "Get product by id owned by the current user and return the product")
    public ResponseEntity<BaseResponse<ProductDTO>> getOwnedProductById(@PathVariable @NotNull Long id) {
        // get the current user
        UserDTO currentUser = userService.getCurrentUserDTO();

        // call the get by id method in the product service
        ProductDTO productDTO = productService.findByIdAndOwnerId(id, currentUser.getId());

        // return the response
        return BaseResponse.ok(productDTO).message("Product retrieved successfully").build();
    }

    /**
     * Update price of a product owned by the current user
     * @param id the product id
     * @param price the new price
     * @return the response entity
     */
    @PutMapping("/owned/{id}/price")
    @Operation(summary = "Update price of a product owned by the current user", description = "Update price of a product owned by the current user and return the product")
    public ResponseEntity<BaseResponse<ProductDTO>> updateOwnedProductPrice(@PathVariable Long id, @NotNull @RequestParam Double price) {
        // get the current user
        UserDTO currentUser = userService.getCurrentUserDTO();

        // check if the product exists
        productService.findByIdAndOwnerId(id, currentUser.getId());

        // call the update price method in the product service
        ProductDTO productDTO = productService.updatePriceById(id, price);

        // return the response
        return BaseResponse.ok(productDTO).message("Product price updated successfully").build();
    }

    /**
     * Delete a product by id owned by the current user
     * @param id the product id
     * @return the response entity
     */
    @DeleteMapping("/owned/{id}")
    @Operation(summary = "Delete a product by id owned by the current user", description = "Delete a product by id owned by the current user and return the product")
    public ResponseEntity<BaseResponse<ProductDTO>> deleteOwnedProductById(@PathVariable @NotNull Long id) {
        // get the current user
        UserDTO currentUser = userService.getCurrentUserDTO();

        // check if the product exists
        productService.findByIdAndOwnerId(id, currentUser.getId());

        // call the delete by id method in the product service
        ProductDTO productDTO = productService.deleteById(id);

        // return the response
        return BaseResponse.ok(productDTO).message("Product deleted successfully").build();
    }
}
