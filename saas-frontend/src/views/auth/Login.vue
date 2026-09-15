<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h1 class="login-title">欢迎登录</h1>
        <p class="login-subtitle">SaaS 多租户平台</p>
      </div>

      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        class="login-form"
        @submit.prevent="handleLogin"
      >
        <!-- 租户选择 -->
        <el-form-item v-if="showTenant" prop="tenantId">
          <el-select
            v-model="form.tenantId"
            placeholder="请选择租户"
            class="login-select"
            clearable
          >
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
          <el-input
            v-model="form.username"
            placeholder="请输入用户名"
            prefix-icon="User"
            size="large"
            clearable
          />
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
            @keyup.enter="handleLogin"
          />
        </el-form-item>

        <!-- 验证码 -->
        <el-form-item prop="captcha">
          <div class="captcha-row">
            <el-input
              v-model="form.captcha"
              placeholder="请输入验证码"
              prefix-icon="CircleCheck"
              size="large"
              style="flex: 1"
              @keyup.enter="handleLogin"
            />
            <img
              :src="captchaImage"
              class="captcha-image"
              alt="验证码"
              @click="refreshCaptcha"
            />
          </div>
        </el-form-item>

        <!-- 记住登录 -->
        <div class="login-options">
          <el-checkbox v-model="form.remember">记住登录</el-checkbox>
          <el-link type="primary" :underline="false">忘记密码？</el-link>
        </div>

        <!-- 登录按钮 -->
        <el-form-item>
          <el-button
            type="primary"
            size="large"
            class="login-button"
            :loading="loading"
            @click="handleLogin"
          >
            {{ loading ? '登录中...' : '登 录' }}
          </el-button>
        </el-form-item>
      </el-form>

      <div class="login-footer">
        <span>还没有账号？</span>
        <el-link type="primary" :underline="false" @click="$router.push('/auth/register')">
          立即注册
        </el-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { useTenantStore } from '@/stores/tenant';
import { getCaptcha } from '@/api/auth';
import type { FormInstance, FormRules } from 'element-plus';

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();
const tenantStore = useTenantStore();

const formRef = ref<FormInstance>();
const loading = ref(false);
const captchaImage = ref('');
const captchaId = ref('');
const showTenant = ref(true);

// 表单数据
const form = reactive({
  username: '',
  password: '',
  captcha: '',
  captchaId: '',
  tenantId: '',
  remember: false,
});

// 表单验证规则
const rules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度为3-20位', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 32, message: '密码长度为6-32位', trigger: 'blur' },
  ],
  captcha: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { len: 4, message: '验证码为4位', trigger: 'blur' },
  ],
};

// 获取验证码
async function refreshCaptcha(): Promise<void> {
  try {
    const result = await getCaptcha();
    captchaImage.value = result.image;
    captchaId.value = result.id;
    form.captchaId = result.id;
  } catch {
    // 获取失败处理
  }
}

// 登录
async function handleLogin(): Promise<void> {
  if (!formRef.value) return;

  try {
    const valid = await formRef.value.validate();
    if (!valid) return;

    loading.value = true;

    await userStore.login({
      username: form.username,
      password: form.password,
      captcha: form.captcha,
      captchaId: form.captchaId,
      tenantId: form.tenantId ? Number(form.tenantId) : undefined,
    });

    // 跳转到目标页面
    const redirect = route.query.redirect as string;
    router.push(redirect || '/');
  } catch {
    // 刷新验证码
    refreshCaptcha();
  } finally {
    loading.value = false;
  }
}

// 加载可选租户列表
async function loadTenants(): Promise<void> {
  try {
    await tenantStore.loadTenantList();
    // 如果只有一个租户，隐藏租户选择
    showTenant.value = tenantStore.hasMultipleTenants;
    // 如果只有一个租户且用户未选择，自动选中
    if (tenantStore.tenantList.length === 1) {
      form.tenantId = String(tenantStore.tenantList[0].id);
    }
  } catch {
    // 获取失败处理
  }
}

onMounted(() => {
  refreshCaptcha();
  loadTenants();
});
</script>

<style scoped>
.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 400px;
  padding: var(--spacing-xxl);
  background-color: var(--color-bg-base);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-lg);
}

.login-header {
  text-align: center;
  margin-bottom: var(--spacing-xxl);
}

.login-title {
  margin: 0 0 var(--spacing-sm);
  font-size: var(--font-size-xxl);
  font-weight: var(--font-weight-bold);
  color: var(--color-text-primary);
}

.login-subtitle {
  margin: 0;
  font-size: var(--font-size-md);
  color: var(--color-text-secondary);
}

.login-form {
  margin-bottom: var(--spacing-lg);
}

.login-select {
  width: 100%;
}

.captcha-row {
  display: flex;
  gap: var(--spacing-sm);
  width: 100%;
}

.captcha-image {
  height: 40px;
  border-radius: var(--radius-md);
  cursor: pointer;
  border: 1px solid var(--color-border);
}

.login-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: var(--spacing-lg);
}

.login-button {
  width: 100%;
}

.login-footer {
  text-align: center;
  color: var(--color-text-secondary);
}
</style>
