package com.yaocode.sts.common.domain;

import com.yaocode.sts.common.domain.model.Aggregate;
import com.yaocode.sts.common.domain.model.Identifier;

import java.util.Optional;

/**
 * DB仓库接口
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 13:50
 */
public interface Repository<T extends Aggregate<ID>, ID extends Identifier<?>> {

    /**
     * 根据id查询数据
     * @param id 主键id
     * @return java.util.Optional<T>
     */
    Optional<T> findById(ID id);

    /**
     * 数据保存接口
     * @param aggregate 领域对象
     * @return ID
     */
    ID save(T aggregate);

    /**
     * 删除接口
     * @param aggregate 领域对象
     */
    void delete(T aggregate);
}
