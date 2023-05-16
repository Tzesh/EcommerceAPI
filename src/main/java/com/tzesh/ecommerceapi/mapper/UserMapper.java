package com.tzesh.ecommerceapi.mapper;

import com.tzesh.ecommerceapi.base.mapper.AuditableMapper;
import com.tzesh.ecommerceapi.base.mapper.BaseMapper;
import com.tzesh.ecommerceapi.dto.UserDTO;
import com.tzesh.ecommerceapi.entity.User;
import org.mapstruct.Mapper;

/**
 * Mapper for {@link User}
 * @see BaseMapper
 * @author tzesh
 */
@Mapper(uses = {AuditableMapper.class})
public interface UserMapper extends BaseMapper<User, UserDTO> {
}