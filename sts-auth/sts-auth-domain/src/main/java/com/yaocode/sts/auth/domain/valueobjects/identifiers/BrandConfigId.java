package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;

public class BrandConfigId extends Identifier<String> {

    private BrandConfigId(String value) {
        super(value);
    }

    public static BrandConfigId of(String value) {
        return new BrandConfigId(value);
    }

    public static BrandConfigId nextId() {
        return new BrandConfigId(IdFactory.generate());
    }

}
