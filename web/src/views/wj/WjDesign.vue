<template>
    <div class="back" title="返回问卷列表">

        <el-popconfirm @confirm="goback" width="320" confirm-button-text="确认返回" cancel-button-text="取消"
            icon-color="#E6A23C" title="注意哦，系统可能不会保存您所做的更改！" icon="Warning">
            <template #reference>
                <el-button circle>
                    <el-icon size="21" color="white">
                        <ArrowLeftBold />
                    </el-icon>
                </el-button>
            </template>
        </el-popconfirm>
    </div>
    <div class="wj-design">
        <div class="header">
            <div class="nav">
                <div class="left">
                    <div class="item">
                        试卷总分：{{ totalScore }}分
                        <el-divider direction="vertical" />
                        题目数：{{ questionList.length }}
                    </div>
                    <div class="item flex pointer">
                        <el-icon>
                            <Setting />
                        </el-icon>
                        <span>题库随机设置</span>
                    </div>
                    <div class="item flex pointer">
                        <el-icon>
                            <Setting />
                        </el-icon>
                        <span>考试常用设置</span>
                    </div>
                    <div class="item pointer">
                        <el-checkbox label="隐藏系统题号" size="large" />
                    </div>
                </div>
                <div class="right">
                    <div class="btn btn1">
                        <el-icon size="18">
                            <View />
                        </el-icon>
                        <span>预览</span>
                    </div>
                    <div class="btn btn2" @click="saveWj">
                        <el-icon size="18">
                            <Select />
                        </el-icon>
                        <span>完成编辑</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="body">
            <div class="sidebar">
                <div class="question-type">
                    <div @click="chooseQuestionType(index)" :class="{ activeItem: activeQuestionIndex == index }"
                        class="item" v-for="(item, index) in questiontypeList">{{ item.name }}</div>
                </div>
                <div class="question-info">
                    <div class="title" @click="toggleStuInfoVisible">
                        <component :is="stuInfoIcon" style="width: 14px; height: 14px;"></component>
                        <span>考生信息</span>
                    </div>
                    <div class="list" v-if="stuInfoVisible">
                        <div class="item" @click="addExamineeInfo(item.label)" v-for="(item, index) in stuInfo"
                            :key="item.id">
                            <component :is="item.icon" class="icon">
                            </component>
                            <span>{{ item.label }}</span>
                        </div>
                    </div>
                </div>
                <div class="question-info">
                    <div class="title" @click="toggleQuestionInfoVisible">
                        <component :is="questionInfoIcon" style="width: 14px; height: 14px;"></component>
                        <span>考试题型</span>
                    </div>
                    <div class="list" v-if="questionInfoVisible">
                        <div @click="openQuestionDrawer(item.questionEditorComponent, item.label, item.questionType, item.questionTypeDesc, item.component)"
                            class="item" v-for="(item, index) in questionInfo" :key="item.id">
                            <component :is="item.icon" class="icon">
                            </component>
                            <span>{{ item.label }}</span>
                        </div>
                    </div>

                </div>
            </div>

            <el-scrollbar class="content" :style="{ height: 'calc(100vh - 50px)' }">
                <div class="padding40">
                    <el-input class="title" v-model="wjForm.title" placeholder="请输入问卷标题" @blur="updateSurvey" />
                </div>
                <div class="padding40">
                    <el-input class="desc" v-model="wjForm.description" type="textarea" :rows="1" autosize
                        placeholder="添加问卷说明" @blur="updateSurvey"></el-input>
                </div>

                <div class="padding40" style="margin-top: 10px;">
                    <span style="margin-left: 10px;">时长：</span>
                    <el-input type="number" v-model="wjForm.timeLimit" @blur="updateSurvey" style="width:70px" />
                    分钟
                </div>

                <div v-if="examineeList.length == 0"><el-empty description="您还没有考生信息哦" /></div>
                <div style="border-bottom: 1px solid #e8e8e8;margin-top: 20px;"></div>
                <div class="card" v-for="(item, index) in examineeList" :key="item.id">
                    <div class="question">{{ item.content }}：</div>
                    <div class="answerList">
                        <el-input readonly style="width: 398px;height: 38px;"></el-input>
                    </div>
                    <div class="card-opr">
                        <div><el-link type="primary">在此后插入新题</el-link></div>
                        <div>
                            <el-button size="small" type="danger" icon="Delete"
                                @click="deleteExaminee(item.surveyId, item.id, item.questionType, item.orderNum, examineeList.length - 1)">删除</el-button>
                            <el-button size="small" icon="Top"
                                @click="updateExamineeOrder(item.surveyId, item.id, item.questionType, item.orderNum, item.orderNum - 1)">上移</el-button>
                            <el-button size="small" icon="Bottom"
                                @click="updateExamineeOrder(item.surveyId, item.id, item.questionType, item.orderNum, item.orderNum + 1)">下移</el-button>
                            <el-button size="small" icon="Upload"
                                @click="updateExamineeOrder(item.surveyId, item.id, item.questionType, item.orderNum, 0)">最前</el-button>
                            <el-button size="small" icon="Download"
                                @click="updateExamineeOrder(item.surveyId, item.id, item.questionType, item.orderNum, examineeList.length - 1)">最后</el-button>
                        </div>
                    </div>
                </div>
                <div class="card" v-for="(item, index) in questionList" :key="item.id">
                    <div class="question">
                        <div style="display: flex">
                            <span>{{ index + 1 }}.&nbsp;</span>
                            <div v-html="item.content"></div>
                        </div>
                        【{{ item.questionTypeDesc }} &nbsp; {{ item.score }}分】
                    </div>
                    <div class="answerList">
                        <component :is="item.component" :localdata="item" :disabled="true"
                            style="width: 18px;padding: 0; margin-right: 10px; ">
                        </component>
                    </div>
                    <div class="card-opr">
                        <div><el-link type="primary">在此后插入新题</el-link></div>
                        <div>
                            <el-button size="small"
                                @click="getQuestionById(item.id, item.questionEditorComponent, item.questionTypeDesc)"
                                type="primary" icon="Edit">编辑</el-button>
                            <el-button size="small"
                                @click="copyQuestionById(item.id, item.questionEditorComponent, item.questionTypeDesc)"
                                type="primary" plain icon="DocumentCopy">复制</el-button>
                            <el-button
                                @click="deleteQuestion(item.surveyId, item.id, item.questionType, item.orderNum, questionList.length - 1)"
                                size="small" type="danger" icon="Delete">删除</el-button>
                            <el-button
                                @click="updateQuestionOrder(item.surveyId, item.id, item.questionType, item.orderNum, item.orderNum - 1)"
                                size="small" icon="Top">上移</el-button>
                            <el-button
                                @click="updateQuestionOrder(item.surveyId, item.id, item.questionType, item.orderNum, item.orderNum + 1)"
                                size="small" icon="Bottom">下移</el-button>
                            <el-button
                                @click="updateQuestionOrder(item.surveyId, item.id, item.questionType, item.orderNum, 0)"
                                size="small" icon="Upload">最前</el-button>
                            <el-button
                                @click="updateQuestionOrder(item.surveyId, item.id, item.questionType, item.orderNum, questionList.length - 1)"
                                size="small" icon="Download">最后</el-button>
                        </div>
                    </div>
                </div>
                <div v-if="questionList.length == 0"><el-empty description="您还没有添加试题哦" /></div>
                <div style="line-height: 100px;text-align: center;">
                    <el-button size="large" icon="view">预览一下</el-button>
                    <el-button @click="saveWj" size="large" icon="Select" type="primary">完成编辑</el-button>
                </div>

            </el-scrollbar>


        </div>
    </div>


    <el-drawer v-model="editorDrawer.visible" :show-close="false" direction="rtl" :close-on-click-modal="false"
        :size="900">
        <template #header>
            <div style="display: flex; align-items: center">
                <h4 style="font-size: 18px; font-weight: bolder;">{{ editorDrawer.title }}</h4>
                <el-select v-model="questionForm.score" placeholder="分值" size="large"
                    style="width: 80px;margin-left: 10px;">
                    <el-option v-for="item in scoreOptions" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
            </div>
        </template>
        <component :is="editorDrawer.questionEditorType" :localdata="questionForm"
            v-if="editorDrawer.questionEditorType && editorDrawer.visible"></component>
        <div v-else>该题型暂未开放哦！</div>
        <template #footer>
            <div style="display: flex; justify-content: flex-end; ">

                <el-popconfirm @confirm="closeQuestion" width="220" confirm-button-text="确认" cancel-button-text="不了"
                    icon="Warning" icon-color="#E6A23C" title="取消操作将会丢失表单数据，确认放弃？">
                    <template #reference>
                        <el-button icon="CloseBold">
                            取消
                        </el-button>
                    </template>
                </el-popconfirm>
                <el-button type="primary" icon="Select" @click="saveQuestion">
                    确定
                </el-button>
            </div>
        </template>
    </el-drawer>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElNotification, ElMessage, ElMessageBox } from 'element-plus'


