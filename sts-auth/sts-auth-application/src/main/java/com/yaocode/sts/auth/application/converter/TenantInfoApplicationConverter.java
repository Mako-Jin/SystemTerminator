package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.TenantInfoDto;
import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.TenantCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * 租户model转换器
 * @author: Jin-LiangBo
 * @date: 2025年10月16日 23:18
 */
@Mapper(componentModel = "spring")
public interface TenantInfoApplicationConverter {

    TenantInfoApplicationConverter INSTANCE = Mappers.getMapper(TenantInfoApplicationConverter.class);

    /**
     * dto转po
     * @param tenantInfoDto dto
     * @return TenantInfoPo
     */
    @Mapping(target = "tenantId", source = "tenantInfoDto.tenantId", qualifiedByName = "stringToTenantId")
    @Mapping(target = "tenantCode", source = "tenantInfoDto.tenantCode", qualifiedByName = "stringToTenantCode")
    TenantInfoEntity toEntity(TenantInfoDto tenantInfoDto);

    /**
     * dto转po
     * @param tenantInfoDto dto
     * @return TenantInfoPo
     */
    @Mapping(target = "tenantId", qualifiedByName = "createTenantId")
    @Mapping(target = "tenantCode", source = "tenantInfoDto.tenantCode", qualifiedByName = "stringToTenantCode")
    TenantInfoEntity toEntityForAdd(TenantInfoDto tenantInfoDto);

    /**
     * 值对象与基本类型的转换方法
     * @param id String tenantId
     * @return TenantId
     */
    @Named("createTenantId")
    default TenantId createTenantId(String id) {
        return TenantId.nextId();
    }

    /**
     * 值对象与基本类型的转换方法
     * @param tenantId TenantId
     * @return String tenantId
     */
    @Named("tenantIdToString")
    default String tenantIdToString(TenantId tenantId) {
        return tenantId != null ? tenantId.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param id String tenantId
     * @return TenantId
     */
    @Named("stringToTenantId")
    default TenantId stringToTenantId(String id) {
        return id != null ? TenantId.of(id) : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param tenantCode TenantCode
     * @return String
     */
    @Named("tenantCodeToString")
    default String tenantCodeToString(TenantCode tenantCode) {
        return tenantCode != null ? tenantCode.getValue() : null;
    }

    /**
     * 值对象与基本类型的转换方法
     * @param tenantCode String
     * @return TenantCode
     */
    @Named("stringToTenantCode")
    default TenantCode stringToTenantCode(String tenantCode) {
        return tenantCode != null ? TenantCode.of(tenantCode) : null;
    }

}
