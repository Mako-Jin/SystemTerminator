package com.yaocode.sts.file.runtime.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 文件访问日志表(审计)
 */
@Data
@TableName("file_tbl_access_log")
public class FileAccessLogEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    @TableField("log_id")
    private Long logId;

    /**
     * 文件ID
     */
    @TableField("file_id")
    private String fileId;

    /**
     * 操作用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 操作类型: upload-上传 download-下载 delete-删除 view-查看 update-更新 copy-复制
     */
    @TableField("operation_type")
    private Integer operationType;

    /**
     * 操作状态: 1-成功 0-失败
     */
    @TableField("operation_status")
    private Integer operationStatus;

    /**
     * 客户端IP地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 用户代理
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 请求URL
     */
    @TableField("request_url")
    private String requestUrl;

    /**
     * 请求方法
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 请求参数(JSON)
     */
    @TableField("request_params")
    private String requestParams;

    /**
     * 响应状态码
     */
    @TableField("response_code")
    private Integer responseCode;

    /**
     * 错误信息
     */
    @TableField("error_message")
    private String errorMessage;

    /**
     * 耗时(毫秒)
     */
    @TableField("cost_time")
    private Long costTime;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
}
