package com.yaocode.sts.common.domain.model;

/**
 * 领域对象实体接口
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:03
 */
public interface Entity<ID> {

    /**
     * 获取主键id
     * @return ID
     */
    ID getId();

    /**
     * 相等比较方法
     * @param obj 比较对象
     * @return boolean
     */
    @Override
    boolean equals(Object obj);

    /**
     * hash方法
     * @return int
     */
    @Override
    int hashCode();

}
