package com.yaocode.sts.auth.infrastructure.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.auth.infrastructure.po.RelTenantUserPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租户用户关联Mapper
 * @author: Jin-LiangBo
 * @date: 2025年10月22日 23:29
 */
@Mapper
public interface RelTenantUserMapper extends BaseMapper<RelTenantUserPo> {
}
