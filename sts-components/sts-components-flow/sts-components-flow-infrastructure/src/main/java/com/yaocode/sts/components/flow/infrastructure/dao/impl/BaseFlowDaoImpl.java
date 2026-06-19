package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.components.flow.infrastructure.dao.BaseFlowDao;

/**
 * 基础 Service 实现
 *
 * @param <M> Mapper 类型
 * @param <T> 实体类型
 */
public abstract class BaseFlowDaoImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseFlowDao<T> {
}
