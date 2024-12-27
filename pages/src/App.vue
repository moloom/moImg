<script setup lang="ts">
import Upload from './components/Upload.vue'

import {ref, onBeforeMount, onMounted} from 'vue';
import axios from 'axios';
import Cookies from 'js-cookie';  // 使用命名导入

/*在页面加载时请求token*/
onBeforeMount(() => {
  console.log('init')
  if (Cookies.get("token") === undefined) {
    // 请求 token
    axios.post('/token/reg').then(res => {
      if (res.data.status === 200) {
        // 成功获取 token
        const token = res.data.data.token;
        console.log('获取的 token:', token);

        // 将 token 存储到 cookie 中，有效期为 3 天
        Cookies.set('token', token, {expires: 3, path: ''}); // expires 设置有效期，单位为天

        // 这里可以进行其他操作，例如跳转页面或更新状态
      } else {
        // 请求失败
        console.error('请求失败:', res.data.msg);
      }
    }).catch(error => {
      // 错误处理
      console.error('请求发生错误:', error);
    });
  }
  //TODO 把token存到 pinia中

  console.log('token:', Cookies.get("token"))
})

// 存储当前主题
const isDarkMode = ref(false);
// 判断系统是否启用了夜间模式
const checkDarkMode = () => {
  // 检查系统偏好
  const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
  isDarkMode.value = mediaQuery.matches;
};

// 切换到夜间主题
const applyDarkMode = () => {
  if (isDarkMode.value) {
    document.body.style.backgroundColor = 'rgb(1, 4, 9)';
    document.body.style.color = '#d1d1d1';
  } else {
    document.body.style.backgroundColor = '#FFF';
    document.body.style.color = '#000';
  }
};

// 监听系统主题变化
const setupDarkModeListener = () => {
  const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
  mediaQuery.addEventListener('change', () => {
    checkDarkMode();
    applyDarkMode();
  });
};

onMounted(() => {
  // 在页面加载时检查并应用夜间模式
  checkDarkMode();
  applyDarkMode();
  setupDarkModeListener();
  // 动态修改 body 样式
});

</script>

<template>
  <div class="app-container">
    <Upload/>
  </div>
</template>

<style scoped>
body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
}

.app-container {
  width: 100%;
  margin: 0 auto;
  background-color: white;
  min-width: 1200px; /*防止过小屏幕时内容溢出 */
  max-width: 2200px; /* 最大宽度，避免过大屏幕时内容过宽 */
  display: flex;
  justify-content: center;
}

/* 夜间模式样式 */
@media (prefers-color-scheme: dark) {
  .app-container {
    background-color: rgb(1, 4, 9);
    color: #ecf0f1;
  }
}
</style>
