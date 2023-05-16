package com.tzesh.ecommerceapi.request.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

/**
 * RemoveUserRequest is a request class for removing a user from the database
 * @author tzesh
 */
public record RemoveUserRequest(
        @NotEmpty @Length(min = 3, max = 50) String username,
        @NotEmpty @Length(min = 10, max = 10) @Pattern(regexp="(^$|[0-9]{10})", message = "Telephone number must be 10 characters and should start with '0'") String telephone)
{ }
