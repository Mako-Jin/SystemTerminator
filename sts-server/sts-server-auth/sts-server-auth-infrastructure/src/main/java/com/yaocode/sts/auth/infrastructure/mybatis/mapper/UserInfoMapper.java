package com.yaocode.sts.auth.infrastructure.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.auth.infrastructure.po.UserInfoPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户信息db mapper
 * @version 1.0
 * @author: Jin-LiangBo
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoPo> {

    /**
     * 根据用户名查询用户信息
     * @param tenantId 租户id
     * @param username 用户名
     * @return com.yaocode.sts.auth.infrastructure.po.UserInfoPo
     */
    UserInfoPo getByUsername(String tenantId, String username);

    /**
     * 根据用户名查询用户信息
     * @param tenantIdList 租户id
     * @param username 用户名
     * @return com.yaocode.sts.auth.infrastructure.po.UserInfoPo
     */
    UserInfoPo getByUsernameInTenantIdList(List<String> tenantIdList, String username);

}
