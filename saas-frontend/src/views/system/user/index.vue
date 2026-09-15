<template>
  <div class="user-management">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-form :model="queryParams" inline>
        <el-form-item label="用户名">
          <el-input
            v-model="queryParams.username"
            placeholder="请输入用户名"
            clearable
            @clear="handleSearch"
          />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input
            v-model="queryParams.phone"
            placeholder="请输入手机号"
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
        v-if="hasPermission('system:user:create')"
        type="primary"
        @click="handleCreate"
      >
        新增用户
      </el-button>
      <el-button
        v-if="hasPermission('system:user:export')"
        @click="handleExport"
      >
        导出
      </el-button>
    </div>

    <!-- 数据表格 -->
    <el-table
      v-loading="loading"
      :data="tableData"
      :stripe="true"
      :border="true"
      class="user-table"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="username" label="用户名" min-width="120" />
      <el-table-column prop="nickname" label="昵称" min-width="120" />
      <el-table-column prop="deptName" label="部门" min-width="120" />
      <el-table-column prop="phone" label="手机号" min-width="120" />
      <el-table-column prop="email" label="邮箱" min-width="160" />
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
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button
            v-if="hasPermission('system:user:edit')"
            type="primary"
            link
            @click="handleEdit(row)"
          >
            编辑
          </el-button>
          <el-button
            v-if="hasPermission('system:user:delete')"
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

    <!-- 用户弹窗 -->
    <UserModal
      v-model:visible="modalVisible"
      :data="currentUser"
      @success="handleSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { usePermissions } from '@/composables/usePermissions';
import { formatDate } from '@/utils/format';
import { getUserList, deleteUser } from '@/api/system/user';
import type { UserInfo, UserQueryParams } from '@/types/user.d';
import UserModal from './components/UserModal.vue';

const { hasPermission } = usePermissions();

const loading = ref(false);
const tableData = ref<UserInfo[]>([]);
const queryParams = reactive<UserQueryParams>({
  username: '',
  phone: '',
  status: undefined,
  page: 1,
  pageSize: 10,
});

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const modalVisible = ref(false);
const currentUser = ref<UserInfo | null>(null);

async function loadData(): Promise<void> {
  try {
    loading.value = true;
    const result = await getUserList({
      ...queryParams,
      page: pagination.current,
      pageSize: pagination.pageSize,
    });
    tableData.value = result.data.list;
    pagination.total = result.data.total;
  } catch {
    // 加载失败处理
  } finally {
    loading.value = false;
  }
}

function handleSearch(): void {
  pagination.current = 1;
  loadData();
}

function handleReset(): void {
  queryParams.username = '';
  queryParams.phone = '';
  queryParams.status = undefined;
  handleSearch();
}

function handleCreate(): void {
  currentUser.value = null;
  modalVisible.value = true;
}

function handleEdit(row: UserInfo): void {
  currentUser.value = { ...row };
  modalVisible.value = true;
}

async function handleDelete(row: UserInfo): Promise<void> {
  try {
    await ElMessageBox.confirm(`确定要删除用户"${row.username}"吗？`, '提示', {
      type: 'warning',
    });
    await deleteUser(row.id);
    ElMessage.success('删除成功');
    loadData();
  } catch {
    // 取消或失败处理
  }
}

function handleExport(): void {
  ElMessage.info('导出功能开发中');
}

function handleSuccess(): void {
  modalVisible.value = false;
  loadData();
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
.user-management {
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

.user-table {
  margin-bottom: var(--spacing-lg);
}

.pagination {
  display: flex;
  justify-content: flex-end;
}
</style>
