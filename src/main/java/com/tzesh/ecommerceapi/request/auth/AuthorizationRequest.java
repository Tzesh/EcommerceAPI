package com.tzesh.ecommerceapi.request.auth;

import com.tzesh.ecommerceapi.enums.auth.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * @author tzesh
 */
public record AuthorizationRequest(
        @NotEmpty @NotBlank @Length(min = 3, max = 50) String username,
        @NotNull RoleEnum role,
        @NotEmpty @NotBlank @Length(min = 128, max = 128) String secret)
{ }
