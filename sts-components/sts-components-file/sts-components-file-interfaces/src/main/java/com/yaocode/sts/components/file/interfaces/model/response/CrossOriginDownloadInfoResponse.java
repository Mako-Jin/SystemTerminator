package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 跨域下载信息
 */
@Data
@Builder
public class CrossOriginDownloadInfoResponse {

    /**
     * 文件ID
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    /**
     * 直连URL（如OSS地址）
     */
    private String directUrl;
    /**
     * 访问凭证
     */
    private String accessKey;
    /**
     * 签名
     */
    private String signature;
    /**
     * 过期时间（秒）
     */
    private Integer expiry;
    /**
     * 过期时间点
     */
    private LocalDateTime expireTime;
    /**
     * 需要携带的请求头
     */
    private Map<String, String> headers;

}