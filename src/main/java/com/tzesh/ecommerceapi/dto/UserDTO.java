package com.tzesh.ecommerceapi.dto;

import com.tzesh.ecommerceapi.base.dto.BaseDTO;
import com.tzesh.ecommerceapi.enums.UserType;
import com.tzesh.ecommerceapi.enums.auth.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * UserDTO is a DTO for User
 * @author tzesh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO extends BaseDTO {

    private Long id;

    @NotNull
    @Length(min = 3, max = 50)
    private String username;

    @Email
    @Length(min = 3, max = 50)
    private String email;

    @NotNull
    @Length(min = 3, max = 50)
    private String name;

    @NotNull
    @Length(min = 10, max = 10)
    @Pattern(regexp="(^$|[0-9]{10})", message = "Telephone number must be 10 characters and should start with '0'")
    private String telephone;

    @NotNull
    private UserType type;

    @NotNull
    private RoleEnum role;
}
