package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * ContactId值对象
 * @author: Jin-LiangBo
 * @date: 2026年02月05日 18:18
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class ContactId extends Identifier<String> {

    private ContactId(String value) {
        super(value);
    }

    public static ContactId nextId() {
        return new ContactId(IdFactory.generate());
    }

}
