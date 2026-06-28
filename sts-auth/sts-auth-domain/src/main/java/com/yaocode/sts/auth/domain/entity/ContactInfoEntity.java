package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.ContactId;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Getter;

/**
 * 联系人信息
 * @author: Jin-LiangBo
 * @date: 2026年01月30日 16:27
 */
@Getter
public class ContactInfoEntity extends AbstractAggregate<ContactId> {

    public ContactInfoEntity(ContactId contactId) {
        super(contactId);
    }

    /**
     * 主键id
     */
    private ContactId contactId;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 文档地址
     */
    private String docsUrl;
    /**
     * 联系人邮箱
     */
    private String contactEmail;
    /**
     * 关联资源id
     */
    private String resourceId;
}
