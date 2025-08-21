<template>
    <el-watermark content="青青菜鸟" :font="font" :gap="[300,400]">
        <div />
    </el-watermark>
</template>

<script setup>
import { ref } from 'vue';

const font = ref({
   fontFamily: '仿宋',fontSize: 24
})
</script>

<style lang='scss' scoped>
div{
    height: 100vh;
    width: 100%;
}
</style>