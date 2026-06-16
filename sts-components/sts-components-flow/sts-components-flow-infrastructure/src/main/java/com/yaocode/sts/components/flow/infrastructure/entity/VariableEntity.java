package com.yaocode.sts.components.flow.infrastructure.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 运行时变量实体
 * 对应表: flow_tbl_variable
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_variable")
public class VariableEntity extends BaseEntity {

    /**
     * 变量ID（主键）
     */
    @TableId
    private String variableId;

    /**
     * 变量名称
     */
    private String variableName;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 执行路径ID
     */
    private String executionId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 作用域类型（1=流程实例，2=执行路径，3=任务，4=并发分支）
     */
    private Integer variableScope;

    /**
     * 流程定义ID
     */
    private String processId;

    /**
     * 流程定义KEY
     */
    private String processKey;

    /**
     * 变量类型（1=字符串，2=整数，3=长整数，4=浮点数，5=布尔值，6=日期，7=JSON，8=二进制，9=序列化对象）
     */
    private Integer variableType;

    /**
     * 文本类型值
     */
    private String textValue;

    /**
     * 整数类型值
     */
    private Long longValue;

    /**
     * 浮点数类型值
     */
    private Double doubleValue;

    /**
     * 二进制数据对象存储URL
     */
    private String binaryStorageFileId;

    /**
     * 二进制数据类型
     */
    private String binaryType;

    /**
     * 是否局部变量（1=是，0=否）
     */
    private Integer isLocal;

    /**
     * 是否瞬态变量（1=是，0=否）
     */
    private Integer isTransient;

    /**
     * 乐观锁版本号
     */
    @Version
    private Integer rev;
}
