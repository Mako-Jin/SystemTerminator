
-- ======================================================
-- 1. 品牌配置表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_brand_config`;
CREATE TABLE `auth_tbl_brand_config` (
    `brand_id` VARCHAR(32) NOT NULL COMMENT '品牌ID',
    `target_type` VARCHAR(20) DEFAULT NULL COMMENT '关联目标类型：TENANT, COMPANY, PLATFORM',
    `target_id` VARCHAR(32) DEFAULT NULL COMMENT '关联目标ID',
    `brand_name` VARCHAR(100) DEFAULT NULL COMMENT '品牌名称',
    `logo_url` VARCHAR(500) DEFAULT NULL COMMENT 'Logo URL',
    `login_title` VARCHAR(200) DEFAULT NULL COMMENT '登录页标题',
    `institution` VARCHAR(200) DEFAULT NULL COMMENT '机构名称',
    `copyright` VARCHAR(200) DEFAULT NULL COMMENT '版权信息',
    `primary_color` VARCHAR(20) DEFAULT NULL COMMENT '主题颜色',
    `login_background_url` VARCHAR(500) DEFAULT NULL COMMENT '登录页背景图',
    `enabled` tinyint DEFAULT 1 COMMENT '是否启用',
    `priority` tinyint DEFAULT 0 COMMENT '优先级（数字越大优先级越高）',
    `version` VARCHAR(20) DEFAULT NULL COMMENT '版本号',
    `is_deleted` tinyint DEFAULT 0 COMMENT '是否删除',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`brand_id`),
    INDEX `idx_target` (`target_type`, `target_id`),
    INDEX `idx_enabled_priority` (`enabled`, `priority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='品牌配置表';

-- ======================================================
-- 2. 客户端信息表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_client_info`;
CREATE TABLE `auth_tbl_client_info` (
    `client_id` VARCHAR(32) NOT NULL COMMENT '客户端ID',
    `client_name` VARCHAR(100) DEFAULT NULL COMMENT '客户端名称',
    `client_type` tinyint DEFAULT NULL COMMENT 'web, mobile, desktop, mini_program',
    `client_version` VARCHAR(20) DEFAULT NULL COMMENT '客户端版本',
    `client_secret` VARCHAR(128) DEFAULT NULL COMMENT '客户端密钥（OAuth2）',
    `grant_types` tinyint DEFAULT NULL COMMENT '授权类型：password, authorization_code, client_credentials, refresh_token',
    `redirect_uris` VARCHAR(500) DEFAULT NULL COMMENT '重定向URI（OAuth2）',
    `scope` VARCHAR(200) DEFAULT NULL COMMENT '授权范围',
    `app_id` VARCHAR(64) DEFAULT NULL COMMENT '应用ID（第三方应用的标识）',
    `enabled` TINYINT(1) DEFAULT 1 COMMENT '是否启用',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`client_id`),
    INDEX `idx_app_id` (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户端信息表';

-- ======================================================
-- 3. 公司信息表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_company_info`;
CREATE TABLE `auth_tbl_company_info` (
    `company_id` VARCHAR(32) NOT NULL COMMENT '公司ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '关联租户',
    `company_name` VARCHAR(100) DEFAULT NULL COMMENT '公司名称',
    `full_name` VARCHAR(200) DEFAULT NULL COMMENT '公司全称',
    `country` VARCHAR(50) DEFAULT NULL COMMENT '国家',
    `region` VARCHAR(50) DEFAULT NULL COMMENT '地区/省份',
    `locality` VARCHAR(50) DEFAULT NULL COMMENT '城市',
    `street` VARCHAR(100) DEFAULT NULL COMMENT '街道',
    `address` VARCHAR(255) DEFAULT NULL COMMENT '详细地址',
    `contact` VARCHAR(50) DEFAULT NULL COMMENT '联系人',
    `postal_code` VARCHAR(20) DEFAULT NULL COMMENT '邮政编码',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `fax` VARCHAR(20) DEFAULT NULL COMMENT '传真',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '公司描述',
    `logo` VARCHAR(500) DEFAULT NULL COMMENT '公司Logo',
    `domain` VARCHAR(100) DEFAULT NULL COMMENT '域名',
    `front_title` VARCHAR(100) DEFAULT NULL COMMENT '前端标题',
    `console_domain` VARCHAR(100) DEFAULT NULL COMMENT '控制台域名',
    `console_title` VARCHAR(100) DEFAULT NULL COMMENT '控制台标题',
    `default_uri` VARCHAR(255) DEFAULT NULL COMMENT '默认URI',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`company_id`),
    INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公司信息表';


