package com.yaocode.sts.file.core.spi;

import com.yaocode.sts.file.core.enums.DuplicateFileStrategyEnums;
import com.yaocode.sts.file.core.model.ExecuteResult;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;

/**
 * 重复文件策略接口
 * 每个策略实现类负责：
 * 1. 判断是否支持当前场景（isSupport）
 * 2. 返回对应的策略枚举（getStrategy）
 * 3. 执行具体策略逻辑（execute）
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface DuplicateStrategy {

    /**
     * 获取策略优先级（数字越小优先级越高）
     */
    int getPriority();

    /**
     * 获取策略名称
     */
    String getName();

    /**
     * 判断当前策略是否支持该场景
     *
     * @param context   上传上下文
     * @param existFile 已存在的文件信息
     * @return true-支持，false-不支持
     */
    boolean isSupport(FileUploadContext context, FileExistenceContext existFile);

    /**
     * 获取策略枚举
     */
    DuplicateFileStrategyEnums getStrategy();

    /**
     * 执行策略
     *
     * @param context    上传上下文
     * @param existFile  已存在的文件信息
     * @param fileBytes  文件字节数组
     * @return 执行结果，返回null表示无需处理（走正常上传流程）
     */
    ExecuteResult execute(
            FileUploadContext context,
            FileExistenceContext existFile,
            byte[] fileBytes
    );

}
