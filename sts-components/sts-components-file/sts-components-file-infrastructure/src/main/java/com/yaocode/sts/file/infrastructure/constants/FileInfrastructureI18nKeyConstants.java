package com.yaocode.sts.file.infrastructure.constants;

/**
 * 文件基础设施层国际化消息 Key 常量
 * <p>
 * 与 messages.properties 中的 key 对应，支持国际化
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface FileInfrastructureI18nKeyConstants {

    // ==================== 插件管理 (900-909) ====================

    /** 插件加载失败 */
    String PLUGIN_LOAD_FAILED = "infrastructure.plugin.load.failed";

    /** 插件未找到 */
    String PLUGIN_NOT_FOUND = "infrastructure.plugin.not.found";

    /** 存储类型不支持 */
    String STORAGE_TYPE_NOT_SUPPORTED = "infrastructure.storage.type.not.supported";

    // ==================== 存储操作 (910-929) ====================

    /** 存储操作失败 */
    String STORAGE_OPERATION_FAILED = "infrastructure.storage.operation.failed";

    /** 存储节点不可用 */
    String STORAGE_NODE_UNAVAILABLE = "infrastructure.storage.node.unavailable";

    /** 存储容量已满 */
    String STORAGE_CAPACITY_EXCEEDED = "infrastructure.storage.capacity.exceeded";

    // ==================== 上传会话 (930-939) ====================

    /** 上传会话过期 */
    String UPLOAD_SESSION_EXPIRED = "infrastructure.upload.session.expired";

    /** 上传会话已取消 */
    String UPLOAD_SESSION_CANCELLED = "infrastructure.upload.session.cancelled";

    /** 上传会话已完成 */
    String UPLOAD_SESSION_COMPLETED = "infrastructure.upload.session.completed";

    // ==================== 数据库操作 (940-949) ====================

    /** 数据访问失败 */
    String DATA_ACCESS_FAILED = "infrastructure.data.access.failed";

    /** 数据已存在 */
    String DATA_ALREADY_EXISTS = "infrastructure.data.already.exists";

    /** 数据不存在 */
    String DATA_NOT_FOUND = "infrastructure.data.not.found";

}
