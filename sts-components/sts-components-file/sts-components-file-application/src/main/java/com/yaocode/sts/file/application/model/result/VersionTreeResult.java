package com.yaocode.sts.file.application.model.result;

import com.yaocode.sts.file.application.model.dto.VersionTreeBranchInfoDto;
import com.yaocode.sts.file.application.model.dto.VersionTreeEdgeDto;
import com.yaocode.sts.file.application.model.dto.VersionTreeNodeDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 版本树结果
 */
@Data
@Builder
public class VersionTreeResult {
    private String fileId;
    private String fileName;
    private List<VersionTreeNodeDto> nodes;
    private List<VersionTreeEdgeDto> edges;
    private List<VersionTreeBranchInfoDto> branches;
}

