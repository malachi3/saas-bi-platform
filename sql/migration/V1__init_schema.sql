-- =============================================================================
-- SaaS 多租户平台数据库初始化脚本
-- 版本: V1
-- 描述: 初始化数据库表结构
-- 数据库: MySQL 8.0
-- =============================================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- -----------------------------------------------------------------------------
-- 1. 租户相关表
-- -----------------------------------------------------------------------------

-- 租户表
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_code`     VARCHAR(50) NOT NULL COMMENT '租户编码',
    `tenant_name`     VARCHAR(200) NOT NULL COMMENT '租户名称',
    `tenant_type`     VARCHAR(20) DEFAULT 'SHARED' COMMENT '租户类型: SHARED-共享, SCHEMA-独立Schema, DATABASE-独立数据库',
    `schema_name`     VARCHAR(100) COMMENT '数据库Schema名称',
    `db_host`         VARCHAR(100) COMMENT '独立数据库主机',
    `db_port`         INT DEFAULT 3306 COMMENT '数据库端口',
    `db_name`         VARCHAR(100) COMMENT '数据库名称',
    `db_username`     VARCHAR(100) COMMENT '数据库用户名',
    `db_password`     VARCHAR(255) COMMENT '数据库密码',
    `package_id`      BIGINT COMMENT '套餐ID',
    `user_count`      INT DEFAULT 0 COMMENT '用户数量',
    `status`          VARCHAR(20) DEFAULT 'NORMAL' COMMENT '状态: NORMAL-正常, FROZEN-冻结, EXPIRED-过期',
    `expire_time`     DATETIME COMMENT '过期时间',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`      VARCHAR(64) COMMENT '创建人',
    `updated_at`      DATETIME COMMENT '更新时间',
    `updated_by`      VARCHAR(64) COMMENT '更新人',
    `deleted_at`      DATETIME COMMENT '删除时间',
    `version`         INT DEFAULT 0 COMMENT '版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_code` (`tenant_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='租户表';

-- 租户套餐表
DROP TABLE IF EXISTS `sys_tenant_package`;
CREATE TABLE `sys_tenant_package` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `package_name`    VARCHAR(100) NOT NULL COMMENT '套餐名称',
    `package_code`    VARCHAR(50) NOT NULL COMMENT '套餐编码',
    `max_users`       INT DEFAULT 5 COMMENT '最大用户数',
    `max_storage_gb`  DECIMAL(10,2) DEFAULT 1 COMMENT '最大存储空间(GB)',
    `max_data_sources` INT DEFAULT 2 COMMENT '最大数据源数量(-1表示无限制)',
    `features`        JSON COMMENT '功能特性JSON',
    `price`           DECIMAL(10,2) DEFAULT 0 COMMENT '价格',
    `sort_order`      INT DEFAULT 0 COMMENT '排序',
    `status`          VARCHAR(20) DEFAULT 'NORMAL' COMMENT '状态',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_package_code` (`package_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='租户套餐表';

-- -----------------------------------------------------------------------------
-- 2. 用户认证相关表
-- -----------------------------------------------------------------------------

