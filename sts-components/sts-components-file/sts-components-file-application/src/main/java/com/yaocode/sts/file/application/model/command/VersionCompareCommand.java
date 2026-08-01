package com.yaocode.sts.file.application.model.command;

import lombok.Builder;
import lombok.Data;

/**
 * 版本对比命令
 */
@Data
@Builder
public class VersionCompareCommand {
    private String versionId1;
    private String versionId2;
}
