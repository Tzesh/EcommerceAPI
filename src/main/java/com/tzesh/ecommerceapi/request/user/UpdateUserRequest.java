package com.tzesh.ecommerceapi.request.user;

import com.tzesh.ecommerceapi.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

/**
 * UpdateUserRequest is a request class for updating a user in the database
 * @author tzesh
 */
public record UpdateUserRequest(@NotEmpty @Length(min = 8, max = 128) String password,
                                @NotEmpty @Email String email,
                                @NotEmpty @Length(min = 10, max = 10) @Pattern(regexp="(^$|[0-9]{10})", message = "Telephone number must be 10 characters and should start with '0'") String telephone,
                                @NotEmpty @Length(min = 3, max = 50) String name,
                                @NotNull UserType type)
{ }