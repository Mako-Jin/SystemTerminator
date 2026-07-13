package com.yaocode.sts.common.basic.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 通用分页结果
 *
 * @param <T> 数据类型
 */
@Data
@Builder
public class PageResult<T> {

    private Long total;
    private Integer page;
    private Integer size;
    private List<T> records;

}
