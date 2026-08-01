package com.yaocode.sts.file.application.service;

import com.yaocode.sts.file.core.enums.DuplicateFileStrategyEnums;
import com.yaocode.sts.file.core.model.ExecuteResult;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;

import java.nio.file.Path;

/**
 * 策略执行器接口
 * 职责：执行选中的策略
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface DuplicateStrategyService {

    /**
     * 执行策略
     *
     * @param context    上传上下文
     * @param existFile  已存在的文件信息
     * @param tempFile  文件字节数组
     * @return 执行结果，返回null表示无需处理（走正常上传流程）
     */
    ExecuteResult execute(
            FileUploadContext context,
            FileExistenceContext existFile,
            Path tempFile,
            String fileMd5,
            String fileSha256
    );

    /**
     * 执行策略（带回调）
     */
    ExecuteResult execute(
            FileUploadContext context,
            FileExistenceContext existFile,
            Path tempFile,
            String fileMd5,
            String fileSha256,
            StrategyCallback callback
    );

    /**
     * 策略执行回调接口
     */
    interface StrategyCallback {
        /** 策略选中时回调 */
        void onStrategySelected(DuplicateFileStrategyEnums strategy);

        /** 策略执行完成时回调 */
        void onStrategyExecuted(DuplicateFileStrategyEnums strategy, ExecuteResult result);

        /** 策略执行出错时回调 */
        void onError(DuplicateFileStrategyEnums strategy, Exception e);
    }
}
