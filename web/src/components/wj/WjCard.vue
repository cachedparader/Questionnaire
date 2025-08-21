<template>
    <div>
        <div class="card">
            <div class="card-top">
                <div class="title">{{ localdata.title }}</div>
                <div class="info">
                    <div class="info-item">ID: {{ localdata.id }}</div>
                    <div class="info-item " :class="{ highlight: localdata.status == 1 }">
                        <span :class="{ lightgray: localdata.status == 0, highlight: localdata.status == 1 }">●</span>
                        {{ statusDesc }}
                    </div>
                    <div class="info-item">答卷
                        <span :class="{ highlight: localdata.answerTotal > 0 }">{{ localdata.answerTotal }}</span>
                    </div>
                    <div class="info-item">{{ localdata.fcd }}</div>
                </div>
            </div>
            <div class="card-bottom">
                <div class="opr">
                    <el-dropdown>
                        <div class="opr-item">
                            <el-icon color="#4b97ce" size="20">
                                <Document />
                            </el-icon>
                            <span>考试设计</span>
                            <el-icon color="lightgray">
                                <CaretBottom />
                            </el-icon>
                        </div>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item @click="editWj">编辑考试</el-dropdown-item>
                                <el-dropdown-item>考试设置</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                    <el-dropdown>
                        <div class="opr-item">
                            <el-icon color="#f59917" size="20">
                                <Promotion />
                            </el-icon>
                            <span>发送考试</span>
                            <el-icon color="lightgray">
                                <CaretBottom />
                            </el-icon>
                        </div>

                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item @click="getLink">链接&二维码</el-dropdown-item>
                                <el-dropdown-item>微信发送</el-dropdown-item>
                                <el-dropdown-item>邮件发送</el-dropdown-item>
                                <el-dropdown-item>短信发送</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>

                    </el-dropdown>
                    <el-dropdown>
                        <div class="opr-item">
                            <el-icon color="#627ce5" size="20">
                                <Histogram />
                            </el-icon>
                            <span>成绩&数据</span>
                            <el-icon color="lightgray">
                                <CaretBottom />
                            </el-icon>
                        </div>

                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item>下载成绩</el-dropdown-item>
                                <el-dropdown-item @click="ranking">考试排名</el-dropdown-item>
                                <el-dropdown-item>题目正确率</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </div>
                <div class="opr">
                    <div class="opr-item-2" v-if="localdata.status == 1" @click="publishWj">
                        <el-icon color="green" size="24">
                            <VideoPlay />
                        </el-icon>
                        <span>发布</span>
                    </div>
                    <div class="opr-item-2" v-if="localdata.status == 2" @click="stopWj">
                        <el-icon color="red" size="24">
                            <VideoPause />
                        </el-icon>
                        <span>停止</span>
                    </div>
                    <div class="opr-item-2" @click="copyWj">
                        <el-icon color="#bfbfbf" size="18">
                            <DocumentCopy />
                        </el-icon>
                        <span>复制</span>
                    </div>
                    <div class="opr-item-2" @click="deleteWj">
                        <el-icon color="#bfbfbf" size="18">
                            <Delete />
                        </el-icon>
                        <span>删除</span>
                    </div>
                    <div class="opr-item-2">
                        <el-icon color="#bfbfbf" size="18">
                            <FolderOpened />
                        </el-icon>
                        <span>文件夹</span>
                    </div>
                    <div class="opr-item-2" @click="updateSurveyStar" >
                        <el-icon color="#ffaa77" size="26"   v-if="localdata.star==1">
                            <StarFilled />
                        </el-icon>
                        <el-icon color="#bfbfbf" size="26"   v-else>
                            <StarFilled />
                        </el-icon>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <el-dialog v-model="messageDialogVisible" title="" width="500" 
        style="border-radius: 10px;padding: 30px;">
        <h2 style="color: #67C23A;">{{ messageDialogTitle }}</h2>
        <div style="margin: 20px 0;">访问地址：<el-link type="primary" target="_blank"
                :href="examLink">{{examLink}}</el-link> 
            
                <el-button type="default" size="small" style="margin-left: 10px;" @click="copyLink">复制链接</el-button>
        </div>
        

        <template #footer>
            <div class="dialog-footer">
                <el-button @click="messageDialogVisible = false">关闭</el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref, toRefs } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter, useRoute } from 'vue-router'
