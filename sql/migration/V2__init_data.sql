-- =============================================================================
-- SaaS 多租户平台数据库初始化数据脚本
-- 版本: V2
-- 描述: 初始化基础数据（超级管理员、套餐、菜单）
-- 数据库: PostgreSQL 16
-- 依赖: V1__init_schema.sql
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 1. 初始化超级管理员租户
-- -----------------------------------------------------------------------------
INSERT INTO sys_tenant (id, tenant_code, tenant_name, tenant_type, status) 
VALUES (1, 'super', '超级租户', 'SHARED', 'NORMAL');

-- -----------------------------------------------------------------------------
-- 2. 初始化套餐数据
-- -----------------------------------------------------------------------------
INSERT INTO sys_tenant_package (package_name, package_code, max_users, max_storage_gb, max_data_sources, features, price, sort_order) VALUES
('免费版', 'free', 5, 1, 2, '{"storage": 1, "users": 5, "dataSources": 2, "dashboard": 3, "export": false}', 0, 1),
('专业版', 'pro', 50, 20, 10, '{"storage": 20, "users": 50, "dataSources": 10, "dashboard": 50, "export": true}', 99, 2),
('企业版', 'enterprise', 200, 100, -1, '{"storage": 100, "users": 200, "dataSources": -1, "dashboard": -1, "export": true}', 399, 3);

-- -----------------------------------------------------------------------------
-- 3. 初始化超级管理员用户
-- 密码: admin123 (bcrypt加密)
-- 加密因子: $2a$10$7EqDpqlsHzq3zWqjDIxmPe1R6v5dZ3yYv7Y5cVvfZ3xZq3Zq3Zq3Z
-- -----------------------------------------------------------------------------
INSERT INTO sys_user (id, tenant_id, username, password, nickname, email, status) 
VALUES (1, 1, 'admin', '$2a$10$7EqDpqlsHzq3zWqjDIxmPe1R6v5dZ3yYv7Y5cVvfZ3xZq3Zq3Zq3', '超级管理员', 'admin@example.com', 'NORMAL');

-- -----------------------------------------------------------------------------
-- 4. 初始化超级管理员角色
-- -----------------------------------------------------------------------------
INSERT INTO sys_role (id, tenant_id, role_name, role_code, role_type, data_scope, sort_order, status, remark)
VALUES (1, 1, '超级管理员', 'super_admin', 'SYSTEM', 1, 1, 'NORMAL', '系统内置超级管理员角色，拥有所有权限');

-- 关联超级管理员用户和角色
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- -----------------------------------------------------------------------------
-- 5. 初始化菜单数据
-- -----------------------------------------------------------------------------
-- 顶级目录
INSERT INTO sys_menu (id, parent_id, menu_type, menu_name, icon, path, order_num, status) VALUES
(1, 0, 'CATALOG', '工作台', 'ant-design:dashboard-outlined', '/dashboard', 1, 'NORMAL'),
(2, 0, 'CATALOG', '分析', '/analysis', 'ant-design:bar-chart-outlined', '/analysis', 2, 'NORMAL'),
(3, 0, 'CATALOG', '数据源', '/datasource', 'ant-design:database-outlined', '/datasource', 3, 'NORMAL'),
(4, 0, 'CATALOG', '数据集', '/dataset', 'ant-design:table-outlined', '/dataset', 4, 'NORMAL'),
(5, 0, 'CATALOG', '报表', '/report', 'ant-design:bar-chart-outlined', '/report', 5, 'NORMAL'),
(100, 0, 'CATALOG', '系统管理', 'ant-design:setting-outlined', '/system', 99, 'NORMAL');

-- 工作台子菜单
INSERT INTO sys_menu (id, parent_id, menu_type, menu_name, path, component, order_num, status) VALUES
(101, 1, 'MENU', '工作台首页', '/dashboard/console', 'dashboard/console/index', 1, 'NORMAL'),
(102, 1, 'MENU', '工作台设置', '/dashboard/settings', 'dashboard/settings/index', 2, 'NORMAL');

-- 系统管理子菜单
INSERT INTO sys_menu (id, parent_id, menu_type, menu_name, path, component, order_num, status) VALUES
(1001, 100, 'MENU', '用户管理', '/system/user', 'system/user/index', 1, 'NORMAL'),
(1002, 100, 'MENU', '角色管理', '/system/role', 'system/role/index', 2, 'NORMAL'),
(1003, 100, 'MENU', '菜单管理', '/system/menu', 'system/menu/index', 3, 'NORMAL'),
(1004, 100, 'MENU', '部门管理', '/system/dept', 'system/dept/index', 4, 'NORMAL'),
(1005, 100, 'MENU', '岗位管理', '/system/post', 'system/post/index', 5, 'NORMAL'),
(1006, 100, 'MENU', '字典管理', '/system/dict', 'system/dict/index', 6, 'NORMAL'),
(1007, 100, 'MENU', '租户管理', '/system/tenant', 'system/tenant/index', 7, 'NORMAL');

-- 系统管理子菜单的按钮权限
INSERT INTO sys_menu (id, parent_id, menu_type, menu_name, permission, order_num, status) VALUES
(10011, 1001, 'BUTTON', '用户新增', 'system:user:add', 1, 'NORMAL'),
(10012, 1001, 'BUTTON', '用户编辑', 'system:user:edit', 2, 'NORMAL'),
(10013, 1001, 'BUTTON', '用户删除', 'system:user:delete', 3, 'NORMAL'),
(10014, 1001, 'BUTTON', '重置密码', 'system:user:resetPwd', 4, 'NORMAL'),
(10015, 1001, 'BUTTON', '导出用户', 'system:user:export', 5, 'NORMAL'),

