package com.tzesh.ecommerceapi.enums.message;

import com.tzesh.ecommerceapi.base.error.BaseErrorMessage;
import lombok.RequiredArgsConstructor;

/**
 * CommentErrorMessage is an enum for comment error messages
 * @author tzesh
 */
@RequiredArgsConstructor
public enum CommentErrorMessage implements BaseErrorMessage {
    COMMENT_NOT_FOUND_BY_USER("Comment not found with user %s"),
    COMMENT_NOT_FOUND_BY_PRODUCT("No comment found with product %s");

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
