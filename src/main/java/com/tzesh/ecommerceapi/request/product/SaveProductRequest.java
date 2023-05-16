package com.tzesh.ecommerceapi.request.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * CreateProductRequest is a request for creating a product
 * @author tzesh
 */
public record SaveProductRequest(
        @NotEmpty @Length(min = 3, max = 100) String name,
        @NotEmpty @Length(min = 3, max = 1000) String description,
        @NotNull Double price
)
{ }