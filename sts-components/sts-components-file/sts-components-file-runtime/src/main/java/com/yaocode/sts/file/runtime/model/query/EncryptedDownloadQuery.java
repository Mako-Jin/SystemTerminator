package com.yaocode.sts.file.runtime.model.query;

import lombok.Builder;
import lombok.Data;

/**
 * 加密下载查询
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class EncryptedDownloadQuery {
    /** 文件ID */
    private String fileId;

    /** 加密密钥（Base64） */
    private String encryptionKey;

    /** 加密算法 */
    @Builder.Default
    private String algorithm = "AES";

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
