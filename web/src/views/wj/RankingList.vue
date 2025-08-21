<template>
    <div class="exam">
        <div class="main">
            <el-watermark :content="config.content" :font="config.font" :z-index="config.zIndex" :rotate="config.rotate"
                :gap="config.gap">
                <div class="logo"></div>
            </el-watermark>
            <div class="title">
                {{ ranking.title }} 排行榜
            </div>

            <div style="padding: 10px 40px 40px;">
                <el-table :data="ranking.rankingList" stripe border style="width: 100%;">
                    <el-table-column  label="排行" width="90" align="center">
                        <template v-slot="scope">
                            {{ scope.$index+1 }}
                        </template>
                        </el-table-column>
                    <el-table-column prop="userName" label="姓名"   align="left" />
                    <el-table-column prop="examDuration" :formatter="formatDuration" label="用时" width="150"align="center"/>
                    <el-table-column prop="score" label="得分" width="130" align="center"/>
                    <el-table-column  label="删除" align="center" width="100">
                        <template v-slot="scope">
                            <el-icon><Delete /></el-icon>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

            <div class="footer" >
                <img src="/src/assets/logo1.png" style="background-color: #ff399b;height: 16px;border-radius: 5px;">
                <span style="margin: 0 10px 0 3px;">问卷君</span>
                <span style="color: #bebebe;">青青菜鸟提供技术支持</span>
            </div>
        </div>
    </div>

</template>

<script setup>
import { ref, computed, watchEffect } from 'vue'
import { useRouter, useRoute } from 'vue-router'
const router = useRouter();
const route = useRoute();

const ranking = ref({})
let surveyId =  route.query.surveyId;
import surveyApi from '@/api/surveyApi';
const getExamRanking = async() => {
    let res = await surveyApi.getExamRanking(surveyId);
    ranking.value = res.data
}
getExamRanking();

const formatDuration = (row,col) => {
    let duration = 0;
    if(row.examDuration){
        duration = row.examDuration;
    }
    return duration + " 秒"
}


const config = ref({
    content: '排行榜',
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
        box-shadow: 1px 1px 2px lightgrey;
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
</style>