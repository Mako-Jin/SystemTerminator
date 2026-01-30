package com.yaocode.sts.auth.interfaces.model.params;

import com.yaocode.sts.common.resources.model.ContactInfoModel;
import com.yaocode.sts.common.web.annotation.CheckSqlInjection;
import com.yaocode.sts.common.web.annotation.CheckXss;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 批量新增资源参数对象
 * @author: Jin-LiangBo
 * @date: 2026年01月28日 19:27
 */
@Data
public class BatchCreateResourceParams {

    /**
     * 资源名称
     */
    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = "资源名称不能为空")
    private String name;

    /**
     * 资源值
     */
    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = "资源编码不能为空")
    private String code;

    /**
     * 资源描述
     */
    @CheckXss
    @CheckSqlInjection
    private String desc;

    /**
     * 资源类型：0：系统；1：服务；2：模块；3：页面；4：接口
     */
    @NotNull(message = "资源类型不能为空")
    private Integer type;

    /**
     * 接口请求地址
     */
    @CheckXss
    @CheckSqlInjection
    private List<String> path;

    /**
     * 请求方法，大写：POST,GET,PUT
     */
    @CheckXss
    @CheckSqlInjection
    private List<String> requestMethod;

    /**
     * 是否已弃用；0：未；1：已
     */
    private Integer isDeprecated;

    /**
     * 是否启用；0：未；1：已
     */
    private Integer isEnabled;

    /**
     * 是否白名单；0：不是；1：是
     */
    private Integer isWhiteList;

    /**
     * 菜单显示图标
     */
    private String icon;

    /**
     * 版本
     */
    private String version;

    /**
     * 父资源编码列表，逗号分割
     */
    private List<String> parentCode;

    /**
     * 作者信息
     */
    private List<ContactInfoModel> contactInfoModelList;

}
