<template>
    <div> <quill-editor theme="snow" :options="{ modules: { toolbar: false } }" v-model:content="localdata.content"
            contentType="html" placeholder="请完善试题描述" @blur="updateSurveyQuestion">
        </quill-editor>
        <div>
            <el-checkbox-group v-model="localdata.correctAnswer" @change="updateSurveyQuestion">
                <ul>
                    <li v-for="(answerItem, index) in localdata.answerOptions" :key="index">
                        <div class="left">
                            <div><el-checkbox  :label="answerItem.id" size="large">&nbsp;</el-checkbox ></div>
                            <el-input type="textarea" 
                            @blur="updateSurveyQuestionOption(index)" placeholder="请完善该选项内容"
                             :rows="1" autosize v-model="answerItem.optionContent" class="input"></el-input>
                        </div>

                        <div class="right">
                            <div class="button">
                                <el-button type="primary" icon="Plus" circle size="small" @click="addItem(index)" ></el-button>
                                <el-button type="danger" icon="Minus" circle size="small" @click="deleteItem(index)"></el-button>
                            </div>
                                <el-checkbox  :label="answerItem.id" style="margin-left: 20px;"   border>正确答案</el-checkbox >
                        </div>
                    </li>
                </ul>
            </el-checkbox-group>
        </div>
    </div>
</template>

<script setup>
import { ref,watchEffect,onMounted } from 'vue'

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'

import { toRefs } from 'vue'
const props = defineProps(['localdata'])
const { localdata } = toRefs(props)

if(localdata.value.correctAnswer){
    console.log("------------> ",localdata.value);
    
    localdata.value.correctAnswer = localdata.value.correctAnswer.split(",").map(item => {
        return item - 0
    });
}else{
    localdata.value.correctAnswer = []
}



import { ElMessage,ElMessageBox  } from 'element-plus'
import surveyApi from '@/api/surveyApi';
const updateSurveyQuestion = async () => {
    localdata.value.content = localdata.value.content.replace(/<img/g, "<img style='max-width:100%;height:auto'");
    let res = await surveyApi.updateSurveyQuestion(localdata.value)
    ElMessage({
        message: '更新成功',
        type: 'success',
    })
    if(localdata.value.correctAnswer){
        localdata.value.correctAnswer = localdata.value.correctAnswer.split(",").map(item => {
            return item - 0
        });
    }else{
        localdata.value.correctAnswer = []
    }
}

const updateSurveyQuestionOption = async (index) => {
    let option = localdata.value.answerOptions[index];
    await surveyApi.updateSurveyQuestionOption(option);
    ElMessage({
        message: '更新成功',
        type: 'success',
    })
}


const addItem = async (index) => {
    console.log('addItem...', index);
    let option = localdata.value.answerOptions[index];
    let newOption = {
        questionId: option.questionId,
        surveyId: option.surveyId,
        orderNum: option.orderNum
    }
    await surveyApi.addSurveyQuestionOption(newOption)
    let res = await surveyApi.getSurveyQuestionOptionList(option.questionId)
    localdata.value.answerOptions = res.data;
}

const deleteItem = (index) => {
    let option = localdata.value.answerOptions[index];
    ElMessageBox.confirm(
        '您确认删除该选项？',
        '删除确认',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            await surveyApi.deleteSurveyQuestionOption(option.id)
            let res = await surveyApi.getSurveyQuestionOptionList(option.questionId)
            localdata.value.answerOptions = res.data;
            ElMessage({
                message: '删除成功',
                type: 'success',
            })
        })
        .catch(() => {
           
        })
}




const updateAnswerOptionsIndex = () => {
    //因插入或删除元素，整体重新更新orderNum属性值
    localdata.value.answerOptions = localdata.value.answerOptions.map((item,index) => {
        item.orderNum = index;
        return item;
    })
}





</script>

<style lang='scss' scoped>
:deep(.quill-editor) {
    min-height: 100px;
}

.el-checkbox-group {
    width: 100%;

    ul {
        padding: 15px 0;
        width: 100%;

        li {

            display: flex;
            align-items: center;
            justify-content: space-between;



            .left {
                display: flex;
                align-items: center;

                .input {
                    margin-left: -10px;
                    width: 600px;
                    border: none;
                    --el-input-border-color: white;
                }

                .input:hover {
                    --el-input-border-color: #e8e8e8;
                }
            }

            .right {

                height: 40px;
                line-height: 40px;
                display: flex;
                align-items: center;

                .button{
                    display: none;
                }
                
            }

        }

        li:hover .right>.button{
            display: block;
        }

    }

}</style>