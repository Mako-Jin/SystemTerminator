package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;

public class StrategyId extends Identifier<String> {
    private StrategyId(String value) {
        super(value);
    }
}
