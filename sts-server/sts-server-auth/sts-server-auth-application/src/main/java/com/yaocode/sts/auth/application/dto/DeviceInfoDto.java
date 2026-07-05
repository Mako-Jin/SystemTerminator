package com.yaocode.sts.auth.application.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeviceInfoDto {

    private String deviceId;
    private String deviceType;
    private String deviceName;
    private String osVersion;
    private Boolean isTrusted;
    private LocalDateTime lastLoginTime;

}
