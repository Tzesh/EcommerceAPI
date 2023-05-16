package com.tzesh.ecommerceapi.service;

import com.tzesh.ecommerceapi.base.error.GenericErrorMessage;
import com.tzesh.ecommerceapi.base.exception.NotFoundException;
import com.tzesh.ecommerceapi.base.service.BaseService;
import com.tzesh.ecommerceapi.dto.CommentDTO;
import com.tzesh.ecommerceapi.dto.ProductDTO;
import com.tzesh.ecommerceapi.dto.UserDTO;
import com.tzesh.ecommerceapi.entity.Comment;
import com.tzesh.ecommerceapi.enums.message.CommentErrorMessage;
import com.tzesh.ecommerceapi.mapper.CommentMapper;
import com.tzesh.ecommerceapi.repository.CommentRepository;
import com.tzesh.ecommerceapi.request.comment.CreateCommentRequest;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for {@link Comment} objects.
 * @author tzesh
 */
@Service
public class CommentService extends BaseService<Comment, CommentDTO, CommentRepository, CommentMapper> {

    /**
     * Constructor for the service
     *
     * @param repository Repository for the service
     * @param service    UserDetailsService for the service
     * @see CommentRepository
     * @see UserDetailsService
     */
    @Autowired
    public CommentService(CommentRepository repository, UserDetailsService service) {
        super(repository, service);
    }

    @Override
    protected CommentMapper initializeMapper() {
        return Mappers.getMapper(CommentMapper.class);
    }

    /**
     * Get comments by product
     *
     * @param product ProductDTO
     * @return List of CommentDTO
     */
    public List<CommentDTO> getCommentsByProduct(ProductDTO product) {
        // get comments by product id
        List<Comment> comments = repository.getCommentsByProductId(product.getId()).get();

        // check if comments is empty
        if (comments.isEmpty()) {
            throw new NotFoundException(GenericErrorMessage.builder()
                    .message(CommentErrorMessage.COMMENT_NOT_FOUND_BY_PRODUCT.getMessage().formatted(product.getName()))
                    .build());
        }

        // return comments
        return mapper.toDTO(comments);
    }

    /**
     * Get comments by user
     *
     * @param user UserDTO
     * @return List of CommentDTO
     */
    public List<CommentDTO> getCommentsByUser(UserDTO user) {
        // get comments by user id
        List<Comment> comments = repository.getCommentsByUserId(user.getId()).get();

        // check if comments is empty
        if (comments.isEmpty()) {
            throw new NotFoundException(GenericErrorMessage.builder()
                    .message(CommentErrorMessage.COMMENT_NOT_FOUND_BY_USER.getMessage().formatted(user.getUsername()))
                    .build());
        }

        // return comments
        return mapper.toDTO(comments);
    }

    /**
     * Get comments by user and product
     *
     * @param user    UserDTO
     * @param product ProductDTO
     * @return List of CommentDTO
     */
    public List<CommentDTO> getCommentsByUserAndProduct(UserDTO user, ProductDTO product) {
        // get comments by user id and product id
        List<Comment> comments = repository.getCommentsByProductIdAndUserId(user.getId(), product.getId()).get();

        // check if comments is empty
        if (comments.isEmpty()) {
            throw new NotFoundException(GenericErrorMessage.builder()
                    .message(CommentErrorMessage.COMMENT_NOT_FOUND_BY_USER.getMessage().formatted(user.getUsername(), product.getName()))
                    .build());
        }

        // return comments
        return mapper.toDTO(comments);
    }

    /**
     * Create comment
     * @param request request
     * @param user UserDTO
     * @param product ProductDTO
     * @return CommentDTO
     */
    public CommentDTO createComment(CreateCommentRequest request, UserDTO user, ProductDTO product) {
        CommentDTO comment = this.mapper.toDTO(request);
        comment.setUser(user);
        comment.setProduct(product);

        return save(comment);
    }

    /**
     * Check if comment is owned by user
     * @param commentId comment id
     * @param user UserDTO
     */
    public void checkIfCommentIsOwnedByUser(Long commentId, UserDTO user) {
        repository.findByIdAndUserId(commentId, user.getId()).orElseThrow(
                () -> new NotFoundException(GenericErrorMessage.builder()
                        .message(CommentErrorMessage.COMMENT_NOT_FOUND_BY_USER.getMessage().formatted(user.getUsername()))
                        .build())
        );
    }
}