const router = useRouter();
const route = useRoute();

const wjForm = ref({})
let surveyId = route.query.surveyId;
import surveyApi from '@/api/surveyApi';
const getSurveyById = async () => {
    let res = await surveyApi.getSurveyById(surveyId)
    wjForm.value = res.data;
    console.log('----> wjForm: ', wjForm);
    getExamineeList();
    getQuestionList();
}
getSurveyById()

// 修改问卷主表
const updateSurvey = async () => {
    let res = await surveyApi.updateSurvey(wjForm.value);
    ElMessage({
        message: '更新成功',
        type: 'success',
    })
}

// 新增问卷问题(考生信息)
const addExamineeInfo = async (content) => {
    let question = {
        surveyId: wjForm.value.id,
        questionType: 0,   // 考生信息
        content: content,
        orderNum: examineeList.value.length
    }
    let res = await surveyApi.addSurveyQuestion(question)
    ElMessage({
        message: '添加考生信息成功',
        type: 'success',
    })
    getExamineeList();
}

// 考生信息列表
const examineeList = ref([])
const getExamineeList = async () => {
    let res = await surveyApi.getExamineeList(wjForm.value.id);
    examineeList.value = res.data;
}

// 试题信息列表
const questionList = ref([])
const getQuestionList = async () => {
    let res = await surveyApi.getQuestionList(wjForm.value.id);
    questionList.value = res.data;
}

