<template>
  <div class="search-container">
    <!-- 搜索框和按钮 -->
    <div class="search-box">
      <input type="text" v-model="searchQuery" placeholder="请描述您的想法" @keyup.enter="searchImages" />
      <button @click="searchImages">开始生成</button>
    </div>

    <!-- 加载状态提示 -->
    <div v-if="loading" class="loading">加载中...</div>

    <!-- 错误提示 -->
    <div v-if="error" class="error">{{ error }}</div>

    <!-- 图片展示区域 使用原链接展示图片 -->
    <div class="image-grid">
      <div v-for="(image, index) in images" :key="index" class="image-item">
        <img :src="image" alt="" class="image" />
        <div class="image-link">
          <a :href="image" target="_blank" rel="noopener noreferrer" class="link-text">
            {{ formatImageUrl(image) }}
          </a>
        </div>
      </div>
    </div>
    
    <!-- 图片展示区域 使用已经下载到服务器的图片展示 -->
    <div class="image-grid">
      <div v-for="(image, index) in images" :key="index" class="image-item">
        <img :src="`${server}/api/image?url=/data/images/6b4629bd-a9df-4f53-a8cf-57709855c315`" alt="" class="image" />
        <div class="image-link">
          <a :href="image" target="_blank" rel="noopener noreferrer" class="link-text">
            {{ formatImageUrl(image) }}
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import axios from 'axios'

export default {
  name: 'AiPicGenerator',
  setup() {
    const searchQuery = ref('')
    const images = ref([])
    const loading = ref(false)
    const error = ref(null)

    const server = "http://127.0.0.1:8999";

    const searchImages = async () => {
      if (!searchQuery.value.trim()) return

      loading.value = true
      error.value = null

      try {
        // 替换为你的实际API地址
        const response = await axios.post(`${server}/api/ideogram/generate`, {
          prompts: [searchQuery.value]
        })

        console.log(response)

        // 假设返回数据中的results数组包含图片URL
        if (response && response.data) {
          images.value = response.data.picUrls[searchQuery.value]
        }
      } catch (err) {
        error.value = '获取图片失败，请稍后重试'
        console.error('API请求失败:', err)
      } finally {
        loading.value = false
      }
    }

    const formatImageUrl = (url) => {
      try {
        const parsed = new URL(url)
        return `${parsed.hostname}${parsed.pathname.substring(0, 30)}...`
      } catch {
        return url.substring(0, 40) + '...' // 简单截断
      }
    }

    // 在setup中添加
    const copyUrl = async (url) => {
      try {
        await navigator.clipboard.writeText(url)
        alert('链接已复制')
      } catch (err) {
        console.error('复制失败:', err)
      }
    }

    return {
      searchQuery,
      images,
      loading,
      error,
      searchImages,
      formatImageUrl,
      copyUrl,
      server
    }
  }
}
</script>

<style scoped>
.search-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.search-box {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

input {
  flex: 1;
  padding: 10px;
  border: 2px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}

.image-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 15px;
}

.image-item {
  position: relative;
  padding-bottom: 100%;
  overflow: hidden;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.image {
  position: absolute;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.loading,
.error {
  text-align: center;
  padding: 20px;
  font-size: 18px;
}

.error {
  color: #dc3545;
}

.image-item {
  position: relative;
  padding-bottom: calc(100% + 30px);
  /* 给链接留出空间 */
  overflow: visible;
  /* 允许链接显示 */
}

.image-link {
  position: absolute;
  bottom: -25px;
  /* 调整到图片容器下方 */
  left: 0;
  right: 0;
  text-align: center;
  padding: 5px 0;
  transition: opacity 0.3s;
}

</style>