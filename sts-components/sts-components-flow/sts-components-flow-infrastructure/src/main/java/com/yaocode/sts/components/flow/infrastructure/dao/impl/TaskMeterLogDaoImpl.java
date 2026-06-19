package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.TaskMeterLogDao;
import com.yaocode.sts.components.flow.infrastructure.entity.TaskMeterLogEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.TaskMeterLogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 任务计量日志 DAO 实现
 */
@Repository
public class TaskMeterLogDaoImpl extends BaseFlowDaoImpl<TaskMeterLogMapper, TaskMeterLogEntity> implements TaskMeterLogDao {

    @Resource
    private TaskMeterLogMapper taskMeterLogMapper;


}
