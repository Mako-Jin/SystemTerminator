package com.yaocode.sts.auth.infrastructure.mybatis.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.auth.infrastructure.po.RelRoleUserPo;

import java.util.List;

/**
 * @author: Jin-LiangBo
 * @date: 2025年10月23日 22:16
 */
public interface RelRoleUserDao extends IService<RelRoleUserPo> {

    /**
     * 根据用户id和角色id列表查关联关系
     * @param tenantId 租户id
     * @param userId 用户id
     * @param roleIdlIst 角色id列表
     * @return java.util.List<RelRoleUserPo>
     */
    List<RelRoleUserPo> getByUserIdAndRoleIdList(String tenantId, String userId, List<String> roleIdlIst);

}
