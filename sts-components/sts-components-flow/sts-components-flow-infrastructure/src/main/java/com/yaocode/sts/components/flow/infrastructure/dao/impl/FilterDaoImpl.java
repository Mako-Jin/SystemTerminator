package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.FilterDao;
import com.yaocode.sts.components.flow.infrastructure.entity.FilterEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.FilterMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 过滤器 DAO 实现
 */
@Repository
public class FilterDaoImpl extends BaseFlowDaoImpl<FilterMapper, FilterEntity> implements FilterDao {

    @Resource
    private FilterMapper filterMapper;


}