-- ======================================================
-- 4. 设备信息表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_device_info`;
CREATE TABLE `auth_tbl_device_info` (
    `device_id` VARCHAR(32) NOT NULL COMMENT '设备ID',
    `device_type` tinyint DEFAULT NULL COMMENT 'IOS, ANDROID, WEB, WECHAT_MINI',
    `user_id` VARCHAR(32) DEFAULT NULL COMMENT '所属用户',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '所属租户',
    `device_name` VARCHAR(100) DEFAULT NULL COMMENT '设备名称',
    `os_name` VARCHAR(50) DEFAULT NULL COMMENT '操作系统',
    `os_version` VARCHAR(20) DEFAULT NULL COMMENT '系统版本',
    `app_version` VARCHAR(20) DEFAULT NULL COMMENT '应用版本',
    `last_ip_address` VARCHAR(45) DEFAULT NULL COMMENT 'IP地址',
    `last_active_time` DATETIME DEFAULT NULL COMMENT '最后活跃时间',
    `user_agent` VARCHAR(500) DEFAULT NULL COMMENT 'User Agent',
    `device_fingerprint` VARCHAR(128) DEFAULT NULL COMMENT '设备指纹',
    `is_trusted` tinyint DEFAULT 0 COMMENT '是否信任设备',
    `jail_broken` tinyint DEFAULT 0 COMMENT '是否越狱/root',
    `status` tinyint DEFAULT 1 COMMENT '设备状态：ACTIVE, INACTIVE, BLOCKED',
    `extras` JSON DEFAULT NULL COMMENT '扩展信息',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`device_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_tenant_id` (`tenant_id`),
    INDEX `idx_device_fingerprint` (`device_fingerprint`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备信息表';


-- ======================================================
-- 5. 实例信息表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_instance_info`;
CREATE TABLE `auth_tbl_instance_info` (
    `instance_id` VARCHAR(32) NOT NULL COMMENT '实例ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '关联租户',
    `instance_name` VARCHAR(100) DEFAULT NULL COMMENT '实例名称',
    `region` VARCHAR(50) DEFAULT NULL COMMENT '部署区域',
    `server_address` VARCHAR(255) DEFAULT NULL COMMENT '服务器地址',
    `server_port` INT DEFAULT NULL COMMENT '服务端口',
    `status` tinyint DEFAULT 0 COMMENT '运行状态',
    `instance_type` tinyint DEFAULT NULL COMMENT '实例类型（prod/test/dev）',
    `environment` tinyint DEFAULT NULL COMMENT '环境标识 -- production, staging, development',
    `version` VARCHAR(20) DEFAULT NULL COMMENT '版本号',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '描述',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`instance_id`),
    INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='实例信息表';



