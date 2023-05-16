package com.tzesh.ecommerceapi.mapper;

import com.tzesh.ecommerceapi.base.mapper.AuditableMapper;
import com.tzesh.ecommerceapi.base.mapper.BaseMapper;
import com.tzesh.ecommerceapi.dto.CommentDTO;
import com.tzesh.ecommerceapi.entity.Comment;
import com.tzesh.ecommerceapi.request.comment.CreateCommentRequest;
import org.mapstruct.Mapper;

/**
 * CommentMapper is a mapper for comments
 * uses UserMapper, ProductMapper, and AuditableMapper
 * @see UserMapper
 * @see ProductMapper
 * @see AuditableMapper
 * @author tzesh
 */
@Mapper(uses = {UserMapper.class, ProductMapper.class, AuditableMapper.class})
public interface CommentMapper extends BaseMapper<Comment, CommentDTO> {
    CommentDTO toDTO(CreateCommentRequest request);
}
