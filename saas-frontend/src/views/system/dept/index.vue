<template>
  <div class="dept-management">
    <!-- 操作栏 -->
    <div class="toolbar">
      <el-button
        v-if="hasPermission('system:dept:create')"
        type="primary"
        @click="handleCreate"
      >
        新增部门
      </el-button>
    </div>

    <!-- 部门树 -->
    <el-table
      v-loading="loading"
      :data="tableData"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      row-key="id"
      default-expand-all
      class="dept-table"
    >
      <el-table-column prop="name" label="部门名称" min-width="200" />
      <el-table-column prop="sort" label="排序" width="100" />
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
            v-if="hasPermission('system:dept:edit')"
            type="primary"
            link
            @click="handleEdit(row)"
          >
            编辑
          </el-button>
          <el-button
            v-if="hasPermission('system:dept:delete')"
            type="danger"
            link
            @click="handleDelete(row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { usePermissions } from '@/composables/usePermissions';
import { formatDate } from '@/utils/format';
import { getDeptTree, deleteDept } from '@/api/system/dept';
import type { DeptTree } from '@/api/system/dept';

const { hasPermission } = usePermissions();

const loading = ref(false);
const tableData = ref<DeptTree[]>([]);

async function loadData(): Promise<void> {
  try {
    loading.value = true;
    const result = await getDeptTree();
    tableData.value = result;
  } catch {
    // 加载失败
  } finally {
    loading.value = false;
  }
}

function handleCreate(): void {
  ElMessage.info('新增部门功能开发中');
}

function handleEdit(row: DeptTree): void {
  ElMessage.info(`编辑部门: ${row.name}`);
}

async function handleDelete(row: DeptTree): Promise<void> {
  try {
    await ElMessageBox.confirm(`确定要删除部门"${row.name}"吗？`, '提示', {
      type: 'warning',
    });
    await deleteDept(row.id);
    ElMessage.success('删除成功');
    loadData();
  } catch {
    // 取消或失败
  }
}

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.dept-management {
  background-color: var(--color-bg-base);
  border-radius: var(--radius-lg);
  padding: var(--spacing-lg);
}

.toolbar {
  margin-bottom: var(--spacing-lg);
}

.dept-table {
  margin-bottom: var(--spacing-lg);
}
</style>
