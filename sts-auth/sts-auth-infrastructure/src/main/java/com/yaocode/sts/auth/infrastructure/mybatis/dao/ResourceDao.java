package com.yaocode.sts.auth.infrastructure.mybatis.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.auth.infrastructure.po.ResourcePo;

import java.util.List;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:26
 */
public interface ResourceDao extends IService<ResourcePo> {

    /**
     * 检查资源是否存在
     * @param resourcePo 资源数据
     * @return ResourcePo
     */
    ResourcePo getByPo(ResourcePo resourcePo);

    /**
     * 根据列表查询列表
     * @param resourcePoList sourceDataList
     * @return java.util.List<ResourcePo>
     */
    List<ResourcePo> getByPoList(List<ResourcePo> resourcePoList);

}
