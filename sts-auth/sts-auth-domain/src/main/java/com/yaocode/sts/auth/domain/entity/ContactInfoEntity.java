package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Data;

/**
 * 联系人信息
 * @author: Jin-LiangBo
 * @date: 2026年01月30日 16:27
 */
@Data
public class ContactInfoEntity extends AbstractAggregate<OrganizationId> {

    /**
     * 主键id
     */
    private String contactId;
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
