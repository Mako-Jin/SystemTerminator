package com.yaocode.sts.common.web.properties;

import com.yaocode.sts.common.web.constants.WebConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RestApiElapsedProperties
 * @author: Jin-LiangBo
 * @date: 2025年10月30日 20:53
 */
@Setter
@Getter
@ConfigurationProperties(prefix = RestApiElapsedProperties.REST_API_ELAPSED_PREFIX)
public class RestApiElapsedProperties {

    public static final String REST_API_ELAPSED_PREFIX = "yaocode.web.api.elapsed";

    /**
     * 是否启用接口耗时监控
     */
    private boolean enabled = true;

    /**
     * 耗时阈值（毫秒）
     */
    private long threshold = WebConstants.DEFAULT_API_ELAPSED_THRESHOLD;

    /**
     * 是否记录到数据库
     */
    private boolean enableDbStorage = false;

    /**
     * 数据库记录阈值（毫秒），只有耗时超过此值的请求才会记录到数据库
     */
    private long dbStorageThreshold = 0L;

    /**
     * 是否记录所有请求（包括成功和失败）
     */
    private boolean recordAllRequests = false;

}
