package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.ResourceDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.ResourceMapper;
import com.yaocode.sts.auth.infrastructure.po.ResourcePo;
import org.springframework.stereotype.Service;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:26
 */
@Service
public class ResourceDaoImpl extends ServiceImpl<ResourceMapper, ResourcePo> implements ResourceDao {
}
