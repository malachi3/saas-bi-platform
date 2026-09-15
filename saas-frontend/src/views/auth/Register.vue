<template>
  <div class="register-container">
    <div class="register-card">
      <div class="register-header">
        <h1 class="register-title">用户注册</h1>
        <p class="register-subtitle">创建您的账户</p>
      </div>

      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        class="register-form"
        @submit.prevent="handleRegister"
      >
        <!-- 租户选择 -->
        <el-form-item prop="tenantId">
          <el-select v-model="form.tenantId" placeholder="请选择租户" class="register-select">
            <el-option
              v-for="tenant in tenantList"
              :key="tenant.id"
              :label="tenant.name"
              :value="tenant.id"
            />
          </el-select>
        </el-form-item>

        <!-- 用户名 -->
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" size="large" />
        </el-form-item>

        <!-- 昵称 -->
        <el-form-item prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入昵称" prefix-icon="UserFilled" size="large" />
        </el-form-item>

        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            size="large"
            show-password
          />
        </el-form-item>

        <!-- 确认密码 -->
        <el-form-item prop="confirmPassword">
          <el-input
            v-model="form.confirmPassword"
            type="password"
            placeholder="请确认密码"
            prefix-icon="Lock"
            size="large"
            show-password
          />
        </el-form-item>

        <!-- 注册按钮 -->
        <el-form-item>
          <el-button type="primary" size="large" class="register-button" :loading="loading" @click="handleRegister">
            {{ loading ? '注册中...' : '注 册' }}
          </el-button>
        </el-form-item>
      </el-form>

      <div class="register-footer">
        <span>已有账号？</span>
        <el-link type="primary" :underline="false" @click="$router.push('/auth/login')">立即登录</el-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useTenantStore } from '@/stores/tenant';
import type { FormInstance, FormRules } from 'element-plus';
import { validatePassword, validateConfirmPassword } from '@/utils/validate';

const router = useRouter();
const tenantStore = useTenantStore();

const formRef = ref<FormInstance>();
const loading = ref(false);
const tenantList = ref<{ id: number; name: string }[]>([]);

const form = reactive({
  tenantId: '',
  username: '',
  nickname: '',
  password: '',
  confirmPassword: '',
});

const rules: FormRules = {
  tenantId: [{ required: true, message: '请选择租户', trigger: 'change' }],
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度为3-20位', trigger: 'blur' },
  ],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      validator: (_rule, value, callback) => {
        const result = validatePassword(value);
        if (result.valid) {
          callback();
        } else {
          callback(new Error(result.message));
        }
      },
      trigger: 'blur',
    },
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (_rule, value, callback) => {
        const result = validateConfirmPassword(form.password, value);
        if (result.valid) {
          callback();
        } else {
          callback(new Error(result.message));
        }
      },
      trigger: 'blur',
    },
  ],
};

async function handleRegister(): Promise<void> {
  if (!formRef.value) return;

  try {
    const valid = await formRef.value.validate();
    if (!valid) return;

    loading.value = true;
    // TODO: 调用注册 API
    router.push('/auth/login');
  } catch {
    // 注册失败
  } finally {
    loading.value = false;
  }
}

onMounted(async () => {
  try {
    await tenantStore.loadTenantList();
    tenantList.value = tenantStore.tenantList;
    if (tenantList.value.length === 1) {
      form.tenantId = String(tenantList.value[0].id);
    }
  } catch {
    // 加载失败
  }
});
</script>

<style scoped>
.register-container {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-card {
  width: 400px;
  padding: var(--spacing-xxl);
  background-color: var(--color-bg-base);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-lg);
}

.register-header {
  text-align: center;
  margin-bottom: var(--spacing-xxl);
}

.register-title {
  margin: 0 0 var(--spacing-sm);
  font-size: var(--font-size-xxl);
  font-weight: var(--font-weight-bold);
  color: var(--color-text-primary);
}

.register-subtitle {
  margin: 0;
  font-size: var(--font-size-md);
  color: var(--color-text-secondary);
}

.register-form {
  margin-bottom: var(--spacing-lg);
}

.register-select {
  width: 100%;
}

.register-button {
  width: 100%;
}

.register-footer {
  text-align: center;
  color: var(--color-text-secondary);
}
</style>
