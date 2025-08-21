<template>
    <div class="exam">
        <div class="main">
            <el-watermark :content="config.content" :font="config.font" :z-index="config.zIndex" :rotate="config.rotate"
                :gap="config.gap">
                <div class="logo"></div>
            </el-watermark>
            <div class="title" v-if="!openStatus">
                问卷不存在！
            </div>
            <el-form :model="examForm" v-else>
                <div class="title">
                    {{ examForm.title }}
                </div>
                <div class="card" v-for="(item, index) in examForm.examineeList" :key="item.id">
                    <div class="question">{{ item.content }}：</div>
                    <div class="answerList">
                        <el-input style="width: 398px;height: 38px;" v-model="item.userAnswer"></el-input>
                    </div>
                </div>
                <div class="card" v-for="(item, index) in examForm.questionList" :key="item.id">
                    <div class="question" style="display: flex;">
                        <span>{{ index + 1 }}.&nbsp;</span>
                        <div v-html="item.content"></div>
                    </div>
                    <span style="margin-left: 55px; color:#0095ff; background-color:#e1fafa;padding: 1px 5px; border-radius: 5px">
                        [ {{ item.questionTypeDesc }} {{ item.score }}分 ]</span>
                    <div class="answerList">
                        <component :is="item.component" :localdata="item"
                            style="width: 18px;padding: 0; margin-right: 10px; ">
                        </component>
                    </div>
                </div>
            </el-form>
            <div style="height: 200px;text-align: center;line-height: 200px;">
                <el-button @click="submitExam" type="primary" size="large" icon="Select"
                    style="width: 200px;height: 44px;font-size: 16px">提
                    交</el-button>
            </div>
            <div class="footer">
                <img src="/src/assets/logo1.png" style="background-color: #ff399b;height: 16px;border-radius: 5px;">
                <span style="margin: 0 10px 0 3px;">问卷君</span>
                <span style="color: #bebebe;">青青菜鸟提供技术支持</span>
            </div>

        </div>
    </div>
    <div class="ttl">
        <el-icon color="red" size="20">
            <Clock />
        </el-icon>
        <span v-if="ttl > 0">{{ remainder }}</span>
        <span v-else>考试结束</span>
    </div>
</template>

<script setup>
import { ref, computed, watchEffect } from 'vue'
import { useRouter, useRoute } from 'vue-router'
const router = useRouter();
const route = useRoute();

const ttl = ref(90 * 60);
let ttlIntervalId = setInterval(() => {
    ttl.value -= 1;
}, 1000);

const openStatus = ref(true)
import surveyApi from '@/api/surveyApi';
const examForm = ref({})
const getSurveyForExam = async () => {
    let res = await surveyApi.getSurveyForExam(route.query.surveyId)
    if(res.data){
        examForm.value = res.data;
        ttl.value = res.data.timeLimit * 60;
    }else{
        openStatus.value = false;
    }
    
}
getSurveyForExam();





watchEffect(() => {
    if (ttl.value <= 0) {
        clearInterval(ttlIntervalId);
        // 提交考试
    }
})

const remainder = computed(() => {
    const hours = leftPad0(Math.floor(ttl.value / 3600));
    const minutes = leftPad0(Math.floor((ttl.value % 3600) / 60));
    const remainingSeconds = leftPad0(ttl.value % 60);

    return hours + ":" + minutes + ":" + remainingSeconds;
})

const leftPad0 = (str) => {
    if (str < 10) {
        return "0" + str;
    }
    return str;
}

import { ElMessage } from 'element-plus'
const submitExam = async () => {
    let valid = true;
    let examineeAnswers = examForm.value.examineeList.map(item => {
        if(!item.userAnswer){
            valid = false;
        }
        return {
            surveyId: item.surveyId,
            questionId: item.id,
            userAnswer: item.userAnswer
        }
    });
    let questionAnswers = examForm.value.questionList.map(item => {
        if(item.questionType ==2){
            if(item.answerArr.length > 0){
                item.answerArr = item.answerArr.sort((a,b) => a-b)
            }else{
                valid = false;
            }
        }
        if(item.questionType ==1){
            if(!item.answer){
                valid = false;
            }
        }
        return {
            surveyId: item.surveyId,
            questionId: item.id,
            userAnswer: item.questionType==2 ? item.answerArr.join(",") : item.answer
        }
    });

    if(!valid){
        ElMessage({
            type: 'warning',
            message: '请完成所有试题再提交！',
        })
        return;
    }

    let examDuration = (examForm.value.timeLimit * 60 ) - ttl.value;
    let res = await surveyApi.submitExam(examineeAnswers.concat(questionAnswers), examForm.value.id, examDuration)

    router.replace({
        path: "/exam/result",
        query: {
            scoreId:res.data
        }
    })
}





const config = ref({
    content: '问卷君',
    font: {
        fontSize: 16,
        fontWeight: 100,
        color: 'rgba(0, 149, 255, 0.5)',
    },
    rotate: -22,
    gap: [100, 80]
})
</script>

<style lang='scss' scoped>
.exam {

    justify-content: center;
    padding-top: 80px;
    background-image: linear-gradient(-45deg, #d9effc, #f5f5f5, #dfe5fe);
    min-height: 100vh;

    .main {
        position: relative;
        margin: 0 auto;
        width: 900px;
        background-color: white;

        .logo {
            height: 90px;
            background-color: #bee4ff;
        }

        .title {
            height: 100px;
            padding: 40px 0 30px;
            color: #0095ff;
            font-weight: bolder;
            font-size: 24px;
            text-align: center;
        }

        .card {
            padding: 30px 0 30px;

            .question {
                color: 16px;
                padding: 0 40px 0;
            }

            .answerList {
                margin: 10px 0;
                color: #595959;
                padding: 0 40px 0;

            }

        }

        .footer {
            font-size: 14px;
            color: #8c8c8c;
            height: 50px;
            line-height: 50px;
            background-color: #f7f7f7;
            display: flex;
            justify-content: center;
            align-items: center;
        }
    }
}

.ttl {
    border-radius: 8px;
    background-color: #eebe77;
    font-size: 18px;
    color: white;
    padding: 10px 20px;
    position: fixed;
    right: 20px;
    top: 40%;
    display: flex;
    align-items: center;

    span {
        margin-left: 6px;
    }
}
</style>