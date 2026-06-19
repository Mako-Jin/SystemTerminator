package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.MeterLogDao;
import com.yaocode.sts.components.flow.infrastructure.entity.MeterLogEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.MeterLogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 计量日志 DAO 实现
 */
@Repository
public class MeterLogDaoImpl extends BaseFlowDaoImpl<MeterLogMapper, MeterLogEntity> implements MeterLogDao {

    @Resource
    private MeterLogMapper meterLogMapper;

}
