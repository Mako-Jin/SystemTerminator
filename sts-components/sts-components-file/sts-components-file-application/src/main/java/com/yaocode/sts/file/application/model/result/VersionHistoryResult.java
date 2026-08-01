package com.yaocode.sts.file.application.model.result;

import com.yaocode.sts.file.application.model.dto.BranchInfoDto;
import com.yaocode.sts.file.application.model.dto.VersionHistoryItemDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 版本历史结果
 */
@Data
@Builder
public class VersionHistoryResult {
    private String fileId;
    private String fileName;
    private Long total;
    private List<VersionHistoryItemDto> items;
    private List<BranchInfoDto> branches;
}

