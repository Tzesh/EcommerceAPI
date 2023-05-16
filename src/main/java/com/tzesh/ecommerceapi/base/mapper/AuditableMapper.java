package com.tzesh.ecommerceapi.base.mapper;

import com.tzesh.ecommerceapi.base.dto.field.BaseDTOAuditableFields;
import com.tzesh.ecommerceapi.base.entity.field.BaseAuditableFields;
import org.mapstruct.Mapper;

/**
 * @author tzesh
 */
@Mapper
public interface AuditableMapper {

    /**
     * Map the auditable fields from entity to DTO
     */
    BaseDTOAuditableFields BaseAuditableFieldsToBaseDTOAuditableFields(BaseAuditableFields entity);

    /**
     * Map the auditable fields from DTO to entity
     */
    BaseAuditableFields BaseDTOAuditableFieldsToBaseAuditableFields(BaseDTOAuditableFields dto);
}
