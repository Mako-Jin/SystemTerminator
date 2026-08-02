package com.yaocode.sts.scheduler.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 分布式锁实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scheduler_distributed_lock")
public class DistributedLockEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 锁键 */
    private String lockKey;

    /** 锁持有者 */
    private String lockOwner;

    /** 锁值(JSON) */
    private String lockValue;

    /** 过期时间 */
    private LocalDateTime expireTime;

    // ========== 审计字段 ==========
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