const router = useRouter();
const route = useRoute();

const messageDialogVisible = ref(false)

const props = defineProps(['localdata','statusDesc'])
const { localdata,statusDesc } = toRefs(props)


import surveyApi from '@/api/surveyApi';
const updateSurveyStar = async () => {
    let star = 1 - localdata.value.star;
    await surveyApi.updateSurveyStar(localdata.value.id, star)
    localdata.value.star = star;
    ElMessage({
            message: '操作成功',
            type: 'success',
        })
}


const editWj = () => {
    if(localdata.value.status == 2){
        ElMessage({
            message: '请先停止问卷',
            type: 'warning',
        })
        return;
    }
    router.push({
        path: '/wj/design',
        query: {
            surveyId: localdata.value.id
        }
    })
}

const copyWj = async() => {
    let res = await surveyApi.copySurvey(localdata.value.id);
    ElMessage({
            message: '复制成功',
            type: 'success',
        })
    setTimeout(() => {
        router.push({
            path: '/wj/design',
            query: {
                surveyId: res.data
            }
        })
    }, 1000);
}






//const examLink = ref("http://localhost:8888/exam");
const examLink = ref(window.location.origin + "/exam?surveyId=" + localdata.value.id);
const copyLink = async () => {
    await navigator.clipboard.writeText(examLink.value);
    ElMessage({
        message: '已复制到剪贴板',
        type: 'success',
    })
}

const emit = defineEmits(['refreshList'])

const deleteWj = () => {
    ElMessageBox.confirm(
        `确认删除ID: ${localdata.value.id} 的问卷？`,
        '警告',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then( async() => {
            await surveyApi.deleteSurveyToRecycle(localdata.value.id);

            ElMessage({
                type: 'success',
                message: '问卷删除成功',
            })

            emit('refreshList')
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '取消删除',
            })
        })
}

const messageDialogTitle = ref("");
const publishWj = async () => {
    let res = await surveyApi.publishSurvey(localdata.value.id)
    localdata.value.status = res.data;
    messageDialogVisible.value = true;
    messageDialogTitle.value = "问卷发布成功啦！"
}

const stopWj = async () => {
    let res = await surveyApi.stopSurvey(localdata.value.id)
    localdata.value.status = res.data;
    ElMessage({
        type: 'success',
        message: '问卷已停止',
    })
}


const getLink = () => {
    messageDialogVisible.value = true;
    messageDialogTitle.value = "问卷发布地址"
}

const ranking = () => {
    let {href} = router.resolve({
        path: "/exam/ranking",
        query:{
            surveyId: localdata.value.id
        }
    })
    window.open(href, "_blank")
}




</script>

<style lang='scss' scoped>
.card {
    margin: 20px 2px 0;
    background-color: white;
    // height: 123px;
    box-shadow: 0 0 3px lightgray;
    padding: 13px 24px;

    .card-top {
        display: flex;
        justify-content: space-between;
        align-items: center;
        color: #1f1f1f;
        border-bottom: 1px solid #f5f5f5;
        padding-bottom: 8px;

        .title {
            font-size: 16px;
            font-weight: 400;
            cursor: pointer;
        }

        .info {
            display: flex;
            align-items: center;
            color: #262626;
            font-size: 12px;

            .info-item {
                margin-left: 15px;
            }
        }
    }

    .card-bottom {
        display: flex;
        align-items: center;
        justify-content: space-between;

        .opr {
            display: flex;
            align-items: center;
            color: #7a7a7a;
            margin: 20px 0;
            font-size: 14px;

            .opr-item {
                display: flex;
                align-items: center;
                margin-right: 20px;
                cursor: pointer;
                outline: none;
                border: none;

                span {
                    margin: 0 3px;
                }
            }



            .opr-item-2 {
                font-size: 12px;
                display: flex;
                align-items: center;
                margin-left: 20px;
                cursor: pointer;

                span {
                    margin: 0 3px;
                }
            }


        }
    }
}

.highlight {
    color: #0095ff;
}

.lightgray {
    color: lightgray;
}
</style>