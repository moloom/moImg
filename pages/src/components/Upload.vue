<script setup lang="ts">
import {onBeforeMount, ref} from 'vue';
import axios from 'axios';

const fileInput = ref<HTMLInputElement | null>(null);

const handleFileDrop = (event: DragEvent) => {
  const files = event.dataTransfer?.files;
  if (files?.length) {
    uploadFiles(files);
  }
};

const selectFiles = () => {
  fileInput.value?.click();
};

const handleFileSelect = () => {
  const files = fileInput.value?.files;
  if (files?.length) {
    uploadFiles(files);
  }
};

const uploadFiles = async (files: FileList) => {
  const formData = new FormData();
  Array.from(files).forEach(file => {
    formData.append('files[]', file);
  });

  try {
    const response = await axios.post('/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    console.log('上传成功', response.data);
  } catch (error) {
    console.error('上传失败', error);
  }
};

/*在页面加载时请求token*/
onBeforeMount(() => {
  console.log('upload')
  axios.get('/token').then(res => {
    console.log(res.data)
  })
})
</script>


<template>
  <div class="upload-container">
    <div
        class="upload-box"
        @dragover.prevent
        @drop="handleFileDrop"
        @click="selectFiles"
    >
      <input
          type="file"
          ref="fileInput"
          multiple
          @change="handleFileSelect"
          style="display: none"
      />
      <p>拖拽文件、Ctrl+V 粘贴文件或点击选择文件进行上传</p>
    </div>
  </div>
</template>

<style scoped>
.upload-container {
  display: block;
  justify-content: center;
  align-items: center;
  height: 90vh;
}

.upload-box {
  width: 1000px;
  height: 250px;
  border: 2px dashed #d3a6d1; /* 淡紫色边框 */
  display: flex;
  margin-top: 30px;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  text-align: center;
  background-color: rgba(211, 166, 209, 0.11);
  cursor: pointer;
}

.upload-box p {
  font-size: 16px;
  color: #6b6b6b;
}

.upload-box:hover {
  background-color: #eaeaea;
}
</style>