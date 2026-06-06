package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;

@Data
@TableName("auth_tbl_client_info")
@EqualsAndHashCode(callSuper = true)
public class ClientInfoPo extends BasePo {

    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * web, mobile, desktop, mini_program
     */
    private String clientType;
    /**
     * 客户端版本
     */
    private String clientVersion;
    /**
     * 应用ID（第三方）
     */
    private String appId;

}
