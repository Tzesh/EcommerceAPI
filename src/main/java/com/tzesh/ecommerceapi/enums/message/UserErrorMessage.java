package com.tzesh.ecommerceapi.enums.message;

import com.tzesh.ecommerceapi.base.error.BaseErrorMessage;
import lombok.RequiredArgsConstructor;

/**
 * UserErrorMessage is an enum for user error messages
 * @see BaseErrorMessage
 * @author tzesh
 */
@RequiredArgsConstructor
public enum UserErrorMessage implements BaseErrorMessage {
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_EXISTS("User already exists with this email, username or telephone");

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
