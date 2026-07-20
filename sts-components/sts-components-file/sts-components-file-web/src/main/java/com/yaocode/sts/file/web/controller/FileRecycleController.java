package com.yaocode.sts.file.web.controller;

import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.common.web.model.PageResultModel;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.common.web.utils.PageResultUtils;
import com.yaocode.sts.common.web.utils.ResultUtils;
import com.yaocode.sts.file.interfaces.api.FileRecycleApi;
import com.yaocode.sts.file.interfaces.model.response.BatchRestoreResponse;
import com.yaocode.sts.file.interfaces.model.response.CleanupResponse;
import com.yaocode.sts.file.interfaces.model.response.RecycleFileInfoResponse;
import com.yaocode.sts.file.interfaces.model.response.RecycleStatisticsResponse;
import com.yaocode.sts.file.runtime.model.result.RecycleFileInfoResult;
import com.yaocode.sts.file.runtime.service.FileRecycleService;
import com.yaocode.sts.file.web.converter.FileRecycleConverter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文件回收站控制器
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@RestController
public class FileRecycleController implements FileRecycleApi {

    @Resource
    private FileRecycleService fileRecycleService;

    @Resource
    private FileRecycleConverter converter;

    // ==================== 1. 回收站查询 ====================

    @Override
    public PageResultModel<RecycleFileInfoResponse> getRecycleBin(
            Integer page, Integer size, String fileName, String startTime, String endTime) {
        log.info("获取回收站列表: page={}, size={}, fileName={}", page, size, fileName);

        PageResult<RecycleFileInfoResult> result = fileRecycleService.getRecycleBin(
                converter.toRecycleBinQuery(page, size, fileName, startTime, endTime)
        );

        return PageResultUtils.ok(result.getTotal(), converter.toRecycleFileInfoResponseList(result.getRecords()));
    }

    @Override
    public ResultModel<RecycleStatisticsResponse> getRecycleStatistics() {
        log.info("获取回收站统计信息");

        RecycleStatisticsResponse response = converter.toRecycleStatisticsResponse(
                fileRecycleService.getRecycleStatistics()
        );
        return ResultUtils.ok(response);
    }

    // ==================== 2. 文件恢复 ====================

    @Override
    public ResultModel<String> restoreFromRecycle(String fileId) {
        log.info("从回收站恢复文件: {}", fileId);

        fileRecycleService.restoreFromRecycle(
                converter.toRestoreFromRecycleCommand(fileId)
        );
        return ResultUtils.ok();
    }

    @Override
    public ResultModel<BatchRestoreResponse> batchRestoreFromRecycle(List<String> fileIds) {
        log.info("批量从回收站恢复文件: {} 个", fileIds.size());

        BatchRestoreResponse response = converter.toBatchRestoreResponse(
                fileRecycleService.batchRestoreFromRecycle(
                        converter.toBatchRestoreFromRecycleCommand(fileIds)
                )
        );
        return ResultUtils.ok(response);
    }

    // ==================== 3. 永久删除 ====================

    @Override
    public ResultModel<String> permanentDelete(String fileId) {
        log.warn("从回收站永久删除文件: {}", fileId);

        fileRecycleService.permanentDelete(
                converter.toPermanentDeleteCommand(fileId)
        );
        return ResultUtils.ok();
    }

    @Override
    public ResultModel<CleanupResponse> emptyRecycleBin() {
        log.warn("清空回收站");

        CleanupResponse response = converter.toCleanupResponse(
                fileRecycleService.emptyRecycleBin(
                        converter.toEmptyRecycleBinCommand()
                )
        );
        return ResultUtils.ok(response);
    }

    @Override
    public ResultModel<CleanupResponse> emptyRecycleBinByConditions(
            Integer days, String storageType, Boolean confirm) {
        log.warn("按条件清空回收站: days={}, storageType={}, confirm={}", days, storageType, confirm);

        CleanupResponse response = converter.toCleanupResponse(
                fileRecycleService.emptyRecycleBinByConditions(
                        converter.toEmptyRecycleBinByConditionsCommand(days, storageType, confirm)
                )
        );
        return ResultUtils.ok(response);
    }
}
