package com.yaocode.sts.auth.infrastructure.converter;

import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.common.domain.valueobject.TenantId;
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
    @Mapping(target = "parentId", source = "tenantInfoEntity.parentId", qualifiedByName = "tenantIdToString")
    TenantInfoPo toPo(TenantInfoEntity tenantInfoEntity);

    /**
     * entity转Po
     * @param tenantInfoPo TenantInfoPo
     * @return TenantInfoEntity
     */
    default TenantInfoEntity toEntity(TenantInfoPo tenantInfoPo) {
        return TenantInfoEntity.build(
                stringToTenantId(tenantInfoPo.getTenantId()),
                tenantInfoPo.getTenantName(),
                stringToTenantCode(tenantInfoPo.getTenantCode()),
                tenantInfoPo.getTenantDesc(),
                tenantInfoPo.getTenantStatus(),
                tenantInfoPo.getTenantLevel(),
                tenantInfoPo.getAllowRegister(),
                tenantInfoPo.getAllowAdd(),
                stringToTenantId(tenantInfoPo.getParentId())
        );
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
