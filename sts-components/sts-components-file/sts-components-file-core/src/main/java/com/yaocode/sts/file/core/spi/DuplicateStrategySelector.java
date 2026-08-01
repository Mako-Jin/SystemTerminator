package com.yaocode.sts.file.core.spi;

import com.yaocode.sts.file.core.enums.DuplicateFileStrategyEnums;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;

import java.util.List;

/**
 * 策略选择器接口
 * 职责：从所有策略中选择最合适的一个
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface DuplicateStrategySelector {

    /**
     * 选择策略
     *
     * @param context   上传上下文
     * @param existFile 已存在的文件信息
     * @return 策略枚举，返回null表示无需处理（正常上传）
     */
    DuplicateFileStrategyEnums selectStrategy(
            FileUploadContext context,
            FileExistenceContext existFile
    );

    /**
     * 获取所有已注册的策略
     */
    List<DuplicateStrategy> getAllStrategies();

    /**
     * 注册策略
     */
    void registerStrategy(DuplicateStrategy strategy);

    /**
     * 移除策略
     */
    void unregisterStrategy(DuplicateStrategy strategy);
}
