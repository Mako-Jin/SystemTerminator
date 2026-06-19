package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;

/**
 * 资源联系人持久化对象
 * @author: Jin-LiangBo
 * @date: 2026年01月30日 16:30
 */
@Data
@TableName("auth_tbl_resource_contact")
@EqualsAndHashCode(callSuper = true)
public class ResourceContactPo extends BasePo {

    /**
     * 主键id
     */
    @TableId
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
     * 联系人手机
     */
    private String contactPhone;
    /**
     * 是否为主要联系人
     */
    private Boolean isPrimary;
    /**
     * 关联资源id
     */
    private String resourceId;

}
