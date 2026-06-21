package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;

public class StrategyId extends Identifier<String> {
    private StrategyId(String value) {
        super(value);
    }

    public static StrategyId nextId() {
        return new StrategyId(IdFactory.generate());
    }

}
