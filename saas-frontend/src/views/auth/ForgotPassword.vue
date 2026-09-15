<template>
  <div class="forgot-password-container">
    <div class="forgot-password-card">
      <div class="forgot-password-header">
        <h1 class="forgot-password-title">找回密码</h1>
        <p class="forgot-password-subtitle">请输入您的注册邮箱</p>
      </div>

      <el-form ref="formRef" :model="form" :rules="rules" class="forgot-password-form" @submit.prevent="handleSubmit">
        <el-form-item prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" prefix-icon="Message" size="large" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" size="large" class="submit-button" :loading="loading" @click="handleSubmit">
            {{ loading ? '发送中...' : '发送验证码' }}
          </el-button>
        </el-form-item>
      </el-form>

      <div class="forgot-password-footer">
        <el-link type="primary" :underline="false" @click="$router.push('/auth/login')">返回登录</el-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import type { FormInstance, FormRules } from 'element-plus';
import { validateEmail } from '@/utils/validate';

const router = useRouter();
const formRef = ref<FormInstance>();
const loading = ref(false);

const form = reactive({
  email: '',
});

const rules: FormRules = {
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    {
      validator: (_rule, value, callback) => {
        const result = validateEmail(value);
        if (result.valid) {
          callback();
        } else {
          callback(new Error(result.message || '邮箱格式不正确'));
        }
      },
      trigger: 'blur',
    },
  ],
};

async function handleSubmit(): Promise<void> {
  if (!formRef.value) return;

  try {
    const valid = await formRef.value.validate();
    if (!valid) return;

    loading.value = true;
    // TODO: 调用发送验证码 API
    ElMessage.success('验证码已发送到您的邮箱');
    router.push('/auth/reset-password');
  } catch {
    // 发送失败
  } finally {
    loading.value = false;
  }
}
</script>

<style scoped>
.forgot-password-container {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.forgot-password-card {
  width: 400px;
  padding: var(--spacing-xxl);
  background-color: var(--color-bg-base);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-lg);
}

.forgot-password-header {
  text-align: center;
  margin-bottom: var(--spacing-xxl);
}

.forgot-password-title {
  margin: 0 0 var(--spacing-sm);
  font-size: var(--font-size-xxl);
  font-weight: var(--font-weight-bold);
  color: var(--color-text-primary);
}

.forgot-password-subtitle {
  margin: 0;
  font-size: var(--font-size-md);
  color: var(--color-text-secondary);
}

.forgot-password-form {
  margin-bottom: var(--spacing-lg);
}

.submit-button {
  width: 100%;
}

.forgot-password-footer {
  text-align: center;
}
</style>
