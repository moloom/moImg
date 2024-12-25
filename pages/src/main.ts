import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import axios from 'axios'

const app = createApp(App)

app.mount('#app');

// 全局设置 axios
app.config.globalProperties.$axios = axios;