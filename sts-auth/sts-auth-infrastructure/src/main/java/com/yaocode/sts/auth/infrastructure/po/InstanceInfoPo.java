package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;

@Data
@TableName("auth_tbl_resource_contact")
@EqualsAndHashCode(callSuper = true)
public class InstanceInfoPo extends BasePo {

    /**
     * 实例id
     */
    @TableId
    private String instanceId;
    /**
     * 关联租户
     */
    private String tenantId;
    /**
     * 实例名称
     */
    private String instanceName;
    /**
     * 部署区域
     */
    private String region;
    /**
     * 服务器地址
     */
    private String serverAddress;
    /**
     * 服务端口
     */
    private Integer serverPort;

    /**
     * 运行状态
     */
    private Integer status;

    /**
     * 实例类型（prod/test/dev）
     */
    private String instanceType;

    /**
     * 环境标识
     */
    private String environment;

    /**
     * 版本号
     */
    private String version;

    /**
     * 描述
     */
    private String description;
}
