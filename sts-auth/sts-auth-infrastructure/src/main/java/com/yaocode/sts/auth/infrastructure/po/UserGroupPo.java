package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户组持久化对象
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:27
 */
@Data
@TableName("auth_tbl_user_group")
@EqualsAndHashCode(callSuper = true)
public class UserGroupPo extends BasePo {

    /**
     * 用户组id
     */
    @TableId
    private String userGroupId;
    /**
     * 用户组编码
     */
    private String userGroupCode;
    /**
     * 用户组名
     */
    private String userGroupName;
    /**
     * 用户组描述
     */
    private String userGroupDesc;
    /**
     * 用户组父id
     */
    private String parentId;

}