const deleteExaminee = (surveyId, questionId, questionType, oldOrder, newOrder) => {
    ElMessageBox.confirm(
        '您确认删除该考生信息项？',
        '删除确认',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            let res = await surveyApi.deleteSurveyQuestion(questionId);
            getExamineeList();
            ElMessage({
                message: '删除成功',
                type: 'success',
            })
            updateExamineeOrder(surveyId, questionId, questionType, oldOrder, newOrder);
        })
        .catch(() => {

        })

}

const deleteQuestion = (surveyId, questionId, questionType, oldOrder, newOrder) => {
    ElMessageBox.confirm(
        '您确认删除该试题？',
        '删除确认',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            let res = await surveyApi.deleteSurveyQuestion(questionId);
            getQuestionList()
            ElMessage({
                message: '删除成功',
                type: 'success',
            })
            updateQuestionOrder(surveyId, questionId, questionType, oldOrder, newOrder);
        })
        .catch(() => {

        })

}

// 调整顺序
const updateExamineeOrder = async (surveyId, questionId, questionType, oldOrder, newOrder) => {
    if ((oldOrder == 0 && newOrder == -1) || (oldOrder == examineeList.value.length - 1 && newOrder == examineeList.value.length)) {
        return;
    }
    let res = await surveyApi.updateSurveyQuestionOrder(surveyId, questionId, questionType, oldOrder, newOrder)
    getExamineeList();
}

const updateQuestionOrder = async (surveyId, questionId, questionType, oldOrder, newOrder) => {
    if ((oldOrder == 0 && newOrder == -1) || (oldOrder == questionList.value.length - 1 && newOrder == questionList.value.length)) {
        return;
    }
    let res = await surveyApi.updateSurveyQuestionOrder(surveyId, questionId, questionType, oldOrder, newOrder)
    getQuestionList()
}


const scoreOptions = [
    { value: 1, label: '1分' },
    { value: 2, label: '2分' },
    { value: 3, label: '3分' },
    { value: 4, label: '4分' },
    { value: 5, label: '5分' },
    { value: 6, label: '6分' },
    { value: 7, label: '7分' },
    { value: 8, label: '8分' },
    { value: 9, label: '9分' },
    { value: 10, label: '10分' }
]





const questionForm = ref({

})


