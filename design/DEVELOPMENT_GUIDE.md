# AI 辅助开发实战规范手册

> 本规范基于阿里巴巴Java开发手册、JavaScript编码规范及业界最佳实践整理，适用于前后端协同开发场景。

---

## 一、项目架构规范

### 1.1 目录结构约定

```
项目根目录/
├── src/                          # 源代码
│   ├── api/                      # API 封装层（统一管理所有接口调用）
│   ├── assets/                   # 静态资源（图片、字体等）
│   ├── components/              # 公共组件（业务无关的通用组件）
│   │   ├── Button/
│   │   ├── Table/
│   │   └── Modal/
│   ├── config/                   # 配置文件（环境变量、业务配置）
│   ├── constants/               # 常量定义（枚举、魔法值替代）
│   ├── hooks/                    # 自定义 Hooks
│   ├── layouts/                 # 布局组件
│   ├── pages/                   # 页面组件（路由级组件）
│   │   └── UserManagement/
│   │       ├── index.tsx
│   │       ├── components/       # 页面私有组件
│   │       ├── hooks/            # 页面私有 hooks
│   │       └── types/            # 页面私有类型
│   ├── services/                # 业务逻辑层（与 API 层分离）
│   ├── store/                   # 状态管理
│   ├── styles/                  # 全局样式
│   ├── types/                   # 全局类型定义
│   └── utils/                   # 工具函数
├── tests/                       # 测试文件
│   ├── unit/
│   └── e2e/
├── docs/                        # 文档
├── scripts/                     # 构建/部署脚本
└── configs/                     # 配置文件（ESLint、Prettier等）
```

### 1.2 模块分层原则

```
┌─────────────────────────────────────────┐
│              Pages / Views              │  # 页面层：路由匹配、布局组装
├─────────────────────────────────────────┤
│              Components                 │  # 组件层：展示逻辑、可复用UI
├─────────────────────────────────────────┤
│         Services / Hooks                │  # 业务层：复杂业务逻辑
├─────────────────────────────────────────┤
│              API Layer                  │  # 接口层：HTTP请求、数据转换
├─────────────────────────────────────────┤
│           Infrastructure                │  # 基础设施：工具库、状态管理
└─────────────────────────────────────────┘
```

---

## 二、命名规范

### 2.1 通用命名规则

| 类型 | 规范 | 示例 | 错误示例 |
|------|------|------|----------|
| 变量 | camelCase | `userName`, `isActive` | `user_name`, `UserName` |
| 常量 | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT` | `maxRetryCount`, `MAX_RETRY_COUNT` |
| 函数 | 动词/动词+名词 | `getUserById`, `handleClick` | `user`, `click` |
| 类名 | PascalCase | `UserService`, `OrderController` | `userService`, `order_controller` |
| 组件文件 | PascalCase | `UserCard.tsx` | `user-card.tsx`, `UserCard.js` |
| 配置文件 | kebab-case | `eslint-config.js` | `eslintConfig.js` |
| 数据库表 | 统一小写+下划线 | `sys_user`, `order_detail` | `SysUser`, `orderDetail` |
| 接口URL | RESTful风格 | `/api/v1/users/{id}` | `/api/v1/getUserById` |

### 2.2 命名前缀后缀约定

```
【变量前缀】
is/has/can/should + 动词    →  布尔值：`isLoading`, `hasPermission`, `canEdit`
arr/list + 名词复数          →  数组：`arrUsers`, `listItems`
map/dict + 名词              →  对象/Map：`mapUserById`, `dictStatus`

【后缀约定】
- Config      →  配置对象：`apiConfig`, `themeConfig`
- Handler     →  事件处理：`clickHandler`, `errorHandler`
- Factory     →  工厂类：`userFactory`, `orderFactory`
- Repository  →  数据仓库：`userRepository`
- DTO         →  数据传输对象：`UserDTO`, `OrderDTO`
- VO          →  视图对象：`UserVO`, `OrderVO`
- Entity      →  数据库实体：`UserEntity`, `OrderEntity`
```

---

## 三、代码风格规范

### 3.1 缩进与格式

```javascript
// ✅ 正确：使用 2 空格缩进，统一风格
function getUserById(id) {
  const user = database.find(user => user.id === id);
  if (!user) {
    return null;
  }
  return {
    id: user.id,
    name: user.name,
    email: user.email
  };
}

// ❌ 错误：混用缩进、缺少大括号
function getUserById(id){
    let user=database.find(u=>u.id===id)
    if(!user) return null;
    return {id:user.id,name:user.name}
}
```

### 3.2 引号与分号

```javascript
// ✅ 使用单引号，尾部不加分号（可选，根据团队选择）
const userName = '张三';
const template = `<div class="user-card">${userName}</div>`;

