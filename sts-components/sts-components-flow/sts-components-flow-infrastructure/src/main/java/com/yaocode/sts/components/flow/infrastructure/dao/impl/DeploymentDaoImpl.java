package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.DeploymentDao;
import com.yaocode.sts.components.flow.infrastructure.entity.DeploymentEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.DeploymentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 部署 DAO 实现
 */
@Repository
public class DeploymentDaoImpl extends BaseFlowDaoImpl<DeploymentMapper, DeploymentEntity> implements DeploymentDao {

    @Resource
    private DeploymentMapper deploymentMapper;

}
