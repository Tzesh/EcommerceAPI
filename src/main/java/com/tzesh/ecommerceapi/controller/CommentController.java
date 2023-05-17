package com.tzesh.ecommerceapi.controller;

import com.tzesh.ecommerceapi.base.response.BaseResponse;
import com.tzesh.ecommerceapi.dto.CommentDTO;
import com.tzesh.ecommerceapi.request.comment.CreateCommentRequest;
import com.tzesh.ecommerceapi.service.CommentService;
import com.tzesh.ecommerceapi.service.ProductService;
import com.tzesh.ecommerceapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CommentController is a controller for comments
 * @author tzesh
 */
@RestController
@RequestMapping("/comments")
@Tag(name = "4. Comment Controller", description = "Comment operations")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;
    private final ProductService productService;
    private final UserService userService;

    /**
     * Get all comments
     * @return the response entity
     * @see CommentService#findAll()
     */
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all comments (ADMIN)", description = "Get all comments and return the comments")
    public ResponseEntity<BaseResponse<List<CommentDTO>>> getAllProducts() {
        // call the get all method in the product service
        List<CommentDTO> commentList = commentService.findAll();

        // return the response
        return BaseResponse.ok(commentList).message("Comments retrieved successfully").build();
    }

    /**
     * Create comment
     * @param request the request
     * @return the response entity
     */
    @PostMapping("/")
    @Operation(summary = "Create comment", description = "Create comment and return the comment")
    public ResponseEntity<BaseResponse<CommentDTO>> create(@RequestBody @Valid CreateCommentRequest request) {
        // call the create method in the comment service
        CommentDTO comment = commentService.createComment(request, userService.getCurrentUserDTO(), productService.findById(request.productId()));

        // return the response
        return BaseResponse.ok(comment).message("Comment created successfully").build();
    }

    /**
     * Get comments by product id
     * @param productId the product id
     * @return the response entity
     */
    @GetMapping("/product/{productId}")
    @Operation(summary = "Get comments by product id", description = "Get comments by product id and return the comments")
    public ResponseEntity<BaseResponse<List<CommentDTO>>> getCommentsByProductId(@NotNull @PathVariable Long productId) {
        // call the get all method in the product service
        List<CommentDTO> commentList = commentService.getCommentsByProduct(productService.findById(productId));

        // return the response
        return BaseResponse.ok(commentList).message("Comments retrieved successfully").build();
    }

    /**
     * Get comments by user id
     * @param userId the user id
     * @return the response entity
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get comments by user id", description = "Get comments by user id and return the comments")
    public ResponseEntity<BaseResponse<List<CommentDTO>>> getCommentsByUserId(@NotNull @PathVariable Long userId) {
        // call the get all method in the product service
        List<CommentDTO> commentList = commentService.getCommentsByUser(userService.findById(userId));

        // return the response
        return BaseResponse.ok(commentList).message("Comments retrieved successfully").build();
    }

    /**
     * Get comments by user id and product id
     * @param userId the user id
     * @param productId the product id
     * @return the response entity
     */
    @GetMapping("/user/{userId}/product/{productId}")
    @Operation(summary = "Get comments in given product written by given user", description = "Get comments in given product written by given user and return comments")
    public ResponseEntity<BaseResponse<List<CommentDTO>>> getCommentsByUserIdAndProductId(@NotNull @PathVariable Long userId, @NotNull @PathVariable Long productId) {
        // call the get all method in the product service
        List<CommentDTO> commentList = commentService.getCommentsByUserAndProduct(userService.findById(userId), productService.findById(productId));

        // return the response
        return BaseResponse.ok(commentList).message("Comments retrieved successfully").build();
    }

    /**
     * Delete comment by id owned by user
     * @param commentId the comment id
     * @return the response entity
     */
    @DeleteMapping("/owned/{commentId}")
    @Operation(summary = "Delete comment by id owned by user", description = "Delete comment by id owned by user and return the comment")
    public ResponseEntity<BaseResponse<CommentDTO>> deleteCommentById(@NotNull @PathVariable Long commentId) {
        // check if the comment exists
        commentService.checkIfCommentIsOwnedByUser(commentId, userService.getCurrentUserDTO());

        // call the get all method in the product service
        CommentDTO comment = commentService.deleteById(commentId);

        // return the response
        return BaseResponse.ok(comment).message("Comment deleted successfully").build();
    }

    /**
     * Delete comment by id
     * @param commentId the comment id
     * @return the response entity
     */
    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete comment by id (ADMIN)", description = "Delete comment by id and return the comment")
    public ResponseEntity<BaseResponse<CommentDTO>> deleteCommentByIdAdmin(@NotNull @PathVariable Long commentId) {
        // call the get all method in the product service
        CommentDTO comment = commentService.deleteById(commentId);

        // return the response
        return BaseResponse.ok(comment).message("Comment deleted successfully").build();
    }
}