// ❌ 使用双引号
const userName = "张三";
```

### 3.3 空格规范

```javascript
// ✅ 操作符前后加空格
const sum = a + b;
const isValid = age > 18 && status === 'active';

// ✅ 箭头函数参数加括号（单参数时省略括号可选）
const double = (num) => num * 2;
const users = data.map((item) => item.name);

// ✅ 大括号前加空格
function handler() {
  // ...
}

// ✅ 注释前后加空格
// 这是一个单行注释

/**
 * 这是一个多行注释
 * 用于描述复杂逻辑
 */

// ❌ 错误示例
const sum=a+b;
const isValid=age>18&&status==='active';
const double=num=>num*2;
```

### 3.4 代码块规范

```javascript
// ✅ 使用大括号包裹，即使单行也包裹（安全、易扩展）
if (isLoading) {
  return <Loading />;
}

// ❌ 省略大括号
if (isLoading)
  return <Loading />;
```

### 3.5 导出规范

```javascript
// ✅ 命名导出 + 默认导出分离
// types/user.ts
export interface User {
  id: string;
  name: string;
}

export type UserStatus = 'active' | 'inactive';

// ✅ 组件同时提供命名导出和默认导出
// components/UserCard.tsx
export function UserCard() { }
export default UserCard;

// ❌ 批量导出（barrel file 除外）
export * from './user';
```

---

## 四、类型与接口规范

### 4.1 TypeScript 类型定义

```typescript
// ✅ 基础类型：使用具体类型，不使用 any
type UserId = string;
type UserStatus = 'active' | 'inactive' | 'banned';
type Nullable<T> = T | null;

// ✅ 接口定义（推荐，用于对象结构）
interface User {
  id: UserId;
  name: string;
  email: string;
  status: UserStatus;
  createdAt: Date;
  updatedAt: Date;
}

// ✅ 复杂类型使用 type 别名
type UserList = User[];
type UserMap = Record<string, User>;
type UserFilter = Partial<Pick<User, 'status' | 'name'>>;

// ❌ 错误示例
const getUser = (id: any): any => { };
const user: any = { };
```

### 4.2 API 响应类型

```typescript
// 统一 API 响应格式
interface ApiResponse<T> {
  code: number;      // 0 = 成功，非0 = 失败
  data: T;
  message: string;
  success: boolean;
}

// 分页响应
interface PaginatedResponse<T> {
  items: T[];
  total: number;
  page: number;
  pageSize: number;
  totalPages: number;
}

// 请求错误
interface ApiError {
  code: number;
  message: string;
  details?: Record<string, string>;  // 字段级错误信息
}
```

### 4.3 枚举使用

```typescript
// ✅ 使用 const enum 避免运行时开销
const enum UserStatus {
  ACTIVE = 'active',
  INACTIVE = 'inactive',
  BANNED = 'banned'
}

// ✅ 数字枚举用于向后兼容的场景
const enum ErrorCode {
  SUCCESS = 0,
  NOT_FOUND = 404,
  UNAUTHORIZED = 401,
  FORBIDDEN = 403,
  SERVER_ERROR = 500
}
```

---

## 五、前后端接口规范

### 5.1 RESTful API 设计

```
【资源命名】
- 使用名词复数：`/users`, `/orders`, `/products`
- 不使用动词：`/getUser` ❌ → `/users` ✅

【HTTP 方法语义】
GET    /api/v1/users          # 获取用户列表（查询）
GET    /api/v1/users/{id}      # 获取单个用户（详情）
POST   /api/v1/users           # 创建用户（新增）
PUT    /api/v1/users/{id}      # 完整更新用户
PATCH  /api/v1/users/{id}      # 部分更新用户
DELETE /api/v1/users/{id}      # 删除用户

【复杂查询参数】
GET /api/v1/orders?status=paid&page=1&pageSize=20&sort=createdAt,desc

【动作型接口（必要时）】
POST /api/v1/users/{id}/activate    # 激活用户
POST /api/v1/users/{id}/reset-pwd   # 重置密码
```

### 5.2 请求头规范

```http
Content-Type: application/json
Authorization: Bearer <token>
X-Request-ID: <uuid>           # 请求追踪ID
X-Language: zh-CN              # 多语言标识
X-Timezone: Asia/Shanghai      # 时区信息
```

### 5.3 响应格式规范

```json
// ✅ 成功响应
{
  "code": 0,
  "data": {
    "id": "10001",
    "name": "张三",
    "email": "zhangsan@example.com"
  },
  "message": "success",
  "success": true,
  "timestamp": 1699999999999
}