-- 用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`       BIGINT NOT NULL COMMENT '租户ID',
    `username`        VARCHAR(50) NOT NULL COMMENT '用户名',
    `password`        VARCHAR(255) NOT NULL COMMENT '密码(bcrypt加密)',
    `nickname`        VARCHAR(100) COMMENT '昵称',
    `avatar`          VARCHAR(500) COMMENT '头像URL',
    `email`           VARCHAR(200) COMMENT '邮箱',
    `phone`           VARCHAR(20) COMMENT '手机号',
    `dept_id`         BIGINT COMMENT '部门ID',
    `status`          VARCHAR(20) DEFAULT 'NORMAL' COMMENT '状态: NORMAL-正常, DISABLED-禁用, LOCKED-锁定',
    `login_count`     INT DEFAULT 0 COMMENT '登录次数',
    `last_login_time` DATETIME COMMENT '最后登录时间',
    `last_login_ip`   VARCHAR(128) COMMENT '最后登录IP',
    `pwd_expire_time` DATETIME COMMENT '密码过期时间',
    `pwd_error_count` INT DEFAULT 0 COMMENT '密码错误次数',
    `pwd_lock_time`   DATETIME COMMENT '密码锁定时间',
    `must_pwd`        TINYINT(1) DEFAULT 0 COMMENT '是否必须修改密码',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`      VARCHAR(64) COMMENT '创建人',
    `updated_at`      DATETIME COMMENT '更新时间',
    `updated_by`      VARCHAR(64) COMMENT '更新人',
    `deleted_at`      DATETIME COMMENT '删除时间',
    `version`         INT DEFAULT 0 COMMENT '版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_username` (`tenant_id`, `username`),
    UNIQUE KEY `uk_tenant_phone` (`tenant_id`, `phone`),
    UNIQUE KEY `uk_tenant_email` (`tenant_id`, `email`),
    KEY `idx_user_tenant` (`tenant_id`),
    KEY `idx_user_dept` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 部门表
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`       BIGINT NOT NULL COMMENT '租户ID',
    `parent_id`       BIGINT DEFAULT 0 COMMENT '父部门ID',
    `dept_name`       VARCHAR(100) NOT NULL COMMENT '部门名称',
    `dept_code`       VARCHAR(50) COMMENT '部门编码',
    `leader`          VARCHAR(50) COMMENT '负责人',
    `phone`           VARCHAR(20) COMMENT '联系电话',
    `email`           VARCHAR(200) COMMENT '邮箱',
    `sort_order`      INT DEFAULT 0 COMMENT '排序',
    `status`          VARCHAR(20) DEFAULT 'NORMAL' COMMENT '状态',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`      VARCHAR(64) COMMENT '创建人',
    `updated_at`      DATETIME COMMENT '更新时间',
    `updated_by`      VARCHAR(64) COMMENT '更新人',
    `deleted_at`      DATETIME COMMENT '删除时间',
    `version`         INT DEFAULT 0 COMMENT '版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_dept_code` (`tenant_id`, `dept_code`),
    KEY `idx_dept_tenant` (`tenant_id`),
    KEY `idx_dept_parent` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';

