package com.yaocode.sts.auth.interfaces.assembler;

import com.yaocode.sts.auth.application.dto.TenantInfoDto;
import com.yaocode.sts.auth.interfaces.model.params.CreateTenantParams;
import com.yaocode.sts.auth.interfaces.model.vo.TenantInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 租户信息装饰器
 * @author: Jin-LiangBo
 * @date: 2025年10月16日 23:06
 */
@Mapper(componentModel = "spring")
public interface TenantInfoAssembler {

    TenantInfoAssembler INSTANCE = Mappers.getMapper(TenantInfoAssembler.class);

    /**
     * 参数对象转dto
     * @param tenantParams 参数对象
     * @return TenantInfoDto
     */
    TenantInfoDto toDto(CreateTenantParams tenantParams);

    /**
     * dto转Vo
     * @param tenantInfoDto 数据传输对象
     * @return TenantInfoVo
     */
    TenantInfoVo toVo(TenantInfoDto tenantInfoDto);

}