// ✅ 分页响应
{
  "code": 0,
  "data": {
    "items": [...],
    "total": 100,
    "page": 1,
    "pageSize": 20,
    "totalPages": 5
  },
  "message": "success",
  "success": true
}

// ❌ 错误响应示例
{
  "code": 40401,
  "data": null,
  "message": "用户不存在",
  "success": false,
  "details": {
    "field": "userId",
    "reason": "NOT_FOUND"
  }
}
```

### 5.4 错误码体系

| 错误码范围 | 含义 | 示例 |
|-----------|------|------|
| 0 | 成功 | `{"code": 0}` |
| 400xx | 参数/校验错误 | 40001=参数缺失, 40002=参数格式错误 |
| 401xx | 认证错误 | 40101=Token过期, 40102=Token无效 |
| 403xx | 权限错误 | 40301=无访问权限 |
| 404xx | 资源错误 | 40401=资源不存在 |
| 500xx | 服务端错误 | 50001=数据库异常, 50002=服务不可用 |

---

## 六、数据库设计规范

### 6.1 表命名规范

```sql
-- ✅ 统一小写下划线格式
-- 通用前缀：sys_（系统）、biz_（业务）、log_（日志）
sys_user
sys_role
sys_permission
biz_order
biz_product
log_operate

-- ❌ 错误示例
sysUser          -- 驼峰
sys-user         -- 中划线
SystemUser       -- PascalCase
```

### 6.2 字段命名规范

```sql
-- ✅ 主键
id              BIGINT PRIMARY KEY AUTO_INCREMENT

-- ✅ 通用字段（所有表必须包含）
created_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
created_by     VARCHAR(64) COMMENT '创建人'
updated_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
updated_by     VARCHAR(64) COMMENT '更新人'
deleted_at     DATETIME COMMENT '删除时间（软删除）'
deleted_by     VARCHAR(64) COMMENT '删除人'
version        INT DEFAULT 1 COMMENT '乐观锁版本号'

-- ✅ 业务字段
user_name      VARCHAR(100) NOT NULL COMMENT '用户名'
mobile_phone   VARCHAR(20) COMMENT '手机号'
email          VARCHAR(200) COMMENT '邮箱'
status         TINYINT DEFAULT 1 COMMENT '状态：1-正常，0-禁用'

-- ❌ 避免使用
userName       -- 驼峰
USER_NAME      -- 全大写
u_name         -- 缩写不规范
```

### 6.3 索引规范

```sql
-- ✅ 索引命名：idx_{表名}_{字段名}
idx_sys_user_mobile_phone
idx_sys_user_email
idx_sys_user_status

-- ✅ 联合索引：idx_{表名}_{字段1}_{字段2}
idx_sys_user_status_created_at

-- ✅ 唯一索引
uniq_sys_user_mobile_phone
```

---

## 七、前端组件规范

### 7.1 组件结构规范

```tsx
// ✅ 组件文件结构顺序
// 1. 导入
import React, { useState, useEffect } from 'react';
import { Button } from '@/components/common';
import { useUserStore } from '@/store';
import type { User } from '@/types';

// 2. 类型定义
interface UserCardProps {
  user: User;
  onEdit?: (id: string) => void;
  onDelete?: (id: string) => void;
}

// 3. 组件定义
export function UserCard({ user, onEdit, onDelete }: UserCardProps) {
  // 4. Hooks
  const [isExpanded, setIsExpanded] = useState(false);

  // 5. 事件处理
  const handleEdit = () => {
    onEdit?.(user.id);
  };

  // 6. 渲染
  return (
    <div className="user-card">
      <span>{user.name}</span>
      <Button onClick={handleEdit}>编辑</Button>
    </div>
  );
}

// 7. 默认导出
export default UserCard;
```

### 7.2 Props 规范

```tsx
// ✅ 使用 TypeScript 严格定义 props
interface ButtonProps {
  type?: 'primary' | 'secondary' | 'danger';
  size?: 'small' | 'medium' | 'large';
  disabled?: boolean;
  loading?: boolean;
  children: React.ReactNode;
  onClick?: (e: React.MouseEvent) => void;
}

// ✅ 使用扩展运算符传递剩余 props
function Button({ type = 'primary', size = 'medium', children, ...props }: ButtonProps) {
  return <button className={`btn btn-${type} btn-${size}`} {...props}>{children}</button>;
}

// ❌ 避免 any
function Button(props: any) { }
```

### 7.3 Hooks 规范

```tsx
// ✅ 自定义 Hooks 命名以 use 开头
function useUser(id: string) {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    fetchUser(id)
      .then(setUser)
      .catch(setError)
      .finally(() => setLoading(false));
  }, [id]);

  return { user, loading, error };
}

