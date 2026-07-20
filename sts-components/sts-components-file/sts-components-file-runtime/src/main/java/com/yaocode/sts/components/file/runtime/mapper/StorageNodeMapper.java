package com.yaocode.sts.components.file.runtime.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.file.runtime.entity.StorageNodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 存储节点Mapper
 *
 * @author yaocode
 * @since 1.0.0
 */
@Mapper
@Repository
public interface StorageNodeMapper extends BaseMapper<StorageNodeEntity> {

    /**
     * 根据节点代码查询
     */
    StorageNodeEntity selectByNodeCode(
            @Param("tenantId") String tenantId,
            @Param("nodeCode") String nodeCode
    );

    /**
     * 根据租户和启用状态查询
     */
    List<StorageNodeEntity> selectByTenantAndStatus(
            @Param("tenantId") String tenantId,
            @Param("enabledOnly") Boolean enabledOnly
    );

    /**
     * 根据存储类型查询
     */
    List<StorageNodeEntity> selectByStorageType(
            @Param("tenantId") String tenantId,
            @Param("storageType") String storageType
    );

    /**
     * 根据租户查询启用的节点
     */
    List<StorageNodeEntity> selectEnabledByTenant(
            @Param("tenantId") String tenantId
    );

    /**
     * 查询所有健康节点
     */
    List<StorageNodeEntity> selectHealthyNodes(
            @Param("tenantId") String tenantId
    );

    /**
     * 更新节点健康状态
     */
    int updateHealthStatus(
            @Param("tenantId") String tenantId,
            @Param("nodeId") Long nodeId,
            @Param("healthStatus") Integer healthStatus
    );

    /**
     * 更新节点容量
     */
    int updateCapacity(
            @Param("tenantId") String tenantId,
            @Param("nodeId") Long nodeId,
            @Param("usedCapacity") Long usedCapacity
    );

    /**
     * 根据优先级查询节点（按priority升序）
     */
    List<StorageNodeEntity> selectByPriority(
            @Param("tenantId") String tenantId,
            @Param("limit") Integer limit
    );

    /**
     * 统计租户下的节点数
     */
    int countByTenant(@Param("tenantId") String tenantId);

    /**
     * 查询所有启用的节点（按权重降序）
     */
    List<StorageNodeEntity> selectEnabledByWeight(
            @Param("tenantId") String tenantId
    );

    /**
     * 更新节点启用状态
     */
    int updateEnabled(
            @Param("tenantId") String tenantId,
            @Param("nodeId") Long nodeId,
            @Param("enabled") Boolean enabled
    );
}
