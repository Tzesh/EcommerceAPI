package com.tzesh.ecommerceapi.mapper;

import com.tzesh.ecommerceapi.base.mapper.AuditableMapper;
import com.tzesh.ecommerceapi.base.mapper.BaseMapper;
import com.tzesh.ecommerceapi.dto.ProductDTO;
import com.tzesh.ecommerceapi.entity.Product;
import com.tzesh.ecommerceapi.request.product.SaveProductRequest;
import org.mapstruct.Mapper;

/**
 * ProductMapper is a mapper for Product entity
 * @author tzesh
 */
@Mapper(uses = {UserMapper.class, AuditableMapper.class})
public interface ProductMapper extends BaseMapper<Product, ProductDTO> {
    ProductDTO toDTO(SaveProductRequest request);
}
