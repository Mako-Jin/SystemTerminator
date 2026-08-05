package com.yaocode.sts.file.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 文件分支实体
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@TableName("file_tbl_file_branch")
@EqualsAndHashCode(callSuper = true)
public class FileBranchEntity extends BasePo {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分支ID（业务主键）
     */
    private String branchId;

    /**
     * 所属文件ID
     */
    private String fileId;

    /**
     * 分支名称
     */
    private String branchName;

    /**
     * 分支类型: 1-主分支 2-开发分支 3-功能分支 4-修复分支 5-发布分支
     */
    private Integer branchType;

    /**
     * 分支描述
     */
    private String branchDescription;

    /**
     * 分支头版本ID
     */
    private String headVersionId;

    /**
     * 来源分支ID
     */
    private String sourceBranchId;

    /**
     * 是否为默认分支: 0-否 1-是
     */
    private Boolean isDefault;

    /**
     * 是否活跃: 0-否 1-是
     */
    private Boolean isActive;

}
