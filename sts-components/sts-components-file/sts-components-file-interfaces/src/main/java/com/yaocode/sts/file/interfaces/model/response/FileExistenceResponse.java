package com.yaocode.sts.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 文件存在性检查响应
 * <p>
 * 用于秒传场景，检查文件是否已存在于存储系统中
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class FileExistenceResponse {

    /**
     * 是否存在
     */
    private Boolean exists;
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
     * 文件MD5值
     */
    private String fileMd5;
    /**
     * 文件URL
     */
    private String fileUrl;
    /**
     * 是否存在重复
     */
    private Boolean isDuplicate;
    /**
     * 存储类型
     */
    private Integer storageType;
    /**
     * 重复文件列表
     */
    private List<FileInfoResponse> duplicateFiles;

}