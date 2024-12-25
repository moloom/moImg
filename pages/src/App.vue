<script setup lang="ts">
import Upload from './components/Upload.vue'
</script>

<template>
  <div class="app-container">
    <Upload/>
  </div>
</template>

<style scoped>
.app-container {
  width: 100%;
  margin: 0 auto;
  background-color: white;
  min-width: 1200px;  /*防止过小屏幕时内容溢出 */
  max-width: 2200px; /* 最大宽度，避免过大屏幕时内容过宽 */
  display: flex;
  justify-content: center;
}
</style>
