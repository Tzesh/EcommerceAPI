package com.tzesh.ecommerceapi.repository;

import com.tzesh.ecommerceapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * CommentRepository is a repository for Comment entity
 * @author tzesh
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * Get comments by product id
     * @param productId Product id
     * @return List of Comment
     */
    @Query("SELECT c FROM Comment c WHERE c.product.id = ?1")
    Optional<List<Comment>> getCommentsByProductId(Long productId);

    /**
     * Get comments by user id
     * @param userId User id
     * @return List of Comment
     */
    @Query("SELECT c FROM Comment c WHERE c.user.id = ?1")
    Optional<List<Comment>> getCommentsByUserId(Long userId);

    /**
     * Get comments by product id and user id
     * @param productId Product id
     * @param userId User id
     * @return List of Comment
     */
    @Query("SELECT c FROM Comment c WHERE c.product.id = ?1 AND c.user.id = ?2")
    Optional<List<Comment>> getCommentsByProductIdAndUserId(Long productId, Long userId);

    /**
     * Find comment by id and user id
     * @param id Comment id
     * @param userId User id
     * @return Comment
     */
    @Query("SELECT c FROM Comment c WHERE c.id = ?1 AND c.user.id = ?2")
    Optional<List<Comment>> findByIdAndUserId(Long id, Long userId);

}