// 打开试题编辑页面
const openQuestionDrawer = async (questionEditorComponent, title, questionType, questionTypeDesc, component) => {

    if (questionType != null && questionType != undefined) {

        let question = {
            surveyId: wjForm.value.id,
            questionType: questionType,
            content: '',
            orderNum: questionList.value.length,
            score: 5,
            questionTypeDesc: questionTypeDesc,
            component: component,
            questionEditorComponent: questionEditorComponent
        }
        let res = await surveyApi.addSurveyQuestion(question)
        questionForm.value = res.data;
        //questionForm.value.correctAnswer = [];
        console.log(questionForm.value);
    }

    editorDrawer.value.visible = true;
    editorDrawer.value.questionEditorType = questionEditorComponent;
    editorDrawer.value.title = title;
    console.log(title);
}

const getQuestionById = async (questionId, questionEditorComponent, title) => {
    let res = await surveyApi.getQuestionById(questionId);
    questionForm.value = res.data;

    editorDrawer.value.visible = true;
    editorDrawer.value.questionEditorType = questionEditorComponent;
    editorDrawer.value.title = title;
}

const copyQuestionById = async (questionId, questionEditorComponent, title) => {
    let res = await surveyApi.copyQuestionById(questionId);
    getQuestionById(res.data, questionEditorComponent, title);
}

const saveQuestion = async () => {
    await surveyApi.updateSurveyQuestion(questionForm.value)
    editorDrawer.value.visible = false;
    // 查询试题列表
    getQuestionList();
}

const closeQuestion = async () => {

    editorDrawer.value.visible = false;
    // 查询试题列表
    getQuestionList();
}


const saveWj = async() => {
    // 1. 问卷数据校验
    if (wjForm.value.title == '') {
        ElMessage({
            message: '问卷标题不能为空',
            type: 'warning',
        })
        return;
    }
    if (questionList.value.length == 0) {
        ElMessage({
            message: '您还没有添加试题',
            type: 'warning',
        })
        return;
    }
    // 2. 更新状态
    let res = await surveyApi.finishEditSurvey(wjForm.value)
    ElMessage({
            message: '操作成功',
            type: 'success',
        })
    // 3. 跳转到问卷列表页面
    setTimeout(() => {
      router.replace({
        path: '/wj/list'
      })   
    }, 1000);
}








/* const addQuestion = () => {
    console.log(questionForm.value);
    let options = questionForm.value.answerOptions.map(v => {
        return v.answerDesc;
    })
    if (checkDuplicates(options)) {
        ElNotification({
            title: '警告',
            message: '存在重复选项，请修复！',
            type: 'warning',
        })
        return;
    }
} */

function checkDuplicates(array) {
    return new Set(array).size !== array.length;
}


const closeDrawer = () => {
    editorDrawer.value.visible = false;

}

const editorDrawer = ref({
    visible: false,
    questionEditorType: "WjRadioEditor",
    title: ""
})











const questionList2 = ref([
    {
        id: 0, questionDesc: "关于Java描述，以下错误的是？", component: "WjRadioList", questionType: 0, questionTypeDesc: '单选', orderNum: 0,
        answerOptions: [
            { id: 0, answerDesc: "Java语言是一种面向对象的编程语言", correctAnswer: 0, orderNum: 0 },
            { id: 1, answerDesc: "运行效率比C语言更快", correctAnswer: 1, orderNum: 1 },
            { id: 2, answerDesc: "支持多线程", correctAnswer: 0, orderNum: 2 },
            { id: 3, answerDesc: "Java不依赖于平台，具备可移植性", correctAnswer: 0, orderNum: 3 },
        ], correctAnswer: [1]
    },
    {
        id: 1, questionDesc: "Redis有什么特点？", component: "WjCheckboxList", questionType: 1, questionTypeDesc: '多选', orderNum: 1,
        answerOptions: [
            { id: 0, answerDesc: "非关系型数据库", correctAnswer: 1, orderNum: 0 },
            { id: 1, answerDesc: "存取速度贼快，因为它把数据都读取到内存当中操作", correctAnswer: 1, orderNum: 1 },
            { id: 2, answerDesc: "提供了丰富的数据结构", correctAnswer: 1, orderNum: 2 },
            { id: 3, answerDesc: "是MySql的良好替代品", correctAnswer: 0, orderNum: 3 },
        ], correctAnswer: [0, 1, 2]
    },
    {
        id: 2, questionDesc: "关于微服务架构描述错误的是", component: "WjRadioList", questionType: 0, questionTypeDesc: '单选', orderNum: 1,
        answerOptions: [
            { id: 0, answerDesc: "微服务架构的本质依然是面向服务的其本质依然是解耦展现层与业务层", correctAnswer: 1, orderNum: 0 },
            { id: 1, answerDesc: "微服务架构中，服务是自治的，之间采用轻量级通信，去掉了ESB集中转发", correctAnswer: 1, orderNum: 1 },
            { id: 2, answerDesc: "微服务间绝对没有依赖关系，可独立开发、部署，无需考虑服务前向兼容", correctAnswer: 1, orderNum: 2 },
            { id: 3, answerDesc: "与面向服务架构相比，微服务划分粒度更细，通常设计遵循单一职责原则", correctAnswer: 0, orderNum: 3 },
        ], correctAnswer: [2]
    }
])