-- 岗位表
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`       BIGINT NOT NULL COMMENT '租户ID',
    `post_code`       VARCHAR(50) NOT NULL COMMENT '岗位编码',
    `post_name`       VARCHAR(100) NOT NULL COMMENT '岗位名称',
    `sort_order`      INT DEFAULT 0 COMMENT '排序',
    `status`          VARCHAR(20) DEFAULT 'NORMAL' COMMENT '状态',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`      VARCHAR(64) COMMENT '创建人',
    `updated_at`      DATETIME COMMENT '更新时间',
    `updated_by`      VARCHAR(64) COMMENT '更新人',
    `deleted_at`      DATETIME COMMENT '删除时间',
    `version`         INT DEFAULT 0 COMMENT '版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_post_code` (`tenant_id`, `post_code`),
    KEY `idx_post_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='岗位表';

-- -----------------------------------------------------------------------------
-- 3. 权限相关表
-- -----------------------------------------------------------------------------

-- 角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`       BIGINT NOT NULL COMMENT '租户ID',
    `role_name`       VARCHAR(100) NOT NULL COMMENT '角色名称',
    `role_code`       VARCHAR(50) NOT NULL COMMENT '角色编码',
    `role_type`       VARCHAR(20) DEFAULT 'CUSTOM' COMMENT '角色类型: SYSTEM-系统角色, CUSTOM-自定义角色',
    `data_scope`      INT DEFAULT 1 COMMENT '数据权限范围: 1-全部数据, 2-本部门及以下, 3-本部门, 4-仅本人, 5-自定义',
    `data_scope_depts` TEXT COMMENT '自定义数据权限部门ID列表',
    `sort_order`      INT DEFAULT 0 COMMENT '排序',
    `status`          VARCHAR(20) DEFAULT 'NORMAL' COMMENT '状态',
    `remark`          VARCHAR(500) COMMENT '备注',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`      VARCHAR(64) COMMENT '创建人',
    `updated_at`      DATETIME COMMENT '更新时间',
    `updated_by`      VARCHAR(64) COMMENT '更新人',
    `deleted_at`      DATETIME COMMENT '删除时间',
    `version`         INT DEFAULT 0 COMMENT '版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_role_code` (`tenant_id`, `role_code`),
    KEY `idx_role_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 用户角色关联表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`         BIGINT NOT NULL COMMENT '用户ID',
    `role_id`         BIGINT NOT NULL COMMENT '角色ID',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    KEY `idx_user_role_user` (`user_id`),
    KEY `idx_user_role_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- 菜单表
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `parent_id`       BIGINT DEFAULT 0 COMMENT '父菜单ID',
    `menu_type`       VARCHAR(20) NOT NULL COMMENT '菜单类型: CATALOG-目录, MENU-菜单, BUTTON-按钮',
    `menu_name`       VARCHAR(100) NOT NULL COMMENT '菜单名称',
    `icon`            VARCHAR(100) COMMENT '菜单图标',
    `path`            VARCHAR(200) COMMENT '路由地址',
    `component`       VARCHAR(200) COMMENT '组件路径',
    `redirect`        VARCHAR(200) COMMENT '重定向地址',
    `is_external`     TINYINT(1) DEFAULT 0 COMMENT '是否外链',
    `is_cache`        TINYINT(1) DEFAULT 0 COMMENT '是否缓存',
    `is_visible`      TINYINT(1) DEFAULT 1 COMMENT '是否显示',
    `permission`      VARCHAR(100) COMMENT '权限标识',
    `order_num`       INT DEFAULT 0 COMMENT '排序',
    `status`          VARCHAR(20) DEFAULT 'NORMAL' COMMENT '状态',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`      VARCHAR(64) COMMENT '创建人',
    `updated_at`      DATETIME COMMENT '更新时间',
    `updated_by`      VARCHAR(64) COMMENT '更新人',
    `deleted_at`      DATETIME COMMENT '删除时间',
    `version`         INT DEFAULT 0 COMMENT '版本号',
    PRIMARY KEY (`id`),
    KEY `idx_menu_parent` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单表';

-- 角色菜单关联表
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id`         BIGINT NOT NULL COMMENT '角色ID',
    `menu_id`         BIGINT NOT NULL COMMENT '菜单ID',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_menu` (`role_id`, `menu_id`),
    KEY `idx_role_menu_role` (`role_id`),
    KEY `idx_role_menu_menu` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关联表';

