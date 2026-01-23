package com.yaocode.sts.common.resources.model;

import lombok.Data;

/**
 * 联系人信息
 * @author: Jin-LiangBo
 * @date: 2025年12月08日 22:35
 */
@Data
public class ContactInfoModel {

    /**
     * 联系人名称
     */
    private String name;
    /**
     * 文档地址
     */
    private String docsUrl;
    /**
     * 联系人邮箱
     */
    private String email;

}
