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
    private String id;

    private String userId;
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
    private String changeSource;
}