-- 角色部门关联表
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id`         BIGINT NOT NULL COMMENT '角色ID',
    `dept_id`         BIGINT NOT NULL COMMENT '部门ID',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_dept` (`role_id`, `dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色部门关联表';

-- -----------------------------------------------------------------------------
-- 4. 字典与配置表
-- -----------------------------------------------------------------------------

-- 字典类型表
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`       BIGINT NOT NULL COMMENT '租户ID',
    `dict_name`       VARCHAR(100) NOT NULL COMMENT '字典名称',
    `dict_code`       VARCHAR(100) NOT NULL COMMENT '字典编码',
    `status`          VARCHAR(20) DEFAULT 'NORMAL' COMMENT '状态',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`      VARCHAR(64) COMMENT '创建人',
    `updated_at`      DATETIME COMMENT '更新时间',
    `updated_by`      VARCHAR(64) COMMENT '更新人',
    `deleted_at`      DATETIME COMMENT '删除时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_dict_code` (`tenant_id`, `dict_code`),
    KEY `idx_dict_type_tenant` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典类型表';

-- 字典数据表
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`       BIGINT NOT NULL COMMENT '租户ID',
    `dict_type_id`    BIGINT NOT NULL COMMENT '字典类型ID',
    `dict_label`      VARCHAR(200) NOT NULL COMMENT '字典标签',
    `dict_value`      VARCHAR(200) NOT NULL COMMENT '字典值',
    `sort_order`      INT DEFAULT 0 COMMENT '排序',
    `css_class`       VARCHAR(100) COMMENT 'CSS样式',
    `list_class`      VARCHAR(100) COMMENT '列表样式',
    `is_default`      TINYINT(1) DEFAULT 0 COMMENT '是否默认值',
    `status`          VARCHAR(20) DEFAULT 'NORMAL' COMMENT '状态',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `created_by`      VARCHAR(64) COMMENT '创建人',
    `updated_at`      DATETIME COMMENT '更新时间',
    `updated_by`      VARCHAR(64) COMMENT '更新人',
    `deleted_at`      DATETIME COMMENT '删除时间',
    PRIMARY KEY (`id`),
    KEY `idx_dict_data_tenant` (`tenant_id`),
    KEY `idx_dict_data_type` (`dict_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典数据表';

-- -----------------------------------------------------------------------------
-- 5. 日志表
-- -----------------------------------------------------------------------------

-- 登录日志表
DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`       BIGINT COMMENT '租户ID',
    `user_id`         BIGINT COMMENT '用户ID',
    `username`        VARCHAR(100) COMMENT '用户名',
    `login_type`      VARCHAR(20) COMMENT '登录类型: LOGIN-登录, LOGOUT-登出',
    `ip`              VARCHAR(128) COMMENT 'IP地址',
    `user_agent`      VARCHAR(500) COMMENT '用户代理',
    `status`          VARCHAR(20) COMMENT '状态: SUCCESS-成功, FAIL-失败',
    `fail_reason`     VARCHAR(200) COMMENT '失败原因',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_login_tenant` (`tenant_id`),
    KEY `idx_login_user` (`user_id`),
    KEY `idx_login_time` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录日志表';

-- 操作日志表
DROP TABLE IF EXISTS `log_operate`;
CREATE TABLE `log_operate` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`       BIGINT COMMENT '租户ID',
    `user_id`         BIGINT COMMENT '用户ID',
    `username`        VARCHAR(100) COMMENT '用户名',
    `module`          VARCHAR(100) COMMENT '操作模块',
    `business_type`   VARCHAR(20) COMMENT '业务类型: CREATE-新增, UPDATE-修改, DELETE-删除, GRANT-授权',
    `method`          VARCHAR(200) COMMENT '方法名',
    `request_method`  VARCHAR(10) COMMENT '请求方法',
    `request_url`     VARCHAR(500) COMMENT '请求URL',
    `request_param`   TEXT COMMENT '请求参数',
    `response_param`  TEXT COMMENT '响应参数',
    `error_msg`       TEXT COMMENT '错误信息',
    `cost_time`       BIGINT COMMENT '消耗时间(毫秒)',
    `ip`              VARCHAR(128) COMMENT 'IP地址',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_operate_tenant` (`tenant_id`),
    KEY `idx_operate_user` (`user_id`),
    KEY `idx_operate_time` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';

-- 权限变更日志表
DROP TABLE IF EXISTS `log_permission`;
CREATE TABLE `log_permission` (
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`       BIGINT COMMENT '租户ID',
    `user_id`         BIGINT COMMENT '操作用户ID',
    `target_type`     VARCHAR(20) COMMENT '目标类型: USER-用户, ROLE-角色',
    `target_id`       BIGINT COMMENT '目标ID',
    `action`          VARCHAR(20) COMMENT '动作: GRANT-授权, REVOKE-撤销',
    `perm_type`       VARCHAR(20) COMMENT '权限类型: MENU-菜单, DEPT-部门, DATA-数据权限',
    `perm_key`        VARCHAR(200) COMMENT '权限标识',
    `old_value`       TEXT COMMENT '旧值',
    `new_value`       TEXT COMMENT '新值',
    `created_at`      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_permission_tenant` (`tenant_id`),
    KEY `idx_permission_time` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限变更日志表';

SET FOREIGN_KEY_CHECKS = 1;
