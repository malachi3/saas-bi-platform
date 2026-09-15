/**
 * Main Entry Point
 * 应用入口文件
 */

import { createApp } from 'vue';
import { createPinia } from 'pinia';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';

import App from './App.vue';
import router from './router';

// 创建应用实例
const app = createApp(App);

// 安装 Pinia
app.use(createPinia());

// 安装 Vue Router
app.use(router);

// 安装 Element Plus
app.use(ElementPlus);

// 注册 Element Plus 图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

// 挂载应用
app.mount('#app');
