-- ----------------------------
-- Table structure for common_database_conf_d
-- ----------------------------
DROP TABLE IF EXISTS `common_database_conf_d`;
CREATE TABLE `common_database_conf_d` (
                                          `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                                          `name` varchar(255) DEFAULT NULL COMMENT '名称',
                                          `type` varchar(255) NOT NULL COMMENT '数据库类型： PG/MYSQL/ORACLE等',
                                          `url` varchar(255) NOT NULL COMMENT '数据库连接',
                                          `username` varchar(255) NOT NULL COMMENT '数据库账号',
                                          `password` varchar(255) NOT NULL COMMENT '数据库密码',
                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of common_database_conf_d
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for common_sql_api_conf_d
-- ----------------------------
DROP TABLE IF EXISTS `common_sql_api_conf_d`;
CREATE TABLE `common_sql_api_conf_d` (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `name` varchar(255) DEFAULT NULL COMMENT '名称',
                                         `code` varchar(255) NOT NULL COMMENT '编码',
                                         `operate_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'QUERY' COMMENT '操作类型 QUERY/UPDATE/DELETE/INSERT',
                                         `value` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'mybatis sql脚本',
                                         `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                                         `database_id` int DEFAULT NULL COMMENT '数据库id， 为空则默认当前数据库',
                                         `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
                                         `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
                                         PRIMARY KEY (`id`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of common_sql_api_conf_d
-- ----------------------------
BEGIN;
COMMIT;