const stuInfoVisible = ref(true)
const stuInfoIcon = ref('CaretBottom')
const toggleStuInfoVisible = () => {
    stuInfoVisible.value = !stuInfoVisible.value;
    stuInfoIcon.value = stuInfoVisible.value ? 'CaretBottom' : 'CaretRight'
}
const questionInfoVisible = ref(true)
const questionInfoIcon = ref('CaretBottom')
const toggleQuestionInfoVisible = () => {
    questionInfoVisible.value = !questionInfoVisible.value;
    questionInfoIcon.value = questionInfoVisible.value ? 'CaretBottom' : 'CaretRight'
}


const totalScore = computed(() => {
    let score = 0;
    if (questionList.value.length > 0) {
        questionList.value.forEach(item => {
            score += item.score;
        });
    }
    return score;
})




const questionInfo = ref([
    {
        id: 0, label: "考试单选", icon: "User", questionType: 1, questionTypeDesc: "单选题", component: "WjRadioList",
        questionEditorComponent: "WjRadioEditor"
    },
    { id: 1, label: "考试判断", icon: "DataBoard" },
    {
        id: 2, label: "考试多选", icon: "Phone", questionType: 2, questionTypeDesc: "多选题", component: "WjCheckboxList",
        questionEditorComponent: "WjCheckboxEditor"
    },
    { id: 3, label: "代码题", icon: "Reading" },
    { id: 4, label: "单项填空", icon: "Check" },
    { id: 5, label: "多项填空", icon: "Finished" },
    { id: 6, label: "简答题", icon: "Tickets" },
    { id: 7, label: "多项简答", icon: "Memo" },
    { id: 8, label: "考试文件", icon: "Document" },
    { id: 9, label: "完型填空", icon: "ScaleToOriginal" },
    { id: 10, label: "考试须知", icon: "InfoFilled" }

])

const stuInfo = ref([
    { id: 0, label: "姓名", icon: "User" },
    { id: 1, label: "部门", icon: "DataBoard" },
    { id: 2, label: "手机", icon: "Phone" },
    { id: 3, label: "基本信息", icon: "Cpu" },
    { id: 4, label: "其它信息", icon: "Money" }
])

const activeQuestionIndex = ref(0)
const questiontypeList = ref([
    { id: 1, name: "题型" },
    { id: 2, name: "题库" }
])
const chooseQuestionType = (index) => {
    activeQuestionIndex.value = index;
}


const totalQuestion = ref(0)

const goback = () => {
    router.push("/wj/list")
}
</script>

<style lang='scss' scoped>
.back {
    position: fixed;
    left: 20px;
    top: 0;
    height: 42px;
    display: flex;
    align-items: center;

    .el-button {
        background-color: #313131;
        border: none;
    }

    .el-button:hover {
        background-color: #636262;
    }
}

