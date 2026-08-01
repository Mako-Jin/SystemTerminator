package com.yaocode.sts.file.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiffResult {
    private String diffType;
    private Double changePercentage;
    private Integer addedLines;
    private Integer deletedLines;
    private Integer modifiedLines;
    private String diffContent;
}