(10021, 1002, 'BUTTON', '角色新增', 'system:role:add', 1, 'NORMAL'),
(10022, 1002, 'BUTTON', '角色编辑', 'system:role:edit', 2, 'NORMAL'),
(10023, 1002, 'BUTTON', '角色删除', 'system:role:delete', 3, 'NORMAL'),
(10024, 1002, 'BUTTON', '角色授权', 'system:role:grant', 4, 'NORMAL'),

(10031, 1003, 'BUTTON', '菜单新增', 'system:menu:add', 1, 'NORMAL'),
(10032, 1003, 'BUTTON', '菜单编辑', 'system:menu:edit', 2, 'NORMAL'),
(10033, 1003, 'BUTTON', '菜单删除', 'system:menu:delete', 3, 'NORMAL'),

(10041, 1004, 'BUTTON', '部门新增', 'system:dept:add', 1, 'NORMAL'),
(10042, 1004, 'BUTTON', '部门编辑', 'system:dept:edit', 2, 'NORMAL'),
(10043, 1004, 'BUTTON', '部门删除', 'system:dept:delete', 3, 'NORMAL'),

(10051, 1005, 'BUTTON', '岗位新增', 'system:post:add', 1, 'NORMAL'),
(10052, 1005, 'BUTTON', '岗位编辑', 'system:post:edit', 2, 'NORMAL'),
(10053, 1005, 'BUTTON', '岗位删除', 'system:post:delete', 3, 'NORMAL'),

(10061, 1006, 'BUTTON', '字典新增', 'system:dict:add', 1, 'NORMAL'),
(10062, 1006, 'BUTTON', '字典编辑', 'system:dict:edit', 2, 'NORMAL'),
(10063, 1006, 'BUTTON', '字典删除', 'system:dict:delete', 3, 'NORMAL');

-- 为超级管理员角色授权所有菜单
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, id FROM sys_menu WHERE status = 'NORMAL';

-- -----------------------------------------------------------------------------
-- 6. 初始化默认部门
-- -----------------------------------------------------------------------------
INSERT INTO sys_dept (id, tenant_id, parent_id, dept_name, dept_code, leader, sort_order, status) VALUES
(1, 1, 0, '总公司', 'HQ', '超级管理员', 1, 'NORMAL');

-- -----------------------------------------------------------------------------
-- 7. 初始化字典类型
-- -----------------------------------------------------------------------------
INSERT INTO sys_dict_type (tenant_id, dict_name, dict_code, status) VALUES
(1, '用户状态', 'sys_user_status', 'NORMAL'),
(1, '租户状态', 'sys_tenant_status', 'NORMAL'),
(1, '租户类型', 'sys_tenant_type', 'NORMAL'),
(1, '菜单状态', 'sys_menu_status', 'NORMAL'),
(1, '菜单类型', 'sys_menu_type', 'NORMAL'),
(1, '角色状态', 'sys_role_status', 'NORMAL'),
(1, '部门状态', 'sys_dept_status', 'NORMAL'),
(1, '登录状态', 'sys_login_status', 'NORMAL'),
(1, '是否', 'sys_yes_no', 'NORMAL'),
(1, '业务类型', 'sys_business_type', 'NORMAL');

-- -----------------------------------------------------------------------------
-- 8. 初始化字典数据
-- -----------------------------------------------------------------------------
-- 用户状态
INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '正常', 'NORMAL', 1, 'primary', true, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_user_status';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '禁用', 'DISABLED', 2, 'danger', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_user_status';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '锁定', 'LOCKED', 3, 'warning', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_user_status';

-- 租户状态
INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '正常', 'NORMAL', 1, 'primary', true, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_tenant_status';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '冻结', 'FROZEN', 2, 'warning', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_tenant_status';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '过期', 'EXPIRED', 3, 'danger', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_tenant_status';

-- 租户类型
INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '共享模式', 'SHARED', 1, 'primary', true, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_tenant_type';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '独立Schema', 'SCHEMA', 2, 'success', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_tenant_type';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '独立数据库', 'DATABASE', 3, 'warning', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_tenant_type';

-- 菜单类型
INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '目录', 'CATALOG', 1, 'warning', true, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_menu_type';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '菜单', 'MENU', 2, 'primary', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_menu_type';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '按钮', 'BUTTON', 3, 'success', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_menu_type';

-- 菜单状态
INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '正常', 'NORMAL', 1, 'primary', true, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_menu_status';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '停用', 'DISABLED', 2, 'danger', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_menu_status';

-- 角色状态
INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '正常', 'NORMAL', 1, 'primary', true, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_role_status';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '停用', 'DISABLED', 2, 'danger', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_role_status';

-- 部门状态
INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '正常', 'NORMAL', 1, 'primary', true, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_dept_status';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '停用', 'DISABLED', 2, 'danger', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_dept_status';

-- 登录状态
INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '成功', 'SUCCESS', 1, 'success', true, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_login_status';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '失败', 'FAIL', 2, 'danger', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_login_status';

-- 是否
INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '是', 'Y', 1, 'primary', true, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_yes_no';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '否', 'N', 2, 'default', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_yes_no';

-- 业务类型
INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '新增', 'CREATE', 1, 'success', true, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_business_type';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '修改', 'UPDATE', 2, 'primary', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_business_type';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '删除', 'DELETE', 3, 'danger', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_business_type';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '授权', 'GRANT', 4, 'warning', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_business_type';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '导出', 'EXPORT', 5, 'info', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_business_type';

INSERT INTO sys_dict_data (tenant_id, dict_type_id, dict_label, dict_value, sort_order, list_class, is_default, status)
SELECT 1, id, '导入', 'IMPORT', 6, 'info', false, 'NORMAL' FROM sys_dict_type WHERE dict_code = 'sys_business_type';
