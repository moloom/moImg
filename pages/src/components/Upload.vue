<script setup lang="ts">
import {ref} from 'vue';
import axios from 'axios';
import Cookies from 'js-cookie'; // 导入 js-cookie 库

const fileInput = ref<HTMLInputElement | null>(null);
const uploadResults = ref<Array<{ status: string, msg: string, fileName: string, httpsURL: string }>>([]);

const handleFileDrop = (event: DragEvent) => {
  event.preventDefault();
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
    formData.append('file', file);
  });
  try {
    const response = await axios.post('/upload/' + Cookies.get("token"), formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    console.log('上传成功', response.data);

    console.log(response.data.data?.length);
    if (response.data.data?.length) {
      const resultList = response.data.data;
      console.log('上传成功2', resultList);
      if (resultList.length > 1) {
        setTimeout(() => {
          resultList.forEach((result: { status: string, msg: string, file_name: string, https_URL: string }) => {
            uploadResults.value.push({
              status: result.status,
              msg: result.msg,
              fileName: result.file_name,
              httpsURL: result.https_URL
            });
          })
        }, 500);
      } else {
        resultList.forEach((result: { status: string, msg: string, file_name: string, https_URL: string }) => {
          uploadResults.value.push({
            status: result.status,
            msg: result.msg,
            fileName: result.file_name,
            httpsURL: result.https_URL
          });
        })
      }
    }
  } catch (error: any) {
    console.log(error)
    if (error.response?.data) {
      const errResponse = error.response.data as ErrorResponseType;
      console.error('上传失败:', errResponse);
      alert(errResponse.msg);
    }
  }
};
// 模拟文件上传，假设返回结果
/*const uploadFiles = (files: FileList) => {
  // 这里模拟上传返回的数据
  setTimeout(() => {
    const results = Array.from(files).map((file) => ({
      status: '200',
      msg: 'OK',
      fileName: file.name,
      httpsURL: `https://img.moloom.cn/${file.name}`,
    }));

    // 模拟逐行延时添加
    results.forEach((result, index) => {
      setTimeout(() => {
        uploadResults.value.push(result);  // 将每个文件的结果逐行添加
      }, index * 1000);  // 每个文件延迟 1s（可以根据需要调整）
    });
  }, 2000);  // 模拟5秒后上传完成
};*/

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

    <!-- 上传结果表格，只有在上传成功后才显示 -->
    <div v-if="uploadResults.length > 0" class="upload-results">
      <table>
        <thead>
        <tr>
          <th>status</th>
          <th>message</th>
          <th>file name</th>
          <th>https URL</th>
        </tr>
        </thead>
        <tbody>
        <!-- 逐行展示上传结果，使用 v-for 循环 -->
        <tr v-for="(result, index) in uploadResults" :key="index" class="fade-item">
          <td>{{ result.status }}</td>
          <td>{{ result.msg }}</td>
          <td>{{ result.fileName }}</td>
          <td><a :href="result.httpsURL" target="_blank">{{ result.httpsURL }}</a></td>
        </tr>
        </tbody>
      </table>
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
  text-align: center;
  font-size: 16px;
  color: #6b6b6b;
  border-radius: 10px;
  border: 2px dashed #d3a6d1; /* 淡紫色边框 */
  display: flex;
  margin-top: 30px;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  background-color: rgba(211, 166, 209, 0.11);
  cursor: pointer;
  opacity: 0; /* 初始透明度为 0 */
  transform: translateY(100%); /* 初始位置在屏幕下方 */
  animation: slideUp 0.6s ease-in-out forwards; /* 动画效果 */
}

.upload-box:hover {
  background-color: #eaeaea;
}

/* 表格样式 */
.upload-results {
  margin-top: 20px;
  width: 100%;
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f4f4f4;
}

a {
  color: #6b6b6b;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

.fade-item {
  opacity: 0;
  animation: fadeIn 0.5s forwards;
}

@keyframes fadeIn {
  to {
    opacity: 1;
  }
}

/* 定义动画：从下往上 */
@keyframes slideUp {
  0% {
    transform: translateY(100%); /* 初始位置在屏幕下方 */
    opacity: 0; /* 初始透明度为 0 */
  }
  100% {
    transform: translateY(0); /* 最终位置恢复原始位置 */
    opacity: 1; /* 最终透明度为 1 */
  }
}

@media (prefers-color-scheme: dark) {
  /* 夜间模式下的其他元素样式 */
  .upload-box {
    background-color: rgb(13, 17, 23);
    border: 2px dashed #9b59b6; /* 紫色边框 */
    color: #c8c6c6;
  }

  .upload-box:hover {
    background-color: rgba(26, 36, 48, 0.8);
  }
}
</style>