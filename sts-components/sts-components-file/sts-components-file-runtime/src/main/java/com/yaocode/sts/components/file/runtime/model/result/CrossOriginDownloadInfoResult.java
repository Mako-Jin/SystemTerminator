package com.yaocode.sts.components.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 跨域下载信息结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class CrossOriginDownloadInfoResult {
    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 直连URL */
    private String directUrl;

    /** 访问密钥 */
    private String accessKey;

    /** 签名 */
    private String signature;

    /** 过期时间（秒） */
    private Integer expiry;

    /** 过期时间 */
    private LocalDateTime expireTime;

    /** 请求头 */
    private Map<String, String> headers;
}
