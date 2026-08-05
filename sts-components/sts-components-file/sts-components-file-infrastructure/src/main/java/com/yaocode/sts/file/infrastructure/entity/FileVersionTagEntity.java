package com.yaocode.sts.file.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件版本标签实体
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
@TableName("file_tbl_version_tag")
@EqualsAndHashCode(callSuper = true)
public class FileVersionTagEntity extends BasePo {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签ID（业务主键）
     */
    private String tagId;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 版本ID
     */
    private String versionId;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签类型: 1-发布版 2-里程碑 3-测试版 4-自定义
     */
    private Integer tagType;

    /**
     * 标签描述
     */
    private String tagDescription;
}
