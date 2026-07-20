package com.yaocode.sts.file.runtime.model.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 断点续传信息结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class ResumeInfoResult {
    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

    /** 文件大小（字节） */
    private Long fileSize;

    /** 上传ID */
    private String uploadId;

    /** 总分片数 */
    private Integer totalChunks;

    /** 已上传分片数 */
    private Integer uploadedChunks;

    /** 进度百分比 */
    private Integer progress;

    /** 已上传分片列表 */
    private List<UploadedChunkInfoResult> uploadedChunkInfos;

    /** 缺失的分片序号列表 */
    private List<Integer> missingChunks;

    /** 是否可恢复 */
    private Boolean canResume;

    /** 状态 */
    private String status;
}


