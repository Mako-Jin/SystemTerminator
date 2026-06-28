package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.BlackWhiteListEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.BlackWhiteListId;
import com.yaocode.sts.common.domain.Repository;

import java.util.List;

public interface BlackWhiteListRepository extends Repository<BlackWhiteListEntity, BlackWhiteListId> {

    /**
     * 查询租户级或平台级的黑白名单规则
     */
    List<BlackWhiteListEntity> findByTenantIdOrNullAndEnabled(String tenantId);

    /**
     * 查询租户级黑白名单
     */
    List<BlackWhiteListEntity> findByTenantIdAndEnabled(String tenantId);

    /**
     * 查询平台级黑白名单
     */
    List<BlackWhiteListEntity> findByTenantIdIsNullAndEnabled();

    /**
     * 保存规则
     *
     * @return 规则ID
     */
    BlackWhiteListId save(BlackWhiteListEntity rule);

    /**
     * 删除规则
     */
    void deleteById(String listId);

}
