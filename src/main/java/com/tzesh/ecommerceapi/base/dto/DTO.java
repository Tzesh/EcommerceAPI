package com.tzesh.ecommerceapi.base.dto;

import java.io.Serializable;

/**
 * Interface for DTOs
 * @see Serializable
 * @see Cloneable
 * @author tzesh
 */
public interface DTO extends Serializable, Cloneable {
    /**
     * Get id of the DTO
     * @return id of the DTO
     */
    abstract Long getId();
}
