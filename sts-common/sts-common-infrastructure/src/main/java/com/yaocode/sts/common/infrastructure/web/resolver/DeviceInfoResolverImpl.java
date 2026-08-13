package com.yaocode.sts.common.infrastructure.web.resolver;

import com.yaocode.sts.common.domain.constants.HeaderConstants;
import com.yaocode.sts.common.domain.constants.RequestConstants;
import com.yaocode.sts.common.domain.context.DeviceInfoContext;
import com.yaocode.sts.common.domain.context.spi.DeviceInfoResolver;
import com.yaocode.sts.common.domain.valueobject.DeviceId;
import com.yaocode.sts.common.domain.web.HttpRequestContext;

import java.util.Optional;

/**
 * 设备信息解析器
 * <p>
 * 支持双通道解析：优先从 Query/Form Parameter 获取，Parameter 不存在时从 HTTP Header 获取
 * 典型场景：前端 SDK 通过 Header 注入设备指纹/操作系统信息
 */
public class DeviceInfoResolverImpl implements DeviceInfoResolver {

    @Override
    public Optional<DeviceInfoContext> resolve(HttpRequestContext context) {
        DeviceInfoContext deviceInfo = DeviceInfoContext.createDefault();

        // ========== 设备基础信息 ==========
        context.getHeaderOrParameter(HeaderConstants.DEVICE_ID)
                .or(() -> context.getHeaderOrParameter(RequestConstants.DEVICE_ID))
                .ifPresent(v -> deviceInfo.setDeviceId(DeviceId.of(v)));
        context.getHeaderOrParameter(HeaderConstants.DEVICE_TYPE)
                .or(() -> context.getHeaderOrParameter(RequestConstants.DEVICE_TYPE))
                .ifPresent(deviceInfo::setDeviceType);
        context.getHeaderOrParameter(HeaderConstants.DEVICE_NAME)
                .or(() -> context.getHeaderOrParameter(RequestConstants.DEVICE_NAME))
                .ifPresent(deviceInfo::setDeviceName);
        context.getHeaderOrParameter(HeaderConstants.DEVICE_MODEL)
                .or(() -> context.getHeaderOrParameter(RequestConstants.DEVICE_MODEL))
                .ifPresent(deviceInfo::setDeviceModel);

        // ========== 操作系统信息 ==========
        context.getHeaderOrParameter(HeaderConstants.OS_NAME)
                .or(() -> context.getHeaderOrParameter(RequestConstants.OS_NAME))
                .ifPresent(deviceInfo::setOsName);
        context.getHeaderOrParameter(HeaderConstants.OS_VERSION)
                .or(() -> context.getHeaderOrParameter(RequestConstants.OS_VERSION))
                .ifPresent(deviceInfo::setOsVersion);
        context.getHeaderOrParameter(HeaderConstants.OS_BUILD)
                .or(() -> context.getHeaderOrParameter(RequestConstants.OS_BUILD))
                .ifPresent(deviceInfo::setOsBuild);

        // ========== 屏幕信息 ==========
        context.getHeaderOrParameter(HeaderConstants.SCREEN_RESOLUTION)
                .or(() -> context.getHeaderOrParameter(RequestConstants.SCREEN_RESOLUTION))
                .ifPresent(deviceInfo::setScreenResolution);
        context.getHeaderOrParameter(HeaderConstants.SCREEN_SIZE)
                .or(() -> context.getHeaderOrParameter(RequestConstants.SCREEN_SIZE))
                .ifPresent(deviceInfo::setScreenSize);
        parseInt(context, HeaderConstants.SCREEN_DENSITY, RequestConstants.SCREEN_DENSITY)
                .ifPresent(deviceInfo::setScreenDensity);

        // ========== 设备指纹 ==========
        context.getHeaderOrParameter(HeaderConstants.DEVICE_FINGERPRINT)
                .or(() -> context.getHeaderOrParameter(RequestConstants.DEVICE_FINGERPRINT))
                .ifPresent(deviceInfo::setDeviceFingerprint);
        context.getHeaderOrParameter(HeaderConstants.IMEI)
                .or(() -> context.getHeaderOrParameter(RequestConstants.IMEI))
                .ifPresent(deviceInfo::setImei);
        context.getHeaderOrParameter(HeaderConstants.IDFA)
                .or(() -> context.getHeaderOrParameter(RequestConstants.IDFA))
                .ifPresent(deviceInfo::setIdfa);
        context.getHeaderOrParameter(HeaderConstants.MAC_ADDRESS)
                .or(() -> context.getHeaderOrParameter(RequestConstants.MAC_ADDRESS))
                .ifPresent(deviceInfo::setMacAddress);

        // ========== 安全信息 ==========
        parseBoolean(context, HeaderConstants.IS_JAIL_BROKEN, RequestConstants.IS_JAIL_BROKEN)
                .ifPresent(deviceInfo::setIsJailbroken);
        parseBoolean(context, HeaderConstants.IS_EMULATOR, RequestConstants.IS_EMULATOR)
                .ifPresent(deviceInfo::setIsEmulator);

        // ========== 位置/语言信息 ==========
        context.getHeaderOrParameter(HeaderConstants.COUNTRY_CODE)
                .or(() -> context.getHeaderOrParameter(RequestConstants.COUNTRY_CODE))
                .ifPresent(deviceInfo::setCountryCode);
        context.getHeaderOrParameter(HeaderConstants.LANG_HEADER_NAME)
                .or(() -> context.getHeaderOrParameter(RequestConstants.LANG_PARAM_NAME))
                .ifPresent(deviceInfo::setLanguage);
        context.getHeaderOrParameter(HeaderConstants.TIMEZONE)
                .or(() -> context.getHeaderOrParameter(RequestConstants.TIMEZONE))
                .ifPresent(deviceInfo::setTimezone);

        return Optional.of(deviceInfo);
    }

    /**
     * 解析 Integer 参数（Header 优先）
     */
    private Optional<Integer> parseInt(HttpRequestContext context, String headerName, String paramName) {
        return context.getHeaderOrParameter(headerName)
                .or(() -> context.getHeaderOrParameter(paramName))
                .flatMap(v -> {
                    try {
                        return Optional.of(Integer.parseInt(v));
                    } catch (NumberFormatException e) {
                        return Optional.empty();
                    }
                });
    }

    /**
     * 解析 Boolean 参数（Header 优先）
     */
    private Optional<Boolean> parseBoolean(HttpRequestContext context, String headerName, String paramName) {
        return context.getHeaderOrParameter(headerName)
                .or(() -> context.getHeaderOrParameter(paramName))
                .flatMap(v -> {
                    if (v.isEmpty()) {
                        return Optional.empty();
                    }
                    return Optional.of(Boolean.parseBoolean(v));
                });
    }
}