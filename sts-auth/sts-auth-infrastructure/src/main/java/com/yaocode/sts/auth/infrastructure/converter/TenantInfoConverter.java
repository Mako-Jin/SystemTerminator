package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.TenantCode;
import com.yaocode.sts.auth.infrastructure.po.TenantInfoPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * 租户信息对象转换
 * @author: Jin-LiangBo
 * @date: 2025年10月10日 21:18
 */
@Mapper(componentModel = "spring")
public interface TenantInfoConverter {

    TenantInfoConverter INSTANCE = Mappers.getMapper(TenantInfoConverter.class);

    /**
     * entity转Po
     * @param tenantInfoEntity TenantInfoEntity
     * @return TenantInfoPo
     */
    @Mapping(target = "tenantId", source = "tenantInfoEntity.id", qualifiedByName = "tenantIdToString")
    @Mapping(target = "tenantCode", source = "tenantInfoEntity.tenantCode", qualifiedByName = "tenantCodeToString")
    TenantInfoPo toPo(TenantInfoEntity tenantInfoEntity);

    /**
     * entity转Po
     * @param tenantInfoPo TenantInfoPo
     * @return TenantInfoEntity
     */
    @Mapping(target = "tenantId", source = "tenantInfoPo.tenantId", qualifiedByName = "stringToTenantId")
    @Mapping(target = "tenantName", source = "tenantName")
    @Mapping(target = "tenantCode", source = "tenantInfoPo.tenantCode", qualifiedByName = "stringToTenantCode")
    TenantInfoEntity toEntity(TenantInfoPo tenantInfoPo);

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
     * @param tenantId String tenantId
     * @return TenantId
     */
    @Named("stringToTenantId")
    default TenantId stringToTenantId(String tenantId) {
        return tenantId != null ? TenantId.of(tenantId) : null;
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
