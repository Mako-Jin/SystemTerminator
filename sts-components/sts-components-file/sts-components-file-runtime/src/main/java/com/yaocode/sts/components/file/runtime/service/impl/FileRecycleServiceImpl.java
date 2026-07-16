package com.yaocode.sts.components.file.runtime.service.impl;

import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.components.file.runtime.model.command.BatchRestoreFromRecycleCommand;
import com.yaocode.sts.components.file.runtime.model.command.EmptyRecycleBinByConditionsCommand;
import com.yaocode.sts.components.file.runtime.model.command.EmptyRecycleBinCommand;
import com.yaocode.sts.components.file.runtime.model.command.PermanentDeleteCommand;
import com.yaocode.sts.components.file.runtime.model.command.RestoreFromRecycleCommand;
import com.yaocode.sts.components.file.runtime.model.query.RecycleBinQuery;
import com.yaocode.sts.components.file.runtime.model.result.BatchRestoreResult;
import com.yaocode.sts.components.file.runtime.model.result.CleanupResult;
import com.yaocode.sts.components.file.runtime.model.result.RecycleFileInfoResult;
import com.yaocode.sts.components.file.runtime.model.result.RecycleStatisticsResult;
import com.yaocode.sts.components.file.runtime.service.FileRecycleService;
import org.springframework.stereotype.Service;

@Service
public class FileRecycleServiceImpl implements FileRecycleService {
    @Override
    public PageResult<RecycleFileInfoResult> getRecycleBin(RecycleBinQuery query) {
        return null;
    }

    @Override
    public RecycleStatisticsResult getRecycleStatistics() {
        return null;
    }

    @Override
    public void restoreFromRecycle(RestoreFromRecycleCommand command) {

    }

    @Override
    public BatchRestoreResult batchRestoreFromRecycle(BatchRestoreFromRecycleCommand command) {
        return null;
    }

    @Override
    public void permanentDelete(PermanentDeleteCommand command) {

    }

    @Override
    public CleanupResult emptyRecycleBin(EmptyRecycleBinCommand command) {
        return null;
    }

    @Override
    public CleanupResult emptyRecycleBinByConditions(EmptyRecycleBinByConditionsCommand command) {
        return null;
    }
}
