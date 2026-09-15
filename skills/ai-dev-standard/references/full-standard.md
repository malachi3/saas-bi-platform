# AI 辅助开发规范手册（完整版）

> 基于阿里巴巴Java开发手册、JavaScript编码规范整理，适用于前后端协同开发场景。

---

## 一、项目架构规范

### 1.1 目录结构约定

```
项目根目录/
├── src/
│   ├── api/           # API 封装层
│   ├── assets/        # 静态资源
│   ├── components/    # 公共组件
│   ├── config/        # 配置文件
│   ├── constants/     # 常量定义
│   ├── hooks/         # 自定义 Hooks
│   ├── layouts/       # 布局组件
│   ├── pages/         # 页面组件
│   │   └── UserManagement/
│   │       ├── index.tsx
│   │       ├── components/   # 页面私有组件
│   │       ├── hooks/         # 页面私有 hooks
│   │       └── types/         # 页面私有类型
│   ├── services/      # 业务逻辑层
│   ├── store/         # 状态管理
│   ├── styles/        # 全局样式
│   ├── types/         # 类型定义
│   └── utils/         # 工具函数
├── tests/
├── docs/
└── configs/
```

---

## 二、命名规范

### 2.1 通用命名规则

| 类型 | 规范 | 示例 | 错误示例 |
|------|------|------|----------|
| 变量 | camelCase | `userName`, `isActive` | `user_name` |
| 常量 | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT` | `maxRetryCount` |
| 函数 | 动词/动词+名词 | `getUserById`, `handleClick` | `user`, `click` |
| 类名 | PascalCase | `UserService` | `userService` |
| 组件文件 | PascalCase | `UserCard.tsx` | `user-card.tsx` |
| 配置文件 | kebab-case | `eslint-config.js` | `eslintConfig.js` |
| 数据库表 | 小写下划线 | `sys_user` | `SysUser` |
| 接口URL | RESTful | `/api/v1/users/{id}` | `/api/v1/getUserById` |

### 2.2 命名前缀后缀约定

```
布尔值：is/has/can/should + 动词    →  `isLoading`, `hasPermission`
数组：arr/list + 名词复数           →  `arrUsers`, `listItems`
对象：map/dict + 名词               →  `mapUserById`

Config   →  配置对象：`apiConfig`, `themeConfig`
Handler  →  事件处理：`clickHandler`
Factory  →  工厂类：`userFactory`
DTO      →  数据传输对象：`UserDTO`
Entity   →  数据库实体：`UserEntity`
```

---

## 三、代码风格规范

### 3.1 基础规则
- 缩进：2空格
- 引号：单引号
- 关键字后加空格：`if (isLoading)`
- 大括号不可省略

### 3.2 导出规范
```typescript
// 命名导出 + 默认导出分离
export interface User { }
export type UserStatus = 'active' | 'inactive';
export function UserCard() { }
export default UserCard;
```

---

## 四、类型与接口规范

### 4.1 TypeScript 类型定义

```typescript
// 使用具体类型，不使用 any
type UserId = string;
type Nullable<T> = T | null;

// 接口定义（用于对象结构）
interface User {
  id: UserId;
  name: string;
  email: string;
  createdAt: Date;
  updatedAt: Date;
}

// 复杂类型使用 type 别名
type UserList = User[];
type UserMap = Record<string, User>;
```

### 4.2 API 响应类型

```typescript
interface ApiResponse<T> {
  code: number;      // 0 = 成功
  data: T;
  message: string;
  success: boolean;
}

interface PaginatedResponse<T> {
  items: T[];
  total: number;
  page: number;
  pageSize: number;
  totalPages: number;
}
```

### 4.3 枚举使用

```typescript
const enum UserStatus {
  ACTIVE = 'active',
  INACTIVE = 'inactive',
  BANNED = 'banned'
}

