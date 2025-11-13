package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.common.domain.model.Identifier;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:06
 */
public class ResourceValue extends Identifier<String> {

    public ResourceValue(String value) {
        super(value);
    }

    public static ResourceValue of(String value) {
        // TODO 这块resource应该制定一个规则，树结构查询就好查，效率高
        return new ResourceValue(value);
    }

}