.wj-design {
    height: 100vh;
    display: flex;
    flex-direction: column;

    .header {
        height: 42px;
        background-color: #313131;
        display: flex;
        justify-content: center;

        .nav {
            width: 1138px;
            height: 100%;
            color: white;
            font-size: 14px;
            display: flex;
            align-items: center;
            justify-content: space-between;

            .left {
                height: 100%;
                display: flex;
                align-items: center;

                .item {
                    margin-right: 15px;

                    .el-checkbox {
                        color: white;
                    }
                }

                .pointer {
                    cursor: pointer;
                }

                .flex {
                    display: flex;
                    align-items: center;

                    .el-icon {
                        margin-right: 3px;
                    }
                }
            }

            .right {
                display: flex;
                align-items: center;
                justify-content: flex-end;

                .btn {
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    height: 42px;
                    cursor: pointer;

                    .el-icon {
                        margin-right: 5px;
                    }

                    span {
                        font-weight: bolder;
                    }

                }

                .btn1 {
                    width: 85px;
                    background-color: white;
                    color: #0095ff;

                }

                .btn2 {
                    width: 111px;
                    background-color: #0095ff;
                }

                .btn2:hover {
                    background-color: #0086e6;
                    font-weight: bolder;
                }
            }
        }
    }

    .body {
        flex: 1;
        justify-content: center;
        display: flex;
        padding-top: 8px;
        background-color: #f5f5f5;

        .sidebar {
            width: 230px;
            background-color: white;
            height: 100%;
            padding: 10px 9px 10px;
            display: flex;
            flex-direction: column;
            align-items: center;

            .question-type {
                background-color: #e8e8e8;
                color: #262626;
                width: 204px;
                height: 31px;
                display: flex;
                align-items: center;
                justify-content: space-around;
                padding: 1px;
                border-radius: 3px;

                .item {
                    width: 98px;
                    height: 25px;
                    line-height: 25px;
                    text-align: center;
                    cursor: pointer;
                    border-radius: 3px;
                    font-size: 13px;
                }

                .activeItem {
                    background-color: white;
                    font-weight: bolder;
                }
            }

            .question-info {
                margin-top: 10px;

                .title {
                    cursor: pointer;
                    width: 208px;
                    height: 30px;
                    font-size: 14px;
                    display: flex;
                    align-items: center;

                    span {
                        margin-left: 5px;
                        font-weight: bolder;
                    }
                }

                .list {
                    display: flex;
                    flex-wrap: wrap;
                    justify-content: space-between;

                    .item {
                        cursor: pointer;
                        padding-left: 5px;
                        width: 100px;
                        height: 26px;
                        font-size: 13px;
                        display: flex;
                        align-items: center;
                        margin: 3px 0;

                        span {
                            margin-left: 5px;
                        }

                        .icon {
                            width: 16px;
                            height: 16px;
                            color: #0095ff;
                        }
                    }

                    .item:hover {
                        background-color: #0095ff;
                        color: white;
                        border-radius: 13px;
                    }

                    .item:hover .icon {
                        color: white;
                        margin-left: 5px;
                    }
                }
            }
        }

        .content {
            height: 100%;
            width: 900px;
            background-color: white;
            margin-left: 8px;
            padding: 0;

            :deep(.el-scrollbar__bar.is-vertical>div) {
                width: 100%;
            }

            --el-scrollbar-bg-color: red;

            :deep(.el-scrollbar__thumb) {
                left: 7px;
            }

            .title {
                --el-input-text-color: #0095ff;
                --el-input-border-color: none;
                height: 40px;
                font-size: 18px;
                font-weight: bolder;
                margin-bottom: 10px;
                margin-top: 40px;
            }

            .desc {
                --el-input-border-color: none;
                color: #262626;
            }

            .padding40 {
                padding: 0 40px;
            }

            .card {
                padding: 30px 0 40px;
                border-top: 1px solid white;
                border-left: 1px solid white;
                border-right: 1px solid white;
                border-bottom: 1px solid #e8e8e8;

                .question {
                    color: 16px;
                    padding: 0 40px 0;
                }

                .answerList {
                    margin: 10px 0;
                    color: #595959;
                    padding: 0 40px 0;

                    .answerItem {
                        margin-left: 15px;
                        color: limegreen;
                    }
                }

                position: relative;

                .card-opr {
                    display: none;
                    box-sizing: border-box;
                    height: 50px;
                    width: 100%;
                    margin: 0 auto;
                    display: none;
                    justify-content: space-between;
                    align-items: center;
                    position: absolute;
                    bottom: 0px;
                    padding: 0 40px 0;
                }
            }

            .card:hover .card-opr {
                box-sizing: border-box;
                display: flex;
                background-image: linear-gradient(0, #f5f5f5, #ffffff);
            }

            .card:hover {
                /* border: 1px dashed red; 
                border-radius: 10px;*/
                border-image: linear-gradient(45deg, #009688, yellowgreen, pink, red, #13a9e4, #9c27b1, #8bc34b) 1;
            }
        }
    }
}

.right-bar {
    width: 40px;
    height: 100px;
    background-color: aquamarine;
    position: fixed;
    right: 30px;
    top: 50%;

}
</style>