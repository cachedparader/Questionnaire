<template>
    <div class="exam">
        <div class="main">
            <div class="title">
                您的测试结果
            </div>

            <div style="padding: 0 40px 0;">
                <table class="result">
                    <tr>
                        <td>
                            <div
                                style="height: 100px;display: flex;flex-direction: column;align-items: center;justify-content: center;">
                                <span style="font-size: 40px; color: #ff6a00;line-height: 50px; ">{{scoreResult.score}}</span>
                                <span style="color: #999999;font-weight: bolder;">得分</span>
                            </div>
                        </td>
                        <td>
                            <div
                                style=" height: 100px;display: flex;flex-direction: column;align-items: center;justify-content: center;">
                                <span style=" font-size: 40px; color: #ff6a00;line-height: 50px;">{{scoreResult.correctNum}}</span>
                                <span style="color: #999999;font-weight: bolder;">答对</span>
                            </div>
                        </td>
                    </tr>

                    <tr style="height: 40px; font-size: 16px;background-color: #f5f5f5;text-align: center;">
                        <td>总分{{scoreResult.surveyScore}}</td>
                        <td>共{{scoreResult.questionNumber}}题</td>
                    </tr>
                </table>
            </div>

            <div style="margin-bottom: 50px;text-align: center;">
                <el-button type="primary" plain @click="viewAnwserKey">
                    <span v-if="answerKey">隐藏</span>
                    <span v-else>查看</span>
                    答题解析 <el-icon style="margin-left: 3px;">
                        <ArrowDownBold />
                    </el-icon></el-button>

            </div>

            <div v-if="answerKey">
                <div style="padding: 5px 40px;" v-for="(item, index) in examDetail.examineeList" :key="item.id">
                    <div class="question" style="font-size: 16px; ">
                        <span style="font-weight: bolder">{{ item.content }}：</span>
                         {{ item.userAnswer }} 
                    </div>
                </div>
                <div class="card" v-for="(item, index) in examDetail.questionList" :key="item.id">
                    <div class="question">
                        <span style="margin-right: 5px;">{{ index + 1 }}.</span>
                        <div v-html="item.content"></div>
                        
                    </div>
                    <div class="answerList">
                        <component :is="item.component" :localdata="item" :disabled="true"
                            style="width: 18px;padding: 0; margin-right: 10px; ">
                        </component>
                        <div v-if="item.answerValid == 1" class="answer-desc">
                            <div style="display: flex;align-items: center;">
                                <el-icon color="#01ad56" size="24"><CircleCheckFilled /></el-icon>
                                回答正确
                            </div>
                            <div>+{{item.score}}分</div>
                        </div>
                        <div v-else class="answer-desc" style="color: red;">
                            <div style="display: flex;align-items: center;">
                                <el-icon color="red" size="24"><CircleCloseFilled /></el-icon>
                                回答错误
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="footer">
                <img src="/src/assets/logo1.png" style="background-color: #ff399b;height: 16px;border-radius: 5px;">
                <span style="margin: 0 10px 0 3px;">问卷君</span>
                <span style="color: #bebebe;">青青菜鸟提供技术支持</span>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'

const answerKey = ref(false)
const viewAnwserKey = () => {
    answerKey.value = !answerKey.value;
}

import { useRouter, useRoute } from 'vue-router'
const router = useRouter();
const route = useRoute();
import surveyApi from '@/api/surveyApi';
let scoreId = route.query.scoreId;
const scoreResult = ref({})
const getExamResult = async() => {
    let res = await surveyApi.getExamResult(scoreId);
    scoreResult.value = res.data;
}
getExamResult();


const examDetail = ref({})
const getExamDetail = async() => {
    let res = await surveyApi.getExamDetail(scoreId);
    examDetail.value = res.data;
}
getExamDetail();

</script>

<style lang='scss' scoped>
.exam {
    justify-content: center;
    padding-top: 80px;
    background-image: linear-gradient(-45deg, #d9effc, #f5f5f5, #dfe5fe);
    min-height: 100vh;
    width: 100%;

    .main {
        margin: 0 auto;
        width: 900px;
        background-color: white;

        .title {
            height: 100px;
            padding: 40px 0 30px;
            color: #0095ff;
            font-weight: bolder;
            font-size: 24px;
            text-align: center;
        }

        .result {
            width: 100%;
            margin: 20px auto 50px;
            border-collapse: collapse;

            td {
                border: 1px solid #efefef;
            }
        }

        .card {
            padding: 30px 0 30px;

            .question {
                color: 16px;
                padding: 0 40px 0;
                display: flex;
            }

            .answerList {
                margin: 10px 0;
                color: #595959;
                padding: 0 40px 0;
                .answer-desc{
                    background-color: #f7f7f7;
                    font-size: 15px;
                    color: #01ad56;
                    margin: 8px 0 0 ;
                    height: 30px;
                    line-height: 30px;
                    border-radius: 15px;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    padding: 0 6px 0 3px;

                    .el-icon{
                        margin-right: 5px;
                    }
                }
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
</style>