-- ======================================================
-- 6. 登录历史表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_login_history`;
CREATE TABLE `auth_tbl_login_history` (
    `id` VARCHAR(32) NOT NULL COMMENT '主键ID',
    `user_id` VARCHAR(32) DEFAULT NULL COMMENT '用户ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '所属租户',
    `username` VARCHAR(64) DEFAULT NULL COMMENT '用户名',
    `login_source` tinyint DEFAULT NULL COMMENT '登录来源：web、mobile、desktop、小程序、三方、其他',
    `auth_method` tinyint DEFAULT NULL COMMENT '认证方式：PASSWORD-密码、SMS-短信、TOTP-双因素、SOCIAL-社交',
    `login_ip` VARCHAR(45) DEFAULT NULL COMMENT '登录IP',
    `device_id` VARCHAR(32) DEFAULT NULL COMMENT '设备ID',
    `client_id` VARCHAR(32) DEFAULT NULL COMMENT '客户端ID',
    `user_agent` VARCHAR(500) DEFAULT NULL COMMENT 'User Agent',
    `login_time` DATETIME DEFAULT NULL COMMENT '登录时间',
    `logout_time` DATETIME DEFAULT NULL COMMENT '退出时间',
    `logout_reason` tinyint DEFAULT NULL COMMENT '主动退出/超时/被踢',
    `status` tinyint DEFAULT NULL COMMENT 'SUCCESS-成功、FAILED-失败',
    `fail_reason` VARCHAR(255) DEFAULT NULL COMMENT '失败原因',
    `session_id` VARCHAR(64) DEFAULT NULL COMMENT '会话ID（关联到当前会话）',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_tenant_id` (`tenant_id`),
    INDEX `idx_login_time` (`login_time`),
    INDEX `idx_session_id` (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录历史表';


-- ======================================================
-- 7. 组织信息表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_org_info`;
CREATE TABLE `auth_tbl_org_info` (
    `organization_id` VARCHAR(32) NOT NULL COMMENT '组织机构ID',
    `organization_name` VARCHAR(100) DEFAULT NULL COMMENT '组织机构名称',
    `full_name` VARCHAR(200) DEFAULT NULL COMMENT '组织机构全称',
    `organization_code` VARCHAR(64) DEFAULT NULL COMMENT '组织机构编码',
    `organization_desc` VARCHAR(500) DEFAULT NULL COMMENT '组织机构描述',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `parent_id` VARCHAR(32) DEFAULT NULL COMMENT '父ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `organization_type` INT DEFAULT NULL COMMENT '组织类型（entity/virtual）',
    `organization_code_path` VARCHAR(500) DEFAULT NULL COMMENT '编码路径',
    `level` INT DEFAULT 0 COMMENT '层级',
    `has_child` tinyint DEFAULT 0 COMMENT '是否有子节点',
    `contact` VARCHAR(50) DEFAULT NULL COMMENT '联系人',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '电话',
    `fax` VARCHAR(20) DEFAULT NULL COMMENT '传真',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `status` tinyint DEFAULT 1 COMMENT '状态',
    `is_primary` tinyint DEFAULT 0 COMMENT '是否主组织',
    `manager_id` VARCHAR(32) DEFAULT NULL COMMENT '负责人用户ID',
    `manager_name` VARCHAR(64) DEFAULT NULL COMMENT '负责人姓名',
    `company_id` VARCHAR(32) DEFAULT NULL COMMENT '所属公司ID',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`organization_id`),
    INDEX `idx_tenant_id` (`tenant_id`),
    INDEX `idx_parent_id` (`parent_id`),
    INDEX `idx_company_id` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组织信息表';


-- ======================================================
-- 8. 密码历史表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_password_history`;
CREATE TABLE `auth_tbl_password_history` (
    `id` VARCHAR(32) NOT NULL COMMENT '主键ID',
    `user_id` VARCHAR(32) DEFAULT NULL COMMENT '用户ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID（用户在不同租户可能有不同密码）',
    `password_hash` VARCHAR(255) DEFAULT NULL COMMENT '历史密码哈希',
    `change_time` DATETIME DEFAULT NULL COMMENT '修改时间',
    `change_source` tinyint DEFAULT NULL COMMENT '修改来源：SELF-自行修改、ADMIN-管理员重置',
    `is_active` tinyint DEFAULT 0 COMMENT '是否为当前有效密码（同一个用户+租户下，只有一条为 true）',
    `expires_at` DATETIME DEFAULT NULL COMMENT '密码过期时间（由租户策略计算得出）',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_user_tenant` (`user_id`, `tenant_id`),
    INDEX `idx_is_active` (`is_active`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='密码历史表';


-- ======================================================
-- 9. 权限策略表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_permission_strategy`;
CREATE TABLE `auth_tbl_permission_strategy` (
    `strategy_id` VARCHAR(32) NOT NULL COMMENT '策略ID',
    `strategy_name` VARCHAR(100) DEFAULT NULL COMMENT '策略名称',
    `strategy_code` VARCHAR(64) DEFAULT NULL COMMENT '策略编码',
    `strategy_desc` VARCHAR(500) DEFAULT NULL COMMENT '策略描述',
    `strategy_type` tinyint DEFAULT NULL COMMENT '策略类型：ROLE_BASED-角色型、GROUP_BASED-组型、USER_BASED-用户型',
    `role_ids` VARCHAR(500) DEFAULT NULL COMMENT '关联的角色ID列表（逗号分隔）',
    `resource_ids` VARCHAR(500) DEFAULT NULL COMMENT '关联的资源ID列表（逗号分隔）',
    `priority` INT DEFAULT 0 COMMENT '优先级（数字越小优先级越高）',
    `is_default` tinyint DEFAULT 0 COMMENT '是否默认策略',
    `conditions` JSON DEFAULT NULL COMMENT '适用场景（JSON格式，如部门、职位等条件）',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `status` tinyint DEFAULT 1 COMMENT '状态：0-禁用、1-启用',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`strategy_id`),
    INDEX `idx_tenant_id` (`tenant_id`),
    INDEX `idx_strategy_code` (`strategy_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限策略表';


-- ======================================================
-- 10. Refresh Token表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_refresh_token`;
CREATE TABLE `auth_tbl_refresh_token` (
    `token_id` VARCHAR(32) NOT NULL COMMENT 'Token ID',
    `jti` VARCHAR(64) DEFAULT NULL COMMENT 'JWT ID（对应 JWT 中的 jti 声明）',
    `token_hash` VARCHAR(255) DEFAULT NULL COMMENT 'Token 哈希值（不存储明文）',
    `user_id` VARCHAR(32) DEFAULT NULL COMMENT '关联用户ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `client_id` VARCHAR(32) DEFAULT NULL COMMENT '关联客户端ID',
    `device_id` VARCHAR(32) DEFAULT NULL COMMENT '关联设备ID',
    `revoked` tinyint DEFAULT 0 COMMENT '是否已撤销：0-否、1-是',
    `revoked_at` DATETIME DEFAULT NULL COMMENT '撤销时间',
    `revoked_reason` VARCHAR(50) DEFAULT NULL COMMENT '撤销原因：LOGOUT, PASSWORD_CHANGE, REPLACED',
    `replaced_by` VARCHAR(32) DEFAULT NULL COMMENT '被哪个新Token替换（刷新时使用）',
    `created_at` DATETIME DEFAULT NULL COMMENT '创建时间',
    `expires_at` DATETIME DEFAULT NULL COMMENT '过期时间',
    `last_used_at` DATETIME DEFAULT NULL COMMENT '最后使用时间',
    `use_count` INT DEFAULT 0 COMMENT '使用次数（用于检测异常）',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`token_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_tenant_id` (`tenant_id`),
    INDEX `idx_jti` (`jti`),
    INDEX `idx_expires_at` (`expires_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Refresh Token表';


-- ======================================================
-- 11. 组织-用户组关联表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_rel_org_user_group`;
CREATE TABLE `auth_tbl_rel_org_user_group` (
    `rel_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `user_group_id` VARCHAR(32) DEFAULT NULL COMMENT '用户组ID',
    `organization_id` VARCHAR(32) DEFAULT NULL COMMENT '组织ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `relation_type` tinyint DEFAULT NULL COMMENT '关联类型：INHERIT（组织成员自动继承）, MANUAL（手动分配）',
    `auto_sync` TINYINT(1) DEFAULT 0 COMMENT '是否自动同步组织成员到用户组',
    PRIMARY KEY (`rel_id`),
    INDEX `idx_user_group_id` (`user_group_id`),
    INDEX `idx_organization_id` (`organization_id`),
    INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组织-用户组关联表';


-- ======================================================
-- 12. 组织-用户关联表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_rel_org_user`;
CREATE TABLE `auth_tbl_rel_org_user` (
    `rel_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `organization_id` VARCHAR(32) DEFAULT NULL COMMENT '组织ID',
    `user_id` VARCHAR(32) DEFAULT NULL COMMENT '用户ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `is_primary_position` TINYINT(1) DEFAULT 0 COMMENT '是否为该组织的主要职位',
    `position_name` VARCHAR(100) DEFAULT NULL COMMENT '职位名称',
    `relation_type` TINYINT DEFAULT NULL COMMENT '关系类型：MANAGER, MEMBER, TEMPORARY',
    `effective_from` DATETIME DEFAULT NULL COMMENT '生效时间',
    `effective_to` DATETIME DEFAULT NULL COMMENT '失效时间',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`rel_id`),
    INDEX `idx_organization_id` (`organization_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组织-用户关联表';


-- ======================================================
-- 13. 角色-成员关联表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_rel_role_member`;
CREATE TABLE `auth_tbl_rel_role_member` (
    `rel_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `role_id` VARCHAR(32) DEFAULT NULL COMMENT '角色ID',
    `member_id` VARCHAR(32) DEFAULT NULL COMMENT '可指向用户或用户组',
    `member_type` TINYINT DEFAULT NULL COMMENT '成员类型：USER（用户）, GROUP（用户组）',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `effective_from` DATETIME DEFAULT NULL COMMENT '生效时间',
    `effective_to` DATETIME DEFAULT NULL COMMENT '失效时间',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`rel_id`),
    INDEX `idx_role_id` (`role_id`),
    INDEX `idx_member_id` (`member_id`),
    INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色-成员关联表';


-- ======================================================
-- 14. 角色-资源实例关联表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_rel_role_resource_instance`;
CREATE TABLE `auth_tbl_rel_role_resource_instance` (
    `rel_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `role_id` VARCHAR(32) DEFAULT NULL COMMENT '角色ID',
    `resource_id` VARCHAR(32) DEFAULT NULL COMMENT '资源ID',
    `resource_type` TINYINT DEFAULT NULL COMMENT '资源类型：API, MENU, BUTTON, DATA',
    `instance_id` VARCHAR(64) DEFAULT NULL COMMENT '资源实例ID（如：特定文档ID、特定订单ID）',
    `instance_name` VARCHAR(100) DEFAULT NULL COMMENT '实例名称',
    `actions` VARCHAR(200) DEFAULT NULL COMMENT '操作权限（逗号分隔：READ,WRITE,DELETE,APPROVE等）',
    `filters` JSON DEFAULT NULL COMMENT '数据过滤条件（JSON格式，如部门ID、时间范围等）',
    `effective_from` DATETIME DEFAULT NULL COMMENT '生效时间',
    `effective_to` DATETIME DEFAULT NULL COMMENT '失效时间',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`rel_id`),
    INDEX `idx_role_id` (`role_id`),
    INDEX `idx_resource_id` (`resource_id`),
    INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色-资源实例关联表';


-- ======================================================
-- 15. 角色-资源关联表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_rel_role_resource`;
CREATE TABLE `auth_tbl_rel_role_resource` (
    `rel_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `role_id` VARCHAR(32) DEFAULT NULL COMMENT '角色ID',
    `resource_id` VARCHAR(32) DEFAULT NULL COMMENT '资源ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `effective_from` DATETIME DEFAULT NULL COMMENT '生效时间',
    `effective_to` DATETIME DEFAULT NULL COMMENT '失效时间',
    `effect` TINYINT DEFAULT 1 COMMENT '权限效果：1-允许、0-拒绝',
    `priority` INT DEFAULT 0 COMMENT '优先级：数字越大优先级越高',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`rel_id`),
    INDEX `idx_role_id` (`role_id`),
    INDEX `idx_resource_id` (`resource_id`),
    INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色-资源关联表';


-- ======================================================
-- 16. 租户-用户关联表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_rel_tenant_users`;
CREATE TABLE `auth_tbl_rel_tenant_users` (
    `rel_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `user_id` VARCHAR(32) DEFAULT NULL COMMENT '用户ID',
    `user_type` TINYINT DEFAULT NULL COMMENT '用户在该租户中的身份类型 EMPLOYEE-员工、ADMIN-管理员、GUEST-访客等',
    `status` TINYINT DEFAULT 1 COMMENT '在该租户中的状态',
    `join_date` DATETIME DEFAULT NULL COMMENT '加入日期',
    `leave_date` DATETIME DEFAULT NULL COMMENT '离开日期',
    `leave_reason` VARCHAR(255) DEFAULT NULL COMMENT '离开原因',
    `user_source` TINYINT DEFAULT NULL COMMENT '登录来源：REGISTER-注册、ASSIGN-管理员分配、SOCIAL-社交登录、SYNC-同步导入',
    `tenant_nickname` VARCHAR(64) DEFAULT NULL COMMENT '用户在该租户下的昵称（可覆盖全局昵称）',
    `tenant_avatar` VARCHAR(500) DEFAULT NULL COMMENT '用户在该租户下的头像（可覆盖全局头像）',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `password` VARCHAR(255) DEFAULT NULL COMMENT '密码',
    `password_changed_at` DATETIME DEFAULT NULL COMMENT '密码最后修改时间',
    `password_expired_at` DATETIME DEFAULT NULL COMMENT '密码过期时间',
    `decipherable` VARCHAR(512) DEFAULT NULL COMMENT '可逆加密的密码 采用非对称加密，并且公钥加密，私钥不公开',
    `salt` VARCHAR(64) DEFAULT NULL COMMENT '盐',
    `is_enabled` TINYINT DEFAULT 1 COMMENT '是否激活：0：未激活；1：已激活',
    `is_locked` TINYINT DEFAULT 0 COMMENT '是否锁定：0-未锁定、1-已锁定',
    `unlock_at` DATETIME DEFAULT NULL COMMENT '自动解锁时间',
    `locked_at` DATETIME DEFAULT NULL COMMENT '锁定时间',
    `lock_reason` VARCHAR(255) DEFAULT NULL COMMENT '锁定原因',
    `unlock_time` DATETIME DEFAULT NULL COMMENT '解锁时间',
    `is_deleted` TINYINT DEFAULT 1 COMMENT '是否被删：0：删了，1没有删',
    PRIMARY KEY (`rel_id`),
    INDEX `idx_tenant_id` (`tenant_id`),
    INDEX `idx_user_id` (`user_id`),
    UNIQUE INDEX `uk_tenant_user` (`tenant_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='租户-用户关联表';


-- ======================================================
-- 17. 用户组-成员关联表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_rel_user_group_member`;
CREATE TABLE `auth_tbl_rel_user_group_member` (
    `rel_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `user_group_id` VARCHAR(32) DEFAULT NULL COMMENT '用户组ID',
    `member_id` VARCHAR(32) DEFAULT NULL COMMENT '可指向用户或用户组',
    `member_type` TINYINT DEFAULT NULL COMMENT '"USER" 或 "GROUP"',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `effective_from` DATETIME DEFAULT NULL COMMENT '生效时间',
    `effective_to` DATETIME DEFAULT NULL COMMENT '失效时间',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`rel_id`),
    INDEX `idx_user_group_id` (`user_group_id`),
    INDEX `idx_member_id` (`member_id`),
    INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户组-成员关联表';


-- ======================================================
-- 18. Remember Me Token表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_remember_me_token`;
CREATE TABLE `auth_tbl_remember_me_token` (
    `token_id` VARCHAR(32) NOT NULL COMMENT 'Token ID',
    `user_id` VARCHAR(32) DEFAULT NULL COMMENT '关联用户ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `username` VARCHAR(64) DEFAULT NULL COMMENT '关联用户名',
    `client_id` VARCHAR(32) DEFAULT NULL COMMENT '绑定的客户端ID',
    `device_id` VARCHAR(32) DEFAULT NULL COMMENT '绑定的设备ID',
    `token_hash` VARCHAR(255) DEFAULT NULL COMMENT 'Token 哈希值（不存储明文）',
    `created_at` DATETIME DEFAULT NULL COMMENT '创建时间',
    `last_used_at` DATETIME DEFAULT NULL COMMENT '最后使用时间',
    `last_used_ip` VARCHAR(45) DEFAULT NULL COMMENT '最后使用IP',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    `last_used_user_agent` VARCHAR(500) DEFAULT NULL COMMENT '最后使用 UserAgent',
    `expires_at` DATETIME DEFAULT NULL COMMENT '过期时间',
    `series` VARCHAR(64) DEFAULT NULL COMMENT '系列号（用于Remember Me令牌序列检测）',
    `revoked` TINYINT DEFAULT 0 COMMENT '是否已被撤销：0-否、1-是',
    `revoked_reason` VARCHAR(255) DEFAULT NULL COMMENT '撤销原因',
    `revoked_at` DATETIME DEFAULT NULL COMMENT '撤销时间',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`token_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_tenant_id` (`tenant_id`),
    INDEX `idx_series` (`series`),
    INDEX `idx_expires_at` (`expires_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Remember Me Token表';


-- ======================================================
-- 19. 资源联系人表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_resource_contact`;
CREATE TABLE `auth_tbl_resource_contact` (
    `contact_id` VARCHAR(32) NOT NULL COMMENT '主键ID',
    `contact_name` VARCHAR(100) DEFAULT NULL COMMENT '联系人姓名',
    `docs_url` VARCHAR(500) DEFAULT NULL COMMENT '文档地址',
    `contact_email` VARCHAR(100) DEFAULT NULL COMMENT '联系人邮箱',
    `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '联系人手机',
    `is_primary` TINYINT(1) DEFAULT 0 COMMENT '是否为主要联系人',
    `resource_id` VARCHAR(32) DEFAULT NULL COMMENT '关联资源ID',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`contact_id`),
    INDEX `idx_resource_id` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资源联系人表';


-- ======================================================
-- 20. 资源表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_resource`;
CREATE TABLE `auth_tbl_resource` (
    `resource_id` VARCHAR(32) NOT NULL COMMENT '资源ID',
    `resource_name` VARCHAR(100) DEFAULT NULL COMMENT '资源名称',
    `resource_value` VARCHAR(255) DEFAULT NULL COMMENT '资源值',
    `resource_desc` VARCHAR(500) DEFAULT NULL COMMENT '资源描述',
    `resource_type` TINYINT DEFAULT NULL COMMENT '资源类型：0：系统；1：服务；2：模块；3：页面；4：接口, BUTTON（按钮）',
    `resource_category` VARCHAR(20) DEFAULT NULL COMMENT '资源分类：MENU（菜单）, API（接口）, DATA（数据）, FILE（文件）',
    `request_url` VARCHAR(500) DEFAULT NULL COMMENT '接口请求地址',
    `request_method` VARCHAR(10) DEFAULT NULL COMMENT '请求方法，大写：POST,GET,PUT',
    `is_deprecated` TINYINT DEFAULT 0 COMMENT '是否已弃用；0：未；1：已',
    `is_white_list` TINYINT DEFAULT 0 COMMENT '是否白名单；0：不是；1：是',
    `sort` INT DEFAULT 0 COMMENT '排序号',
    `icon` VARCHAR(64) DEFAULT NULL COMMENT '菜单显示图标',
    `version` VARCHAR(20) DEFAULT NULL COMMENT '版本',
    `is_enabled` TINYINT DEFAULT 1 COMMENT '是否启用；0：未；1：已',
    `parent_code` VARCHAR(500) DEFAULT NULL COMMENT '父资源编码列表，逗号分割',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID（null 表示平台级资源）',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`resource_id`),
    INDEX `idx_tenant_id` (`tenant_id`),
    INDEX `idx_resource_category` (`resource_category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资源表';


-- ======================================================
-- 21. 角色约束表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_role_constraint`;
CREATE TABLE `auth_tbl_role_constraint` (
    `constraint_id` VARCHAR(32) NOT NULL COMMENT '约束ID',
    `constraint_name` VARCHAR(100) DEFAULT NULL COMMENT '约束名称',
    `constraint_desc` VARCHAR(500) DEFAULT NULL COMMENT '约束描述',
    `constraint_type` TINYINT DEFAULT NULL COMMENT '约束类型：MUTEX-互斥约束、CARDINALITY-基数约束',
    `role_ids` VARCHAR(500) DEFAULT NULL COMMENT '互斥角色列表（逗号分隔）',
    `max_assign` INT DEFAULT NULL COMMENT '最多可分配数量（基数约束使用）',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用、1-启用',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`constraint_id`),
    INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色约束表';


-- ======================================================
-- 22. 角色信息表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_role_info`;
CREATE TABLE `auth_tbl_role_info` (
    `role_id` VARCHAR(32) NOT NULL COMMENT '角色ID',
    `role_name` VARCHAR(100) DEFAULT NULL COMMENT '角色名称',
    `role_code` VARCHAR(64) DEFAULT NULL COMMENT '角色编码',
    `role_desc` VARCHAR(500) DEFAULT NULL COMMENT '角色描述',
    `parent_id` VARCHAR(32) DEFAULT NULL COMMENT '父ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `is_default` TINYINT DEFAULT 0 COMMENT '是否是租户下的默认权限',
    `inherit_strategy` TINYINT DEFAULT NULL COMMENT '继承策略：完全继承、增量继承、覆盖继承、无继承',
    `category` TINYINT DEFAULT NULL COMMENT '角色分类：dynamic-动态角色；static-静态角色；app-应用角色',
    `filters` JSON DEFAULT NULL COMMENT '过滤条件（动态角色使用）',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用；1-启用',
    `role_level` INT DEFAULT 0 COMMENT '角色层级',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`role_id`),
    INDEX `idx_tenant_id` (`tenant_id`),
    INDEX `idx_parent_id` (`parent_id`),
    INDEX `idx_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色信息表';


-- ======================================================
-- 23. 租户配置表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_tenant_config`;
CREATE TABLE `auth_tbl_tenant_config` (
    `config_id` VARCHAR(32) NOT NULL COMMENT '配置ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `password_expiry_enabled` TINYINT DEFAULT 0 COMMENT '是否启用密码过期策略',
    `password_expiry_days` INT DEFAULT 90 COMMENT '密码过期天数',
    `password_min_length` INT DEFAULT 8 COMMENT '密码最小长度',
    `password_complexity` VARCHAR(10) DEFAULT 'MEDIUM' COMMENT '密码复杂度：LOW, MEDIUM, HIGH',
    `password_history_count` INT DEFAULT 5 COMMENT '密码历史记录数（防止重复使用）',
    `session_timeout` INT DEFAULT 3600 COMMENT '会话超时时间（秒）',
    `single_session_enabled` TINYINT DEFAULT 0 COMMENT '是否允许单设备登录',
    `remember_me_enabled` TINYINT DEFAULT 1 COMMENT '是否允许记住我',
    `remember_me_max_days` INT DEFAULT 30 COMMENT '记住我最大有效期（天）',
    `mfa_required` TINYINT DEFAULT 0 COMMENT '是否强制 MFA',
    `mfa_types` TINYINT DEFAULT NULL COMMENT '支持的 MFA 类型：TOTP, SMS, EMAIL',
    `max_login_attempts` INT DEFAULT 5 COMMENT '最大登录失败次数',
    `lockout_duration` INT DEFAULT 900 COMMENT '登录锁定时间（秒）',
    `captcha_enabled` TINYINT DEFAULT 0 COMMENT '是否启用验证码',
    `captcha_type` TINYINT DEFAULT NULL COMMENT '验证码类型：MATH, TEXT, SLIDER',
    `captcha_trigger` TINYINT DEFAULT NULL COMMENT '验证码触发条件：ALWAYS, FAILED, SENSITIVE',
    `password_login_enabled` TINYINT DEFAULT 1 COMMENT '是否允许密码登录',
    `sms_login_enabled` TINYINT DEFAULT 0 COMMENT '是否允许短信登录',
    `email_login_enabled` TINYINT DEFAULT 0 COMMENT '是否允许邮箱登录',
    `qr_code_login_enabled` TINYINT DEFAULT 0 COMMENT '是否允许扫码登录',
    `self_register_enabled` TINYINT DEFAULT 1 COMMENT '是否允许自助注册',
    `forgot_password_enabled` TINYINT DEFAULT 1 COMMENT '是否允许忘记密码重置',
    `status` TINYINT DEFAULT 1 COMMENT '配置状态：ACTIVE, INACTIVE',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`config_id`),
    UNIQUE INDEX `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='租户配置表';


-- ======================================================
-- 24. 租户信息表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_tenant`;
CREATE TABLE `auth_tbl_tenant` (
    `tenant_id` VARCHAR(32) NOT NULL COMMENT '租户ID',
    `tenant_name` VARCHAR(100) DEFAULT NULL COMMENT '租户名称',
    `tenant_code` VARCHAR(64) DEFAULT NULL COMMENT '租户编码',
    `tenant_desc` VARCHAR(500) DEFAULT NULL COMMENT '租户描述',
    `tenant_status` TINYINT DEFAULT 1 COMMENT '租户状态',
    `tenant_level` INT DEFAULT 0 COMMENT '租户层级',
    `allow_register` TINYINT DEFAULT 1 COMMENT '是否允许注册新用户',
    `allow_add` TINYINT DEFAULT 1 COMMENT '是否允许新增新用户',
    `parent_id` VARCHAR(32) DEFAULT NULL COMMENT '父ID',
    `tenant_code_path` VARCHAR(500) DEFAULT NULL COMMENT '编码路径（层级路径，便于查询）',
    `has_child` TINYINT DEFAULT 0 COMMENT '是否有子租户',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`tenant_id`),
    INDEX `idx_parent_id` (`parent_id`),
    INDEX `idx_tenant_code` (`tenant_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='租户信息表';


-- ======================================================
-- 25. 用户联系方式表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_user_contact`;
CREATE TABLE `auth_tbl_user_contact` (
    `contact_id` VARCHAR(32) NOT NULL COMMENT '联系方式ID',
    `user_id` VARCHAR(32) DEFAULT NULL COMMENT '用户ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `contact_type` TINYINT DEFAULT NULL COMMENT '联系方式类型：MOBILE-手机、EMAIL-邮箱、PHONE-座机',
    `contact_value` VARCHAR(128) DEFAULT NULL COMMENT '联系方式值（手机号/邮箱地址）',
    `is_verified` TINYINT DEFAULT 0 COMMENT '是否验证：0-未验证、1-已验证',
    `is_primary` TINYINT DEFAULT 0 COMMENT '是否主联系方式：0-否、1-是',
    `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注（如：工作手机、个人手机）',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`contact_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_tenant_id` (`tenant_id`),
    INDEX `idx_contact_value` (`contact_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户联系方式表';


-- ======================================================
-- 26. 用户证件表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_user_credential`;
CREATE TABLE `auth_tbl_user_credential` (
    `credential_id` VARCHAR(32) NOT NULL COMMENT '证件ID',
    `user_id` VARCHAR(32) DEFAULT NULL COMMENT '用户ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `credential_type` TINYINT DEFAULT NULL COMMENT '证件类型：IDCARD-身份证、PASSPORT-护照、STUDENT_CARD-学生证、MILITARY_CARD-军人证',
    `encrypted_credential_number` VARCHAR(255) DEFAULT NULL COMMENT '证件号码（AES加密存储）',
    `credential_name` VARCHAR(64) DEFAULT NULL COMMENT '证件姓名（与证件一致）',
    `issue_date` VARCHAR(20) DEFAULT NULL COMMENT '签发日期',
    `expire_date` VARCHAR(20) DEFAULT NULL COMMENT '有效期至',
    `is_primary` TINYINT DEFAULT 0 COMMENT '是否主证件：0-否、1-是',
    `credential_image` VARCHAR(500) DEFAULT NULL COMMENT '证件照片/扫描件路径',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`credential_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户证件表';


-- ======================================================
-- 27. 用户教育经历表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_user_education`;
CREATE TABLE `auth_tbl_user_education` (
    `education_id` VARCHAR(32) NOT NULL COMMENT '教育经历ID',
    `user_id` VARCHAR(32) DEFAULT NULL COMMENT '用户ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `school_name` VARCHAR(200) DEFAULT NULL COMMENT '学校名称',
    `degree` TINYINT DEFAULT NULL COMMENT '学历（学士/硕士/博士）',
    `major` VARCHAR(100) DEFAULT NULL COMMENT '专业',
    `start_date` DATE DEFAULT NULL COMMENT '入学日期',
    `end_date` DATE DEFAULT NULL COMMENT '毕业日期',
    `diploma_no` VARCHAR(64) DEFAULT NULL COMMENT '毕业证书编号',
    `sort_order` INT DEFAULT 0 COMMENT '排序（支持多个学历）',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`education_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户教育经历表';


-- ======================================================
-- 28. 用户紧急联系人表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_user_emergency_contact`;
CREATE TABLE `auth_tbl_user_emergency_contact` (
    `emergency_contact_id` VARCHAR(32) NOT NULL COMMENT '紧急联系人ID',
    `user_id` VARCHAR(32) DEFAULT NULL COMMENT '用户ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `contact_name` VARCHAR(64) DEFAULT NULL COMMENT '紧急联系人姓名',
    `relationship` TINYINT DEFAULT NULL COMMENT '关系（父母/配偶/朋友）',
    `phone_num` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '备用邮箱',
    `sort_order` INT DEFAULT 0 COMMENT '排序（支持多个紧急联系人）',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`emergency_contact_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户紧急联系人表';


-- ======================================================
-- 29. 用户工作经历表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_user_employment`;
CREATE TABLE `auth_tbl_user_employment` (
    `employment_id` VARCHAR(32) NOT NULL COMMENT '工作经历ID',
    `user_id` VARCHAR(32) DEFAULT NULL COMMENT '用户ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `organization` VARCHAR(200) DEFAULT NULL COMMENT '组织/公司',
    `division` VARCHAR(100) DEFAULT NULL COMMENT '事业部',
    `department_id` VARCHAR(32) DEFAULT NULL COMMENT '部门ID',
    `department_name` VARCHAR(100) DEFAULT NULL COMMENT '部门名称',
    `employee_number` VARCHAR(64) DEFAULT NULL COMMENT '员工编号',
    `job_title` VARCHAR(100) DEFAULT NULL COMMENT '职位',
    `job_level` VARCHAR(50) DEFAULT NULL COMMENT '职级',
    `cost_center` VARCHAR(64) DEFAULT NULL COMMENT '成本中心',
    `manager_id` VARCHAR(32) DEFAULT NULL COMMENT '上级ID',
    `manager_name` VARCHAR(64) DEFAULT NULL COMMENT '上级姓名',
    `assistant_id` VARCHAR(32) DEFAULT NULL COMMENT '助理用户ID',
    `assistant_name` VARCHAR(64) DEFAULT NULL COMMENT '助理姓名（冗余）',
    `entry_date` DATE DEFAULT NULL COMMENT '入职日期',
    `quit_date` DATE DEFAULT NULL COMMENT '离职日期',
    `probation_end_date` DATE DEFAULT NULL COMMENT '试用期结束日期',
    `regularization_date` DATE DEFAULT NULL COMMENT '转正日期',
    `employment_status` TINYINT DEFAULT NULL COMMENT '雇佣状态：ACTIVE（在职）, RESIGNED（已离职）, PROBATION（试用期）',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`employment_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_tenant_id` (`tenant_id`),
    INDEX `idx_department_id` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户工作经历表';


-- ======================================================
-- 30. 用户组表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_user_group`;
CREATE TABLE `auth_tbl_user_group` (
    `user_group_id` VARCHAR(32) NOT NULL COMMENT '用户组ID',
    `user_group_code` VARCHAR(64) DEFAULT NULL COMMENT '用户组编码',
    `user_group_name` VARCHAR(100) DEFAULT NULL COMMENT '用户组名',
    `user_group_desc` VARCHAR(500) DEFAULT NULL COMMENT '用户组描述',
    `parent_id` VARCHAR(32) DEFAULT NULL COMMENT '用户组父ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `is_enabled` TINYINT DEFAULT 1 COMMENT '状态是否可用',
    `category` TINYINT DEFAULT NULL COMMENT '分组分类（dynamic/static/app）',
    `filters` JSON DEFAULT NULL COMMENT '动态用户组过滤条件（JSON格式）',
    `org_ids_list` VARCHAR(500) DEFAULT NULL COMMENT '组织ID列表',
    `sort` INT DEFAULT 0 COMMENT '排序号',
    `is_default` TINYINT DEFAULT 0 COMMENT '是否默认分组',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`user_group_id`),
    INDEX `idx_tenant_id` (`tenant_id`),
    INDEX `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户组表';


-- ======================================================
-- 31. 用户信息表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_user_info`;
CREATE TABLE `auth_tbl_user_info` (
    `user_id` VARCHAR(32) NOT NULL COMMENT '用户ID',
    `username` VARCHAR(64) DEFAULT NULL COMMENT '用户名',
    `online` TINYINT DEFAULT 0 COMMENT '在线状态：0-离线、1-在线',
    `is_enabled` TINYINT DEFAULT 1 COMMENT '是否激活：0：未激活；1：已激活',
    `is_locked` TINYINT DEFAULT 0 COMMENT '是否锁定：0-未锁定、1-已锁定',
    `lock_time` DATETIME DEFAULT NULL COMMENT '锁定时间',
    `unlock_time` DATETIME DEFAULT NULL COMMENT '解锁时间',
    `mfa_bound` TINYINT(1) DEFAULT 0 COMMENT '是否已绑定MFA',
    `mfa_type` TINYINT DEFAULT NULL COMMENT 'MFA类型：TOTP, SMS, EMAIL',
    `register_source` TINYINT DEFAULT NULL COMMENT '注册来源：REGISTER, ADMIN, SOCIAL, IMPORT',
    `registered_at` DATETIME DEFAULT NULL COMMENT '注册时间',
    `is_deleted` TINYINT DEFAULT 1 COMMENT '是否被删：0：删了，1没有删',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `uk_username` (`username`),
    INDEX `idx_register_source` (`register_source`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';


-- ======================================================
-- 32. 用户个人资料表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_user_profile`;
CREATE TABLE `auth_tbl_user_profile` (
    `user_id` VARCHAR(32) NOT NULL COMMENT '用户ID（与用户表一致）',
    `display_name` VARCHAR(64) DEFAULT NULL COMMENT '显示名称',
    `real_name` VARCHAR(64) DEFAULT NULL COMMENT '真实姓名',
    `gender` TINYINT DEFAULT NULL COMMENT '性别',
    `birth_date` DATE DEFAULT NULL COMMENT '出生日期',
    `married` TINYINT DEFAULT NULL COMMENT '婚姻状态',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像',
    `locale` VARCHAR(20) DEFAULT NULL COMMENT '语言',
    `time_zone` VARCHAR(50) DEFAULT NULL COMMENT '时区',
    `theme` TINYINT DEFAULT NULL COMMENT '主题',
    `extra_attributes` JSON DEFAULT NULL COMMENT '扩展属性（JSON格式）',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户个人资料表';


-- ======================================================
-- 33. 用户密保问题表
-- ======================================================
DROP TABLE IF EXISTS `auth_tbl_user_secret_question`;
CREATE TABLE `auth_tbl_user_secret_question` (
    `id` VARCHAR(32) NOT NULL COMMENT '主键ID',
    `user_id` VARCHAR(32) DEFAULT NULL COMMENT '用户ID',
    `tenant_id` VARCHAR(32) DEFAULT NULL COMMENT '租户ID',
    `question_type` VARCHAR(64) DEFAULT NULL COMMENT '问题类型（可配置的问题列表）',
    `custom_question` VARCHAR(255) DEFAULT NULL COMMENT '自定义问题（如果问题类型是CUSTOM）',
    `encrypted_answer` VARCHAR(255) DEFAULT NULL COMMENT '答案（AES加密存储）',
    `sort_order` INT DEFAULT 0 COMMENT '排序号（支持多个密保问题）',
    `create_user_id` VARCHAR(32) DEFAULT NULL COMMENT '创建者ID',
    `create_user_name` VARCHAR(64) DEFAULT NULL COMMENT '创建者名称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user_id` VARCHAR(32) DEFAULT NULL COMMENT '更新者ID',
    `update_user_name` VARCHAR(64) DEFAULT NULL COMMENT '更新者名称',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户密保问题表';
