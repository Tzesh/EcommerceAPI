package com.tzesh.ecommerceapi.enums;

/**
 * UserType is an enum for user types
 * @author tzesh
 */
public enum UserType {
    CUSTOMER,
    COMPANY;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
