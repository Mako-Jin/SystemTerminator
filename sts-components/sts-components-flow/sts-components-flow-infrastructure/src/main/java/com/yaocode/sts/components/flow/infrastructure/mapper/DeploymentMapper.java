package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaocode.sts.components.flow.infrastructure.entity.DeploymentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部署 Mapper
 * 对应表: flow_tbl_deployment
 */
@Mapper
public interface DeploymentMapper extends BaseFlowMapper<DeploymentEntity> {

    /**
     * 分页查询部署列表
     */
    IPage<DeploymentEntity> selectPageList(
            Page<DeploymentEntity> page,
            @Param("deploymentName") String deploymentName
    );

    /**
     * 根据部署名称查询
     */
    List<DeploymentEntity> selectByDeploymentName(@Param("deploymentName") String deploymentName);

    /**
     * 查询最近部署的记录
     */
    List<DeploymentEntity> selectLatest(@Param("limit") int limit);
}
