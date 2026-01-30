package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源联系人持久化对象
 * @author: Jin-LiangBo
 * @date: 2026年01月30日 16:30
 */
@Data
@TableName("auth_tbl_resource_contact")
@EqualsAndHashCode(callSuper = true)
public class ContactInfoPo extends BasePo {

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
