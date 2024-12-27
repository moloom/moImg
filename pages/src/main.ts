import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import axios from 'axios'
import {createPinia} from "pinia";

const app = createApp(App)
const pinia = createPinia();
// 设置全局的 axios 默认配置
axios.defaults.baseURL = 'https://img.moloom.cn/api';
//axios.defaults.headers.common['Content-Type'] = 'application/json'; // 根据需要设置请求头

// 全局设置 axios
app.config.globalProperties.$axios = axios;
app.use(pinia);
app.mount('#app');