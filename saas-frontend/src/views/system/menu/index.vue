<template>
  <div class="menu-management">
    <!-- 操作栏 -->
    <div class="toolbar">
      <el-button
        v-if="hasPermission('system:menu:create')"
        type="primary"
        @click="handleCreate"
      >
        新增菜单
      </el-button>
    </div>

    <!-- 菜单表格 -->
    <el-table
      v-loading="loading"
      :data="tableData"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      row-key="id"
      default-expand-all
      class="menu-table"
    >
      <el-table-column prop="name" label="菜单名称" min-width="180" />
      <el-table-column prop="icon" label="图标" width="80">
        <template #default="{ row }">
          <el-icon v-if="row.icon"><component :is="row.icon" /></el-icon>
        </template>
      </el-table-column>
      <el-table-column prop="path" label="路由路径" min-width="180" />
      <el-table-column prop="component" label="组件路径" min-width="160" />
      <el-table-column prop="sort" label="排序" width="80" />
      <el-table-column prop="visible" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.visible === 1 ? 'success' : 'info'">
            {{ row.visible === 1 ? '显示' : '隐藏' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button
            v-if="hasPermission('system:menu:edit')"
            type="primary"
            link
            @click="handleEdit(row)"
          >
            编辑
          </el-button>
          <el-button
            v-if="hasPermission('system:menu:delete')"
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
import { getMenuList, deleteMenu } from '@/api/system/menu';
import type { MenuInfo } from '@/types/menu.d';

const { hasPermission } = usePermissions();

const loading = ref(false);
const tableData = ref<MenuInfo[]>([]);

async function loadData(): Promise<void> {
  try {
    loading.value = true;
    const result = await getMenuList();
    tableData.value = result;
  } catch {
    // 加载失败
  } finally {
    loading.value = false;
  }
}

function handleCreate(): void {
  ElMessage.info('新增菜单功能开发中');
}

function handleEdit(row: MenuInfo): void {
  ElMessage.info(`编辑菜单: ${row.name}`);
}

async function handleDelete(row: MenuInfo): Promise<void> {
  try {
    await ElMessageBox.confirm(`确定要删除菜单"${row.name}"吗？`, '提示', {
      type: 'warning',
    });
    await deleteMenu(row.id);
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
.menu-management {
  background-color: var(--color-bg-base);
  border-radius: var(--radius-lg);
  padding: var(--spacing-lg);
}

.toolbar {
  margin-bottom: var(--spacing-lg);
}

.menu-table {
  margin-bottom: var(--spacing-lg);
}
</style>
