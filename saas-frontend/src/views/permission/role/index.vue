<template>
  <div class="role-management">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-form :model="queryParams" inline>
        <el-form-item label="角色名称">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入角色名称"
            clearable
            @clear="handleSearch"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="请选择" clearable @clear="handleSearch">
            <el-option label="启用" :value="1" />
            <el-option label="禁用" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 操作栏 -->
    <div class="toolbar">
      <el-button
        v-if="hasPermission('system:role:create')"
        type="primary"
        @click="handleCreate"
      >
        新增角色
      </el-button>
    </div>

    <!-- 角色表格 -->
    <el-table
      v-loading="loading"
      :data="tableData"
      :stripe="true"
      :border="true"
      class="role-table"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="name" label="角色名称" min-width="150" />
      <el-table-column prop="code" label="角色编码" min-width="150" />
      <el-table-column prop="sort" label="排序" width="100" />
      <el-table-column prop="dataScope" label="数据权限" width="150">
        <template #default="{ row }">
          {{ getDataScopeLabel(row.dataScope) }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" min-width="160">
        <template #default="{ row }">
          {{ formatDate(row.createdAt) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="280" fixed="right">
        <template #default="{ row }">
          <el-button
            v-if="hasPermission('system:role:edit')"
            type="primary"
            link
            @click="handleEdit(row)"
          >
            编辑
          </el-button>
          <el-button
            v-if="hasPermission('system:role:assign')"
            type="success"
            link
            @click="handleAssignMenu(row)"
          >
            分配权限
          </el-button>
          <el-button
            v-if="hasPermission('system:role:delete')"
            type="danger"
            link
            @click="handleDelete(row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
      />
    </div>

    <!-- 分配菜单权限弹窗 -->
    <AssignMenu
      v-model:visible="assignMenuVisible"
      :role-id="currentRoleId"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { usePermissions } from '@/composables/usePermissions';
import { formatDate } from '@/utils/format';
import type { RoleInfo } from '@/types/menu.d';
import AssignMenu from './components/AssignMenu.vue';

const { hasPermission } = usePermissions();

const loading = ref(false);
const tableData = ref<RoleInfo[]>([]);
const queryParams = reactive({
  name: '',
  status: undefined as number | undefined,
  page: 1,
  pageSize: 10,
});

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const assignMenuVisible = ref(false);
const currentRoleId = ref<number | null>(null);

const dataScopeOptions = [
  { value: 1, label: '全部数据权限' },
  { value: 2, label: '自定义数据权限' },
  { value: 3, label: '本部门数据权限' },
  { value: 4, label: '本部门及以下数据权限' },
  { value: 5, label: '仅本人数据权限' },
];

function getDataScopeLabel(dataScope: number): string {
  return dataScopeOptions.find((item) => item.value === dataScope)?.label || '-';
}

function loadData(): void {
  loading.value = true;
  // TODO: 调用 API 获取角色列表
  setTimeout(() => {
    tableData.value = [];
    pagination.total = 0;
    loading.value = false;
  }, 500);
}

function handleSearch(): void {
  pagination.current = 1;
  loadData();
}

function handleReset(): void {
  queryParams.name = '';
  queryParams.status = undefined;
  handleSearch();
}

function handleCreate(): void {
  ElMessage.info('新增角色功能开发中');
}

function handleEdit(row: RoleInfo): void {
  ElMessage.info(`编辑角色: ${row.name}`);
}

function handleAssignMenu(row: RoleInfo): void {
  currentRoleId.value = row.id;
  assignMenuVisible.value = true;
}

async function handleDelete(row: RoleInfo): Promise<void> {
  try {
    await ElMessageBox.confirm(`确定要删除角色"${row.name}"吗？`, '提示', {
      type: 'warning',
    });
    ElMessage.success('删除成功');
    loadData();
  } catch {
    // 取消或失败
  }
}

function handleSizeChange(size: number): void {
  pagination.pageSize = size;
  loadData();
}

function handlePageChange(page: number): void {
  pagination.current = page;
  loadData();
}

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.role-management {
  background-color: var(--color-bg-base);
  border-radius: var(--radius-lg);
  padding: var(--spacing-lg);
}

.search-bar {
  margin-bottom: var(--spacing-lg);
}

.toolbar {
  margin-bottom: var(--spacing-lg);
}

.role-table {
  margin-bottom: var(--spacing-lg);
}

.pagination {
  display: flex;
  justify-content: flex-end;
}
</style>
