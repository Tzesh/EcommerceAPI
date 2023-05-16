package com.tzesh.ecommerceapi.request.comment;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

/**
 * @author tzesh
 */
public record CreateCommentRequest(
        @NotNull Long productId,
        @NotEmpty @Length(min = 3, max = 1000) String content,
        @NotNull @Max(5) @Min(1) Integer rating
) { }
