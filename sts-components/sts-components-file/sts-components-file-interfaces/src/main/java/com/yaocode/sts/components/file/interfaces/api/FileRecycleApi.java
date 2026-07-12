package com.yaocode.sts.components.file.interfaces.api;

import com.yaocode.sts.common.web.model.PageResultModel;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.components.file.interfaces.model.response.BatchRestoreResponse;
import com.yaocode.sts.components.file.interfaces.model.response.CleanupResponse;
import com.yaocode.sts.components.file.interfaces.model.response.RecycleFileInfoResponse;
import com.yaocode.sts.components.file.interfaces.model.response.RecycleStatisticsResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 文件回收站管理API
 */
@RequestMapping("/files/recycle")
public interface FileRecycleApi {

    /**
     * 获取回收站文件列表
     */
    @GetMapping("/list")
    PageResultModel<RecycleFileInfoResponse> getRecycleBin(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String fileName,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime
    );

    /**
     * 从回收站恢复文件
     */
    @PostMapping("/{fileId}/restore")
    ResultModel<Void> restoreFromRecycle(@PathVariable String fileId);

    /**
     * 批量从回收站恢复文件
     */
    @PostMapping("/batch/restore")
    ResultModel<BatchRestoreResponse> batchRestoreFromRecycle(@RequestBody List<String> fileIds);

    /**
     * 从回收站彻底删除文件（物理删除）
     */
    @DeleteMapping("/{fileId}/permanent")
    ResultModel<Void> permanentDelete(@PathVariable String fileId);

    /**
     * 清空回收站（彻底删除所有文件）
     */
    @DeleteMapping("/empty")
    ResultModel<CleanupResponse> emptyRecycleBin();

    /**
     * 清空回收站（按条件清理）
     */
    @DeleteMapping("/empty/conditions")
    ResultModel<CleanupResponse> emptyRecycleBinByConditions(
            @RequestParam(required = false) Integer days,        // 保留天数
            @RequestParam(required = false) String storageType,  // 存储类型
            @RequestParam(defaultValue = "true") Boolean confirm // 二次确认
    );

    /**
     * 获取回收站统计信息
     */
    @GetMapping("/statistics")
    ResultModel<RecycleStatisticsResponse> getRecycleStatistics();

}
