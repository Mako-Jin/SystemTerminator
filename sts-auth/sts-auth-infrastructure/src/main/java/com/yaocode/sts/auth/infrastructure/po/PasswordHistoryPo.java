package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import com.yaocode.sts.common.infrastructure.po.BasePo;


@Data
@TableName("auth_tbl_password_history")
@EqualsAndHashCode(callSuper = true)
public class PasswordHistoryPo extends BasePo {
    @TableId
    private String historyId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 租户ID（用户在不同租户可能有不同密码）
     */
    private String tenantId;
    /**
     * 历史密码哈希
     */
    private String passwordHash;
    /**
     * 修改时间
     */
    private LocalDateTime changeTime;
    /**
     * 修改来源：SELF-自行修改、ADMIN-管理员重置
     */
    private Integer changeSource;
    /**
     * 是否为当前有效密码（同一个用户+租户下，只有一条为 true）
     */
    private Integer isActive;
    /**
     * 密码过期时间（由租户策略计算得出）
     */
    private LocalDateTime expiresAt;
}
