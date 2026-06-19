package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.AuditLogDao;
import com.yaocode.sts.components.flow.infrastructure.entity.AuditLogEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.AuditLogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 审计日志 DAO 实现
 */
@Repository
public class AuditLogDaoImpl extends BaseFlowDaoImpl<AuditLogMapper, AuditLogEntity> implements AuditLogDao {

    @Resource
    private AuditLogMapper auditLogMapper;

}
