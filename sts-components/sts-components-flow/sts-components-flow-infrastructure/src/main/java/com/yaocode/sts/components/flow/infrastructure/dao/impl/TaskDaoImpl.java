package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.TaskDao;
import com.yaocode.sts.components.flow.infrastructure.entity.TaskEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.TaskMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 任务 DAO 实现
 */
@Repository
public class TaskDaoImpl extends BaseFlowDaoImpl<TaskMapper, TaskEntity> implements TaskDao {

    @Resource
    private TaskMapper taskMapper;


}
