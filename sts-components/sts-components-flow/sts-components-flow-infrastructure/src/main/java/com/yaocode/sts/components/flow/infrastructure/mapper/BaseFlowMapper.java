package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 基础 Mapper 接口
 * 所有 Mapper 继承此接口，统一扩展方法
 *
 * @param <T> 实体类型
 */
public interface BaseFlowMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入（需要数据库支持，MySQL 5.7+ 支持）
     */
    int batchInsert(java.util.List<T> list);

    /**
     * 批量更新（根据主键）
     */
    int batchUpdate(java.util.List<T> list);
}
