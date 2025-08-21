<template>
    <div style="margin-top:30px;">
        <div class="top">
            <div class="title">回收站</div>
            <div class="search">
                <el-button type="danger" icon="Delete">清空回收站</el-button>
            </div>
        </div>

        <div class="list">
            <el-table :data="wjList" border stripe>
                <el-table-column label="序号" type="index" width="60" align="center"></el-table-column>
                <el-table-column prop="title" label="问卷名" align="center"></el-table-column>
                <el-table-column prop="fcd" label="发布时间" align="center" width="160"></el-table-column>
                <el-table-column prop="answerTotal" label="答卷数" align="center" width="80"></el-table-column>
                <el-table-column label="清空数据" align="center" width="100">
                    <template v-slot="scope">
                        <el-link v-if="scope.row.answerTotal > 0">清空数据</el-link>
                    </template>
                </el-table-column>
                <el-table-column label="恢复" align="center" width="80">

                    <template v-slot="scope">
                        <el-icon color="green" size="18" @click="restoreSurvey(scope.row.id)">
                            <RefreshLeft />
                        </el-icon>
                    </template>
                </el-table-column>
                <el-table-column label="彻底删除" align="center" width="100">

                    <template v-slot="scope">
                        <el-icon color="red" size="18" @click="deleteSurvey(scope.row.id,scope.row.title)">
                            <CloseBold />
                        </el-icon>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div style="color:red">
            重要提示：被删除的问卷保留3个月后将被彻底删除，无法再恢复运行！
        </div>
    </div>
    <div style="margin-top: 50px;"></div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage,ElMessageBox } from 'element-plus'

import surveyApi from '@/api/surveyApi';
const wjList = ref([]);

const getRecycleList = async() => {
    let res = await surveyApi.getRecycleList()
    wjList.value = res.data;
}
getRecycleList();

const restoreSurvey = async (surveyId) => {
    await surveyApi.restoreSurvey(surveyId)
    ElMessage({
        type: 'success',
        message: '恢复成功',
    })
    getRecycleList();
}

const deleteSurvey = async(surveyId,title) => {
    ElMessageBox.confirm(
        `您确认删除问卷：${title}`,
        '警告',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then( async() => {
            await surveyApi.deleteSurvey(surveyId);

            ElMessage({
                type: 'success',
                message: '问卷删除成功',
            })

            getRecycleList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '取消删除',
            })
        })
}


</script>

<style lang='scss' scoped>
.top {
    display: flex;
    justify-content: space-between;
    align-items: center;

    .title {
        font-size: 20px;
        color: black;
        font-weight: bold;
    }



}

.list {
    margin-bottom: 10px;
    padding: 15px 0;

    .el-icon {
        cursor: pointer;
    }
}
</style>