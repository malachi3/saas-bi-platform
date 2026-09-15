import React, { useState } from 'react';
import { tokens, colors, spacing } from '@/tokens';
import {
  Button,
  Input,
  Select,
  Card,
  Modal,
  Table,
  Badge,
  Tag,
  TableColumn,
} from '@/components/common';

// 示例用户数据
interface User {
  id: number;
  name: string;
  email: string;
  status: 'active' | 'inactive';
  role: string;
}

const mockUsers: User[] = [
  { id: 1, name: '张三', email: 'zhangsan@example.com', status: 'active', role: '管理员' },
  { id: 2, name: '李四', email: 'lisi@example.com', status: 'active', role: '编辑' },
  { id: 3, name: '王五', email: 'wangwu@example.com', status: 'inactive', role: '访客' },
  { id: 4, name: '赵六', email: 'zhaoliu@example.com', status: 'active', role: '编辑' },
];

const columns: TableColumn<User>[] = [
  {
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    width: 80,
  },
  {
    title: '姓名',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    key: 'email',
  },
  {
    title: '状态',
    dataIndex: 'status',
    key: 'status',
    render: (status) => (
      <Badge
        status={status === 'active' ? 'success' : 'default'}
        dot
      />
    ),
  },
  {
    title: '角色',
    dataIndex: 'role',
    key: 'role',
    render: (role) => {
      const variant = role === '管理员' ? 'primary' : role === '编辑' ? 'success' : 'default';
      return <Tag variant={variant}>{role}</Tag>;
    },
  },
  {
    title: '操作',
    key: 'actions',
    width: 150,
    render: (_, record) => (
      <div style={{ display: 'flex', gap: spacing[2] }}>
        <Button size="sm" variant="ghost">编辑</Button>
        <Button size="sm" variant="ghost">删除</Button>
      </div>
    ),
  },
];

export function DemoPage() {
  const [modalOpen, setModalOpen] = useState(false);
  const [inputValue, setInputValue] = useState('');
  const [selectValue, setSelectValue] = useState('');

  const roleOptions = [
    { label: '管理员', value: 'admin' },
    { label: '编辑', value: 'editor' },
    { label: '访客', value: 'guest' },
  ];

  return (
    <div style={{ padding: spacing[6], maxWidth: '1200px', margin: '0 auto' }}>
      <h1 style={{ marginBottom: spacing[6], color: colors.text.primary }}>
        设计系统演示页面
      </h1>

      {/* 按钮示例 */}
      <Card title="按钮组件" style={{ marginBottom: spacing[6] }}>
        <div style={{ display: 'flex', gap: spacing[3], flexWrap: 'wrap' }}>
          <Button variant="primary">主要按钮</Button>
          <Button variant="secondary">次要按钮</Button>
          <Button variant="ghost">幽灵按钮</Button>
          <Button variant="danger">危险按钮</Button>
          <Button variant="primary" loading>加载中</Button>
          <Button variant="primary" disabled>禁用</Button>
        </div>
        <div style={{ display: 'flex', gap: spacing[2], marginTop: spacing[4] }}>
          <Button size="sm">小按钮</Button>
          <Button size="md">中按钮</Button>
          <Button size="lg">大按钮</Button>
        </div>
      </Card>

      {/* 输入框示例 */}
      <Card title="输入框组件" style={{ marginBottom: spacing[6] }}>
        <div style={{ display: 'flex', flexDirection: 'column', gap: spacing[4], maxWidth: '400px' }}>
          <Input
            placeholder="请输入用户名"
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
            allowClear
          />
          <Input
            placeholder="搜索..."
            prefix="🔍"
          />
          <Input
            placeholder="带后缀"
            suffix="@example.com"
          />
        </div>
      </Card>

      {/* 选择器示例 */}
      <Card title="选择器组件" style={{ marginBottom: spacing[6] }}>
        <div style={{ maxWidth: '300px' }}>
          <Select
            placeholder="请选择角色"
            options={roleOptions}
            value={selectValue}
            onChange={(val) => setSelectValue(String(val))}
          />
        </div>
      </Card>

      {/* 表格示例 */}
      <Card
        title="表格组件"
        extra={<Button variant="primary" onClick={() => setModalOpen(true)}>新增用户</Button>}
        style={{ marginBottom: spacing[6] }}
      >
        <Table
          columns={columns}
          dataSource={mockUsers}
          rowKey="id"
        />
      </Card>

      {/* 徽标和标签示例 */}
      <Card title="徽标 & 标签组件" style={{ marginBottom: spacing[6] }}>
        <div style={{ display: 'flex', gap: spacing[4], alignItems: 'center' }}>
          <Badge status="success" dot>在线</Badge>
          <Badge status="warning" dot>忙碌</Badge>
          <Badge status="error" dot>离线</Badge>
          <Badge status="processing" dot>处理中</Badge>
          <Badge count={5} />
          <Badge count={99} overflowCount={10} />
        </div>
        <div style={{ display: 'flex', gap: spacing[2], marginTop: spacing[4] }}>
          <Tag variant="default">默认</Tag>
          <Tag variant="primary">主要</Tag>
          <Tag variant="success">成功</Tag>
          <Tag variant="warning">警告</Tag>
          <Tag variant="error" closable onClose={() => {}}>可关闭</Tag>
        </div>
      </Card>

      {/* 模态框示例 */}
      <Modal
        open={modalOpen}
        title="新增用户"
        onClose={() => setModalOpen(false)}
        onOk={() => setModalOpen(false)}
      >
        <div style={{ display: 'flex', flexDirection: 'column', gap: spacing[4] }}>
          <div>
            <label style={{ display: 'block', marginBottom: spacing[2], color: colors.text.secondary }}>
              用户名
            </label>
            <Input placeholder="请输入用户名" />
          </div>
          <div>
            <label style={{ display: 'block', marginBottom: spacing[2], color: colors.text.secondary }}>
              邮箱
            </label>
            <Input placeholder="请输入邮箱" />
          </div>
          <div>
            <label style={{ display: 'block', marginBottom: spacing[2], color: colors.text.secondary }}>
              角色
            </label>
            <Select placeholder="请选择角色" options={roleOptions} />
          </div>
        </div>
      </Modal>
    </div>
  );
}
