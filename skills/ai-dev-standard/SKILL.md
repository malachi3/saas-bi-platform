---
name: ai-dev-standard
description: AI 辅助开发规范应用。当用户要求 AI 协助开发、检查代码质量、生成前后端代码、制定接口规范、审查代码时自动触发。提供代码生成检查清单、命名规范验证、接口规范检查、Git 提交规范等功能。
---

# AI 辅助开发规范应用

## 概述

在 AI 辅助开发过程中，自动应用阿里巴巴开发规范，确保代码风格统一、接口规范一致、命名规范遵守。

## 核心约束

### 文件生成位置
- 页面组件 → `src/pages/{模块名}/`
- 业务组件 → `src/components/{模块名}/`
- 工具函数 → `src/utils/`
- API封装 → `src/api/`

### 禁止事项
- 禁止生成 `console.log`
- 禁止使用 `any` 类型
- 禁止修改 `src/shared/` 目录
- 禁止组件内直接调用第三方 API

### 必须事项
- 所有组件使用 TypeScript
- 所有函数有返回类型注解
- 所有 API 调用有 Loading/Error 状态
- 所有用户输入有基本校验

### 代码复杂度
- 单函数 ≤ 30 行
- 单组件 ≤ 200 行

## 代码审查清单

生成或修改代码后，自动检查：

```
□ 类型定义完整（无 any）
□ 错误处理完善（无裸 try-catch）
□ Loading 状态处理
□ 空状态处理
□ 命名符合规范
□ 在正确目录生成
□ 复杂逻辑有注释
□ 单文件职责原则
```

## 命名规范速查

| 类型 | 规范 | 示例 |
|------|------|------|
| 变量 | camelCase | `userName`, `isActive` |
| 常量 | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT` |
| 函数 | 动词/动词+名词 | `getUserById`, `handleClick` |
| 类名 | PascalCase | `UserService`, `OrderController` |
| 组件文件 | PascalCase | `UserCard.tsx` |
| 数据库表 | 小写下划线 | `sys_user` |
| 接口URL | RESTful | `/api/v1/users/{id}` |

## 接口响应格式

```typescript
interface ApiResponse<T> {
  code: number;      // 0 = 成功
  data: T;
  message: string;
  success: boolean;
}
```

## 错误码速查

| 范围 | 含义 |
|------|------|
| 0 | 成功 |
| 400xx | 参数错误 |
| 401xx | 认证错误 |
| 403xx | 权限错误 |
| 404xx | 资源错误 |
| 500xx | 服务错误 |

## Git 提交 Type

`feat` | `fix` | `docs` | `style` | `refactor` | `perf` | `test` | `chore`

格式：`<type>(<scope>): <subject>`

## 完整规范

详细规范请参考 `references/full-standard.md`

---

## 设计系统约束

### 设计系统位置
```
/Users/malachi/Documents/Qoder/2026-09-14/4b67a888/design-system/src/
├── tokens/index.ts           # 设计变量（颜色/间距/字体/阴影）
├── components/common/        # 通用组件
└── styles/variables.css     # CSS 变量
```

### 禁止事项（样式专项）
- 禁止硬编码颜色值（必须使用 `tokens.colors`）
- 禁止硬编码间距值（必须使用 `tokens.spacing`）
- 禁止硬编码圆角（必须使用 `tokens.radius`）
- 禁止硬编码阴影（必须使用 `tokens.shadows`）
- 禁止自行创建与现有组件重复的 UI 组件

### 必须事项（样式专项）
- 所有样式值必须引用 design tokens
- 优先使用设计系统提供的组件
- 新增组件前先检查 `components/common/` 是否存在
- 颜色使用：`tokens.colors.primary[500]`
- 间距使用：`tokens.spacing[4]`（对应 16px）
- 字体使用：`tokens.typography.fontSize.sm`

### 组件优先级
1. 设计系统组件（Button/Input/Card/Table...）
2. 组合现有组件
3. 新建原子组件（需说明原因）

### 样式检查清单
```
□ 颜色是否来自 tokens.colors？
□ 间距是否来自 tokens.spacing？
□ 圆角是否来自 tokens.radius？
□ 阴影是否来自 tokens.shadows？
□ 组件是否已存在于 common 目录？
```

### 使用示例
```tsx
import { Button, Card } from '@/design-system/src/components/common';
import { colors, spacing, typography } from '@/design-system/src/tokens';

// ✅ 正确
<div style={{ color: colors.primary[500], padding: spacing[4] }}>
  <Button variant="primary">提交</Button>
</div>

// ❌ 错误
<div style={{ color: '#1677ff', padding: '16px' }}>
  <button style={{ backgroundColor: '#fff' }}>提交</button>
</div>
```
