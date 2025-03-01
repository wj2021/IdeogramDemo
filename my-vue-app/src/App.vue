<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <AiPicGenerator/>
</template>

<script>
import AiPicGenerator from './components/AiPicGenerator.vue'

export default {
  name: 'App',
  components: {
    AiPicGenerator
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