const enum ErrorCode {
  SUCCESS = 0,
  NOT_FOUND = 404,
  UNAUTHORIZED = 401,
  SERVER_ERROR = 500
}
```

---

## 五、前后端接口规范

### 5.1 RESTful API 设计

```
GET    /api/v1/users          # 获取列表
GET    /api/v1/users/{id}      # 获取详情
POST   /api/v1/users           # 创建
PUT    /api/v1/users/{id}      # 完整更新
PATCH  /api/v1/users/{id}      # 部分更新
DELETE /api/v1/users/{id}      # 删除
```

### 5.2 请求头规范

```http
Content-Type: application/json
Authorization: Bearer <token>
X-Request-ID: <uuid>
X-Language: zh-CN
```

### 5.3 响应格式

```json
{
  "code": 0,
  "data": {},
  "message": "success",
  "success": true,
  "timestamp": 1699999999999
}
```

### 5.4 错误码体系

| 错误码范围 | 含义 | 示例 |
|-----------|------|------|
| 0 | 成功 | - |
| 400xx | 参数/校验错误 | 40001=参数缺失, 40002=格式错误 |
| 401xx | 认证错误 | 40101=Token过期, 40102=Token无效 |
| 403xx | 权限错误 | 40301=无访问权限 |
| 404xx | 资源错误 | 40401=资源不存在 |
| 500xx | 服务端错误 | 50001=数据库异常 |

---

## 六、数据库设计规范

### 6.1 表命名规范

```sql
-- 统一小写下划线格式
sys_user, sys_role, biz_order, log_operate
```

### 6.2 通用字段

```sql
-- 所有表必须包含
created_at     DATETIME
created_by     VARCHAR(64)
updated_at     DATETIME
updated_by     VARCHAR(64)
deleted_at     DATETIME  -- 软删除
version        INT       -- 乐观锁
```

### 6.3 索引命名

```sql
idx_{表名}_{字段名}
uniq_{表名}_{字段名}
idx_sys_user_mobile_phone
```

---

## 七、前端组件规范

### 7.1 组件结构

```tsx
// 1. 导入
// 2. 类型定义
// 3. 组件定义
// 4. Hooks
// 5. 事件处理
// 6. 渲染
export function Component() { }
export default Component;
```

### 7.2 Props 规范

```tsx
interface ButtonProps {
  type?: 'primary' | 'secondary' | 'danger';
  size?: 'small' | 'medium' | 'large';
  children: React.ReactNode;
  onClick?: (e: React.MouseEvent) => void;
}
```

### 7.3 Hooks 规范

```tsx
// 命名以 use 开头
function useUser(id: string) { }
function usePagination() { }
```

---

## 八、Git 提交规范

### 8.1 Type 类型

| Type | 说明 | 示例 |
|------|------|------|
| feat | 新功能 | `feat(user): 添加用户搜索` |
| fix | Bug修复 | `fix(order): 修复订单状态错误` |
| docs | 文档更新 | `docs: 更新API文档` |
| style | 格式调整 | `style: 格式化代码` |
| refactor | 重构 | `refactor(api): 重构接口层` |
| perf | 性能优化 | `perf: 优化列表渲染` |
| test | 测试相关 | `test: 添加单元测试` |
| chore | 构建/工具变更 | `chore: 升级依赖` |

### 8.2 格式

```
<type>(<scope>): <subject>

<body>

<footer>
```

---

## 九、异常处理规范

### 9.1 前端异常处理

```typescript
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
```

### 9.2 禁止事项

```typescript
// ❌ 空 catch（静默吞噬错误）
try { } catch (e) { }

// ✅ 必须处理或重新抛出
try { } catch (e) {
  toast.error('操作失败');
  throw e;
}
```

---

## 十、AI 开发检查清单

```
【代码生成后自检】

□ 是否有类型定义？（无 any）
□ 是否有错误处理？（无裸 try-catch）
□ 是否有 loading 状态？
□ 是否有空状态处理？
□ 是否符合命名规范？
□ 是否在正确的目录下生成？
□ 是否有必要的注释？（复杂逻辑）
□ 是否遵循单文件职责原则？
□ 单函数是否 ≤ 30 行？
□ 单组件是否 ≤ 200 行？
```
