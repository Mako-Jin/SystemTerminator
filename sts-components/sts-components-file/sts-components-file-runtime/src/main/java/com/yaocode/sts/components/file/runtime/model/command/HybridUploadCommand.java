package com.yaocode.sts.components.file.runtime.model.command;

import com.yaocode.sts.components.file.runtime.model.dto.FileObjectDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 混合云上传命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class HybridUploadCommand {
    /** 上传的文件 */
    private FileObjectDto file;

    /** 优先存储列表 */
    private List<String> preferredStorages;

    /** 指定存储类型 */
    private String storageType;

    /** 业务ID */
    private String businessId;

    /** 业务类型 */
    private String businessType;

    /** 选择策略（auto/priority/cost/performance） */
    private String strategy;

    /** 文件标签 */
    private String tags;

    /** 文件描述 */
    private String description;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;
}
