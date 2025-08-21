<template>
    <div class="answerOptions">
        <el-radio-group v-model="localdata.answer" :disabled="disabled">
        <ul style="padding: 0 15px;">
            <li  v-for="(answerItem, index) in localdata.answerOptions" :key="answerItem.id">
                <el-radio :label="answerItem.id" size="large" >
                    {{ answerItem.optionContent }}
                    <span v-if="answerItem.id ==  localdata.correctAnswer && disabled" style="margin-left: 20px;color: limegreen">（正确答案）</span>
                </el-radio>
            </li>
        </ul>
        </el-radio-group>
    </div>
</template>

<script setup>
import { toRefs,watchEffect} from 'vue'
const props = defineProps(['localdata','disabled'])
const { localdata,disabled } = toRefs(props)
localdata.value.answer = localdata.value.correctAnswer-0

watchEffect(() => {
    if(localdata.value.userAnswer){
        localdata.value.answer = localdata.value.userAnswer-0
    }else if(localdata.value.correctAnswer){
        localdata.value.answer = localdata.value.correctAnswer-0
    }
})


</script>

<style lang='scss' scoped>
.answerOptions{
    .el-radio-group{
        --el-text-color-placeholder: #606266;
    }

    :deep(.el-radio__input.is-disabled.is-checked .el-radio__inner::after){
        background-color: white;
        
    }
    :deep(.el-radio__input.is-disabled.is-checked .el-radio__inner){
        border-color: #409eff;
        background-color:#409eff;
    }
}

</style>