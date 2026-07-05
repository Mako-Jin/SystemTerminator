package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 紧急联系人关系
 */
@Getter
public enum RelationshipEnums {

    FATHER(1, "父亲"),
    MOTHER(2, "母亲"),
    SPOUSE(3, "配偶"),
    CHILD(4, "子女"),
    SIBLING(5, "兄弟姐妹"),
    FRIEND(6, "朋友"),
    OTHER(99, "其他");

    private final int code;
    private final String desc;

    RelationshipEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static RelationshipEnums fromCode(int code) {
        for (RelationshipEnums rel : values()) {
            if (rel.code == code) {
                return rel;
            }
        }
        return OTHER;
    }
}
