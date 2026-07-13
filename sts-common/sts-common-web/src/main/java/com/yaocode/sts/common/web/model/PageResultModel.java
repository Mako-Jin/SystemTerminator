package com.yaocode.sts.common.web.model;

import com.yaocode.sts.common.web.enums.ResultEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageResultModel<T> extends ResultModel<List<T>> {

    private Long totalCount;

    public PageResultModel(ResultEnums resultEnums) {
        super(resultEnums, Collections.emptyList());
        this.totalCount = 0L;
    }

    public PageResultModel(ResultEnums resultEnums, Long totalCount, List<T> data) {
        super(resultEnums, data);
        this.totalCount = totalCount;
    }
}