// ✅ 返回有名字的对象，方便解构
function usePagination(initialPage = 1) {
  const [page, setPage] = useState(initialPage);
  const [pageSize, setPageSize] = useState(20);

  return {
    page,
    pageSize,
    setPage,
    setPageSize,
    reset: () => setPage(initialPage)
  };
}
```

---

## 八、Git 提交规范

### 8.1 提交信息格式

```
<type>(<scope>): <subject>

<body>

<footer>
```

### 8.2 Type 类型定义

| Type | 说明 | 示例 |
|------|------|------|
| feat | 新功能 | `feat(user): 添加用户搜索功能` |
| fix | Bug修复 | `fix(order): 修复订单状态显示错误` |
| docs | 文档更新 | `docs: 更新API文档` |
| style | 格式调整（不影响功能） | `style: 格式化代码` |
| refactor | 重构（不修复bug不加功能） | `refactor(api): 重构接口层` |
| perf | 性能优化 | `perf: 优化列表渲染性能` |
| test | 测试相关 | `test: 添加用户模块单元测试` |
| chore | 构建/工具变更 | `chore: 升级依赖版本` |
| ci | CI配置变更 | `ci: 更新GitHub Actions配置` |

### 8.3 提交示例

```bash
# ✅ 正确示例
feat(user): 添加用户注册短信验证码功能

- 集成阿里云短信服务
- 添加短信发送频率限制（60秒/次）
- 实现图形验证码防刷

Closes #123

# ❌ 错误示例
fix bug                    # 缺少 type
Update README.md           # 缺少 type
WIP                         # 临时提交
```

---

## 九、异常处理规范

### 9.1 前端异常处理

```typescript
// ✅ 统一错误处理封装
class ApiException extends Error {
  constructor(
    public code: number,
    message: string,
    public details?: Record<string, string>
  ) {
    super(message);
    this.name = 'ApiException';
  }
}

// ✅ 使用 async/await 时统一 try-catch
async function fetchUser(id: string): Promise<User> {
  try {
    const response = await api.get(`/users/${id}`);
    return response.data;
  } catch (error) {
    if (error instanceof ApiException) {
      toast.error(error.message);
      throw error;
    }
    toast.error('网络请求失败');
    throw new ApiException(-1, '网络请求失败');
  }
}

// ❌ 避免空 catch
try {
  doSomething();
} catch (e) {
  // 静默吞噬错误
}
```

### 9.2 后端异常处理

```java
// ✅ 使用统一异常处理
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return Result.fail(40001, "参数校验失败", errors);
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.fail(50001, "系统异常，请稍后重试");
    }
}
```

---

## 十、AI 开发特殊约定

### 10.1 给 AI 的系统级约束

```
【代码生成约束】

1. 文件生成位置
   - 页面组件 → src/pages/{模块名}/
   - 业务组件 → src/components/{模块名}/
   - 工具函数 → src/utils/
   - API封装 → src/api/

2. 禁止事项
   - 禁止生成 console.log（用于调试）
   - 禁止使用 any 类型
   - 禁止修改 src/shared/ 目录下的基础设施代码
   - 禁止在组件内直接调用第三方 API（通过 api 层）

3. 必须事项
   - 所有组件必须使用 TypeScript
   - 所有函数必须有返回类型注解
   - 所有 API 调用必须通过 Loading/Error 状态
   - 所有用户输入必须做基本校验

4. 代码复杂度
   - 单个函数不超过 30 行
   - 单个组件不超过 200 行
   - 超过则必须拆分
```

### 10.2 AI 开发检查清单

```
【每次代码生成后自检】

□ 是否有类型定义？（无 any）
□ 是否有错误处理？（无裸 try-catch）
□ 是否有 loading 状态？
□ 是否有空状态处理？
□ 是否符合命名规范？
□ 是否在正确的目录下生成？
□ 是否有必要的注释？（复杂逻辑）
□ 是否遵循单文件职责原则？
```

---

## 附录：错误码速查表

| 错误码 | 说明 |
|--------|------|
| 0 | 成功 |
| 40001 | 必填参数缺失 |
| 40002 | 参数格式错误 |
| 40003 | 参数超出范围 |
| 40101 | Token已过期 |
| 40102 | Token无效 |
| 40103 | 签名验证失败 |
| 40301 | 无访问权限 |
| 40302 | 无操作权限 |
| 40401 | 资源不存在 |
| 40402 | 接口不存在 |
| 50001 | 数据库错误 |
| 50002 | 服务内部错误 |
| 50003 | 第三方服务调用失败 |

---

*本规范会持续更新，最后更新时间：2026-09-14*
