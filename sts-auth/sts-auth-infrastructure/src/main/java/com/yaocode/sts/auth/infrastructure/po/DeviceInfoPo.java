package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@TableName("auth_tbl_device_info")
@EqualsAndHashCode(callSuper = true)
public class DeviceInfoPo extends BasePo {

    /**
     * 设备ID
     */
    @TableId
    private String deviceId;
    /**
     * IOS, ANDROID, WEB, WECHAT_MINI
     */
    private Integer deviceType;
    /**
     * 所属用户
     */
    private String userId;

    /**
     * 所属租户
     */
    private String tenantId;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 操作系统
     */
    private String osName;
    /**
     * 系统版本
     */
    private String osVersion;
    /**
     * 应用版本
     */
    private String appVersion;
    /**
     * IP地址
     */
    private String lastIpAddress;
    /**
     * 最后活跃时间
     */
    private LocalDateTime lastActiveTime;
    /**
     * User Agent
     */
    private String userAgent;
    /**
     * 设备指纹
     */
    private String deviceFingerprint;
    /**
     * 是否信任设备
     */
    private Integer isTrusted;
    /**
     * 是否越狱/root
     */
    private Integer jailBroken;
    /**
     * 设备状态：ACTIVE, INACTIVE, BLOCKED
     */
    private Integer status;
    /**
     * 扩展信息
     */
    private String extras;

}
