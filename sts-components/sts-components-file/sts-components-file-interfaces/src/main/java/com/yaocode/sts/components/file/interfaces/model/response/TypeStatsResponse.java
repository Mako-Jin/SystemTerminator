package com.yaocode.sts.components.file.interfaces.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TypeStatsResponse {

    private String fileType;
    private String fileTypeDesc;
    private Long fileCount;
    private Long totalSize;
    private Double percentage;

}
