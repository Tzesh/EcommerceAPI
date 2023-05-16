package com.tzesh.ecommerceapi.dto;

import com.tzesh.ecommerceapi.base.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * ProductDTO is a DTO for Product
 * @author tzesh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO extends BaseDTO {

        private Long id;

        @NotNull
        @Length(min = 3, max = 100)
        private String name;

        @NotNull
        @Length(min = 3, max = 1000)
        private String description;

        @NotNull
        private Double price;

        @NotNull
        private UserDTO owner;
}
