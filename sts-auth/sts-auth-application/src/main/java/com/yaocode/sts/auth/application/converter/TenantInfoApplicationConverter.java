package com.yaocode.sts.auth.application.converter;

import com.yaocode.sts.auth.application.dto.TenantInfoDto;
import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.TenantCode;
import org.mapstruct.Mapper;
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
    default TenantInfoEntity toEntity(TenantInfoDto tenantInfoDto) {
        return TenantInfoEntity.build(
                tenantInfoDto.getTenantName(),
                stringToTenantCode(tenantInfoDto.getTenantCode()),
                tenantInfoDto.getTenantDesc(),
                tenantInfoDto.getTenantStatus(),
                tenantInfoDto.getTenantLevel(),
                tenantInfoDto.getAllowRegister(),
                tenantInfoDto.getAllowAdd(),
                stringToTenantId(tenantInfoDto.getParentId())
        );
    }

    /**
     * dto转po
     * @param tenantInfoDto dto
     * @return TenantInfoPo
     */
    default TenantInfoEntity toEntityForAdd(TenantInfoDto tenantInfoDto) {
        return TenantInfoEntity.build(
                tenantInfoDto.getTenantName(),
                stringToTenantCode(tenantInfoDto.getTenantCode()),
                tenantInfoDto.getTenantDesc(),
                tenantInfoDto.getTenantStatus(),
                tenantInfoDto.getTenantLevel(),
                tenantInfoDto.getAllowRegister(),
                tenantInfoDto.getAllowAdd(),
                stringToTenantId(tenantInfoDto.getTenantId())
        );
    }

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
