package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限持久化对象
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:08
 */

@Data
@TableName("auth_tbl_auth")
@EqualsAndHashCode(callSuper = true)
public class AuthInfoPo extends BasePo {

    /**
     * 主键id,权限id
     */
    @TableId
    private String authId;

    /**
     * 权限名称
     */
    private String authName;

    /**
     * 权限值
     */
    private String authValue;

    /**
     * 权限描述
     */
    private String authDesc;

    /**
     * 权限类型：0：系统；1：服务；2：模块；3：页面；4：接口
     */
    private Integer authType;

    /**
     * 接口请求地址
     */
    private String requestUrl;

    /**
     * 请求方法，大写：POST,GET,PUT
     */
    private String requestMethod;

    /**
     * 是否已弃用；0：未；1：已
     */
    private Integer isDeprecated;

    /**
     * 是否白名单；0：不是；1：是
     */
    private Integer isWhiteList;

    /**
     * 菜单显示图标
     */
    private String menuIcon;

}
