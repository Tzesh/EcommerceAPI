package com.tzesh.ecommerceapi.dto;

import com.tzesh.ecommerceapi.base.dto.BaseDTO;
import com.tzesh.ecommerceapi.entity.Product;
import com.tzesh.ecommerceapi.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * CommentDTO is a DTO for Comment
 * @author tzesh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO extends BaseDTO {

    private Long id;

    @NotBlank
    @Length(min = 3, max = 1000)
    private String content;

    @NotNull
    private ProductDTO product;

    @NotNull
    private UserDTO user;

    @NotNull
    @Size(min = 1, max = 5)
    private Integer rating;
}