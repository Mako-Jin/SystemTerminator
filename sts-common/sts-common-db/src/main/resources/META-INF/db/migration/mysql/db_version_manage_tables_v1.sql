CREATE TABLE IF NOT EXISTS `script_history` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `file_name` varchar(64) DEFAULT NULL COMMENT '文件名',
  `script_content` text COMMENT '脚本内容',
  `script_type` tinyint DEFAULT NULL COMMENT '脚本类型：1-DDL, 2-DML, 3-DQL',
  `security_level` tinyint DEFAULT NULL COMMENT '安全等级：1-低, 2-中, 3-高',
  `status` tinyint DEFAULT NULL COMMENT '执行状态：0-执行中, 1-成功, 2-失败',
  `error_message` text COMMENT '错误信息',
  `execute_time` bigint DEFAULT NULL COMMENT '执行耗时(ms)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='脚本执行历史记录表';
