package com.tzesh.ecommerceapi.repository;

import com.tzesh.ecommerceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for users
 * @author tzesh
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find user by email
     * @param username User email
     * @return Optional of User
     */
    Optional<User> findByUsername(String username);

    /** Check if user exists by email and username
     * @param username User email
     * @param email User email
     * @return boolean
     */
    boolean existsByUsernameOrEmail(String username, String email);

    /**
     * Check if user exists by telephone
     * @param telephone User email
     * @return boolean
     */
    boolean existsByTelephone(String telephone);

    /**
     * Find user by username and telephone
     * @param username User email
     * @param telephone User telephone
     * @return Optional of User
     */
    Optional<User> findByUsernameAndTelephone(String username, String telephone);

    /**
     * Check if email, username and telephone exists
     * @param username User email
     * @param email User email
     * @param telephone User telephone
     * @return boolean
     */
    boolean existsByUsernameOrEmailOrTelephone(String username, String email, String telephone);
}
