package com.yaocode.sts.common.tools.id;

/**
 * ID生成策略接口
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 12:11
 */
public interface IdGenerator <T>{

    /**
     * 生成id
     * @return T
     */
    T generate();

    /**
     * 生成器名称
     * @return java.lang.String
     */
    String getName();

    /**
     * 生成器类别
     * @return IdGeneratorType
     */
    IdGeneratorType getType();

}
