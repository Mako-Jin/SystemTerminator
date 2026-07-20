package com.yaocode.sts.file.runtime.service;

import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.file.runtime.model.command.BatchRestoreFromRecycleCommand;
import com.yaocode.sts.file.runtime.model.command.EmptyRecycleBinByConditionsCommand;
import com.yaocode.sts.file.runtime.model.command.EmptyRecycleBinCommand;
import com.yaocode.sts.file.runtime.model.command.PermanentDeleteCommand;
import com.yaocode.sts.file.runtime.model.command.RestoreFromRecycleCommand;
import com.yaocode.sts.file.runtime.model.query.RecycleBinQuery;
import com.yaocode.sts.file.runtime.model.result.BatchRestoreResult;
import com.yaocode.sts.file.runtime.model.result.CleanupResult;
import com.yaocode.sts.file.runtime.model.result.RecycleFileInfoResult;
import com.yaocode.sts.file.runtime.model.result.RecycleStatisticsResult;


/**
 * 文件回收站服务接口
 * <p>
 * 使用 CQRS 模式，命令和查询分离
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface FileRecycleService {

    // ==================== 1. 回收站查询（查询） ====================

    /**
     * 获取回收站文件列表
     */
    PageResult<RecycleFileInfoResult> getRecycleBin(RecycleBinQuery query);

    /**
     * 获取回收站统计信息
     */
    RecycleStatisticsResult getRecycleStatistics();

    // ==================== 2. 文件恢复（命令） ====================

    /**
     * 从回收站恢复文件
     */
    void restoreFromRecycle(RestoreFromRecycleCommand command);

    /**
     * 批量从回收站恢复文件
     */
    BatchRestoreResult batchRestoreFromRecycle(BatchRestoreFromRecycleCommand command);

    // ==================== 3. 永久删除（命令） ====================

    /**
     * 从回收站彻底删除文件
     */
    void permanentDelete(PermanentDeleteCommand command);

    /**
     * 清空回收站
     */
    CleanupResult emptyRecycleBin(EmptyRecycleBinCommand command);

    /**
     * 按条件清空回收站
     */
    CleanupResult emptyRecycleBinByConditions(EmptyRecycleBinByConditionsCommand command);
}
