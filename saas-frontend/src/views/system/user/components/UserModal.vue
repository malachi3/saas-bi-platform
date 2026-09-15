<template>
  <el-dialog
    v-model="visible"
    :title="isEdit ? '编辑用户' : '新增用户'"
    width="600px"
    @close="handleClose"
  >
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="100px"
    >
      <el-form-item label="用户名" prop="username">
        <el-input
          v-model="form.username"
          :disabled="isEdit"
          placeholder="请输入用户名"
        />
      </el-form-item>

      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="form.nickname" placeholder="请输入昵称" />
      </el-form-item>

      <el-form-item v-if="!isEdit" label="密码" prop="password">
        <el-input
          v-model="form.password"
          type="password"
          show-password
          placeholder="请输入密码"
        />
      </el-form-item>

      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入手机号" />
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱" />
      </el-form-item>

      <el-form-item label="部门" prop="deptId">
        <el-tree-select
          v-model="form.deptId"
          :data="deptTree"
          :props="{ label: 'name', value: 'id', children: 'children' }"
          placeholder="请选择部门"
          check-strictly
          :render-after-expand="false"
        />
      </el-form-item>

      <el-form-item label="角色" prop="roleIds">
        <el-select v-model="form.roleIds" multiple placeholder="请选择角色">
          <el-option
            v-for="role in roleOptions"
            :key="role.id"
            :label="role.name"
            :value="role.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="form.status">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="0">禁用</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="form.remark"
          type="textarea"
          :rows="3"
          placeholder="请输入备注"
        />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" :loading="loading" @click="handleSubmit">
        确定
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch } from 'vue';
import { ElMessage } from 'element-plus';
import type { FormInstance, FormRules } from 'element-plus';
import { createUser, updateUser } from '@/api/system/user';
import { getDeptTree } from '@/api/system/dept';
import type { UserInfo, UserFormData } from '@/types/user.d';

interface Props {
  visible: boolean;
  data?: UserInfo | null;
}

interface Emits {
  (e: 'update:visible', value: boolean): void;
  (e: 'success'): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

const formRef = ref<FormInstance>();
const loading = ref(false);
const deptTree = ref<{ id: number; name: string; children?: [] }[]>([]);
const roleOptions = ref<{ id: number; name: string }[]>([]);

const form = reactive<UserFormData>({
  username: '',
  nickname: '',
  password: '',
  phone: '',
  email: '',
  deptId: undefined,
  roleIds: [],
  status: 1,
  remark: '',
});

const isEdit = computed(() => !!props.data?.id);

const rules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度为3-20位', trigger: 'blur' },
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 32, message: '密码长度为6-32位', trigger: 'blur' },
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' },
  ],
  email: [
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' },
  ],
};

watch(() => props.visible, async (val) => {
  if (val) {
    await loadDeptTree();
    if (props.data) {
      Object.assign(form, {
        id: props.data.id,
        username: props.data.username,
        nickname: props.data.nickname,
        phone: props.data.phone,
        email: props.data.email,
        deptId: props.data.deptId,
        status: props.data.status,
        remark: props.data.remark,
        roleIds: [],
      });
    } else {
      resetForm();
    }
  }
});

async function loadDeptTree(): Promise<void> {
  try {
    const result = await getDeptTree();
    deptTree.value = result;
  } catch {
    // 加载失败
  }
}

function resetForm(): void {
  form.username = '';
  form.nickname = '';
  form.password = '';
  form.phone = '';
  form.email = '';
  form.deptId = undefined;
  form.roleIds = [];
  form.status = 1;
  form.remark = '';
  formRef.value?.clearValidate();
}

async function handleSubmit(): Promise<void> {
  if (!formRef.value) return;

  try {
    const valid = await formRef.value.validate();
    if (!valid) return;

    loading.value = true;

    if (isEdit.value) {
      await updateUser(form.id as number, form);
      ElMessage.success('编辑成功');
    } else {
      await createUser(form);
      ElMessage.success('新增成功');
    }

    emit('success');
  } catch {
    // 提交失败
  } finally {
    loading.value = false;
  }
}

function handleClose(): void {
  emit('update:visible', false);
  resetForm();
}
</script>
