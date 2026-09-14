# SaaS 多租户平台 - 项目开发规范

> 本项目为面向成长型企业的轻量级 BI 平台，采用 Java + Vue3 技术栈，支持 1000+ 租户规模。

---

## 一、技术栈

| 层级 | 技术选型 |
|------|----------|
| 后端 | Java 21 + Spring Boot 3.x + MyBatis-Plus |
| 前端 | Vue 3 + Vite + TypeScript + Pinia |
| 数据库 | PostgreSQL 16 |
| 缓存 | Redis 7 |
| 消息队列 | RabbitMQ |
| 部署 | Docker + K8s |

---

## 二、架构规范

### 2.1 多租户隔离策略

| 租户类型 | 隔离级别 | 适用场景 |
|----------|----------|----------|
| SHARED | 共享 Schema | 普通租户（预计 80%） |
| SCHEMA | 独立 Schema | 中型客户 |
| DATABASE | 独立数据库 | 大客户/高安全要求 |

### 2.2 目录结构

```
项目根目录/
├── saas-backend/           # Java 后端
│   └── src/main/java/com/saas/
│       ├── common/        # 公共模块
│       │   ├── core/      # 核心基类
│       │   ├── tenant/    # 多租户支持
│       │   └── web/       # Web 公共组件
│       ├── module/        # 业务模块
│       │   ├── system/    # 系统管理
│       │   ├── tenant/    # 租户管理
│       │   └── billing/   # 计费模块
│       └── saas-app/      # 应用入口
│
└── saas-frontend/         # Vue3 前端
    ├── src/
    │   ├── api/           # API 封装
    │   ├── components/    # 公共组件
    │   ├── composables/   # Hooks
    │   ├── layouts/       # 布局
    │   ├── router/        # 路由
    │   ├── stores/        # Pinia
    │   ├── types/         # 类型定义
    │   ├── utils/         # 工具
    │   └── views/        # 页面
    └── design-system/     # 设计系统引用
```

---

## 三、前端开发规范

### 3.1 设计系统

**必须使用设计系统，禁止硬编码样式：**

```typescript
// ✅ 正确：使用 design-system tokens
import { tokens } from '@/design-system/src';
import { Button } from '@/design-system/src/components';

// 使用颜色
background-color: tokens.colorPrimary;

// 使用间距
padding: tokens.spacingMedium;

// ✅ 正确：使用设计系统组件
import { DsButton, DsInput, DsTable } from '@/design-system/src';

// ❌ 错误：硬编码颜色/间距
background-color: #1890ff;      // 禁止
padding: 16px;                   // 禁止
```

**设计系统路径：** `/path/to/design-system/src/`

### 3.2 样式规范

| 规则 | 说明 |
|------|------|
| 必须使用 tokens | 所有颜色、间距、字体必须从 design-system 引用 |
| 禁止硬编码 | 不允许 `#fff`、`16px` 等硬编码值 |
| 优先使用组件 | 优先使用 design-system 提供的组件 |

### 3.3 组件规范

```typescript
// 命名：PascalCase
// 文件：PascalCase.tsx
// 导出：命名导出 + 默认导出

// ✅ 正确
export interface UserCardProps {
  name: string;
  avatar?: string;
}

export function UserCard({ name, avatar }: UserCardProps) {
  return <div className="user-card">{name}</div>;
}

export default UserCard;

// ❌ 错误
function userCard() { }  // 小写
```

### 3.4 API 请求规范

```typescript
// 请求拦截器必须携带租户标识
axios.interceptors.request.use(config => {
  const tenantId = useTenantStore().currentTenant?.id;
  if (tenantId) {
    config.headers['X-Tenant-ID'] = tenantId;
  }
  return config;
});

// API 响应类型
interface ApiResponse<T> {
  code: number;
  data: T;
  message: string;
  success: boolean;
  timestamp: number;
}
```

---

## 四、后端开发规范

**遵循标准：** `/Users/malachi/.qoder-cn/skills/ai-dev-standard/references/full-standard.md`

### 4.1 核心规范摘要

| 规范 | 要求 |
|------|------|
| 命名 | camelCase(变量/方法)、PascalCase(类)、UPPER_SNAKE_CASE(常量) |
| 表命名 | 小写下划线：`sys_user`、`biz_order` |
| 接口 URL | RESTful：`/api/v1/users/{id}` |
| 通用字段 | `created_at`、`created_by`、`updated_at`、`updated_by`、`deleted_at`、`version` |

### 4.2 多租户实现

```java
// TenantContextHolder - 租户上下文
public class TenantContextHolder {
    private static final ThreadLocal<Long> TENANT_ID = new ThreadLocal<>();

    public static void setTenantId(Long tenantId) { TENANT_ID.set(tenantId); }
    public static Long getTenantId() { return TENANT_ID.get(); }
    public static void clear() { TENANT_ID.remove(); }
}

// 租户拦截器
@Aspect
@Component
public class TenantInterceptor {
    @Around("@annotation(tenant)")
    public Object around(ProceedingJoinPoint point, Tenant tenant) {
        // 设置租户上下文
        TenantContextHolder.setTenantId(getTenantId());
        try {
            return point.proceed();
        } finally {
            TenantContextHolder.clear();
        }
    }
}
```

### 4.3 响应格式

```json
{
  "code": 0,
  "data": {},
  "message": "success",
  "success": true,
  "timestamp": 1699999999999
}
```

### 4.4 错误码体系

| 范围 | 含义 |
|------|------|
| 0 | 成功 |
| 400xx | 参数/校验错误 |
| 401xx | 认证错误 |
| 403xx | 权限错误 |
| 404xx | 资源错误 |
| 500xx | 服务端错误 |

---

## 五、Git 提交规范

**格式：** `<type>(<scope>): <subject>`

| Type | 说明 | 示例 |
|------|------|------|
| feat | 新功能 | `feat(tenant): 添加租户创建接口` |
| fix | Bug 修复 | `fix(user): 修复用户登录失败` |
| docs | 文档 | `docs: 更新 API 文档` |
| style | 格式 | `style: 格式化代码` |
| refactor | 重构 | `refactor(tenant): 重构租户隔离逻辑` |
| perf | 性能 | `perf: 优化查询性能` |
| test | 测试 | `test: 添加租户模块测试` |
| chore | 构建 | `chore: 升级依赖版本` |

---

## 六、AI 开发检查清单

生成代码后必须自检：

```
□ 类型定义完整（无 any）
□ 错误处理完善（无裸 try-catch）
□ 有 loading 状态处理
□ 有空状态/边界处理
□ 命名符合规范
□ 遵循设计系统规范
□ 单文件职责清晰
□ 函数行数 ≤ 30 行
□ 组件行数 ≤ 200 行
□ 有必要的注释（复杂逻辑）
```

---

## 七、模块开发优先级

| 优先级 | 模块 | 说明 |
|--------|------|------|
| P0 | 租户管理 | 多租户底座、租户创建/切换 |
| P0 | 用户认证 | 登录、注册、Token |
| P0 | 权限系统 | 角色、菜单、权限 |
| P1 | 租户套餐 | 套餐管理、配额限制 |
| P1 | 计费模块 | 订阅、账单 |
| P2 | 操作日志 | 审计日志 |
| P2 | 系统设置 | 租户配置 |

---

## 八、关键文件

- 后端规范：`/Users/malachi/.qoder-cn/skills/ai-dev-standard/references/full-standard.md`
- 设计系统：`/path/to/design-system/src/`
