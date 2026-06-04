package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;


@Data
@TableName("auth_tbl_user_education")
@EqualsAndHashCode(callSuper = true)
public class UserEducationPo extends BasePo {

    @TableId
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 学校名称
     */
    private String schoolName;
    /**
     * 学历（学士/硕士/博士）
     */
    private String degree;
    /**
     * 专业
     */
    private String major;
    /**
     * 入学日期
     */
    private String startDate;
    /**
     * 毕业日期
     */
    private String endDate;
    /**
     * 毕业证书编号
     */
    private String diplomaNo;
    /**
     * 排序（支持多个学历）
     */
    private Integer sortOrder;
}
