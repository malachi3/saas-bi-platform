<template>
  <el-dialog
    v-model="visible"
    title="分配菜单权限"
    width="600px"
    @close="handleClose"
  >
    <div class="menu-tree">
      <el-tree
        ref="treeRef"
        :data="menuTree"
        :props="{ label: 'name', children: 'children' }"
        node-key="id"
        :default-expand-all="true"
        :show-checkbox="true"
      />
    </div>

    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" :loading="loading" @click="handleSubmit">
        确定
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { ElMessage } from 'element-plus';
import type { ElTree } from 'element-plus';
import { getMenuTree, getRoleMenus, assignRoleMenus } from '@/api/system/menu';
import type { MenuInfo } from '@/types/menu.d';

interface Props {
  visible: boolean;
  roleId: number | null;
}

interface Emits {
  (e: 'update:visible', value: boolean): void;
  (e: 'success'): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const treeRef = ref<InstanceType<typeof ElTree>>();
const loading = ref(false);
const menuTree = ref<MenuInfo[]>([]);
const checkedKeys = ref<number[]>([]);

const visible = ref(false);

watch(() => props.visible, async (val) => {
  visible.value = val;
  if (val && props.roleId) {
    await loadMenus();
    await loadRoleMenus();
  }
});

watch(visible, (val) => {
  emit('update:visible', val);
});

async function loadMenus(): Promise<void> {
  try {
    const result = await getMenuTree();
    menuTree.value = result;
  } catch {
    // 加载失败
  }
}

async function loadRoleMenus(): Promise<void> {
  if (!props.roleId) return;

  try {
    const result = await getRoleMenus(props.roleId);
    checkedKeys.value = result;
    // 设置选中状态
    nextTick(() => {
      if (treeRef.value) {
        treeRef.value.setCheckedKeys(result);
      }
    });
  } catch {
    // 加载失败
  }
}

async function handleSubmit(): Promise<void> {
  if (!props.roleId || !treeRef.value) return;

  try {
    loading.value = true;
    const checkedNodes = treeRef.value.getCheckedNodes(false, true);
    const menuIds = checkedNodes.map((node: MenuInfo) => node.id);
    await assignRoleMenus(props.roleId, menuIds);
    ElMessage.success('分配成功');
    emit('success');
    handleClose();
  } catch {
    // 分配失败
  } finally {
    loading.value = false;
  }
}

function handleClose(): void {
  visible.value = false;
  checkedKeys.value = [];
  treeRef.value?.setCheckedKeys([]);
}

import { nextTick } from 'vue';
</script>

<style scoped>
.menu-tree {
  max-height: 400px;
  overflow-y: auto;
}
</style>
