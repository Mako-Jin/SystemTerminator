package com.yaocode.sts.auth.infrastructure.mybatis.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.auth.infrastructure.po.RoleInfoPo;

/**
 * 角色信息Dao
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:50
 */
public interface RoleInfoDao extends IService<RoleInfoPo> {

    /**
     * 查询租户下的默认权限
     * @param tenantId 租户id
     * @return RoleInfoPo
     */
    RoleInfoPo getDefaultRole(String tenantId);

}
