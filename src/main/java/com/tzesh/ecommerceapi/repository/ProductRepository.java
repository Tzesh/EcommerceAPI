package com.tzesh.ecommerceapi.repository;

import com.tzesh.ecommerceapi.entity.Product;
import com.tzesh.ecommerceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ProductRepository is a repository for Product entity
 * @author tzesh
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Check if product exists by id and owner
     * @param id Product id
     * @param owner Product owner id
     * @return boolean
     */
    Optional<Boolean> existsByIdAndOwner(Long id, Long owner);

    /**
     * Find product by id and owner
     * @param owner Product owner
     * @return Optional of Product
     */
    @Query("SELECT p FROM Product p WHERE p.owner.id = ?1")
    Optional<List<Product>> findProductsByOwnerId(Long owner);

    /**
     * Find product by id and owner
     * @param id Product id
     * @param ownerId Product owner id
     * @return Optional of Product
     */
    @Query("SELECT p FROM Product p WHERE p.id = ?1 AND p.owner.id = ?2")
    Optional<Product> findByIdAndOwnerId(Long id, Long ownerId);
}
