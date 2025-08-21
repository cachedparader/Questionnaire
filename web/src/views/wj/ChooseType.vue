<template>
    <div class="wjtype">
        <div class="nav">
            <div @click="changeType(index)" class="item" v-for="(item, index) in typeList"
                :class="{ activeItem: item.id == activeTypeIndex }">
                <component v-if="item.hasIcon" :is="item.icon" style="color:gold; width: 18px; height: 18px; margin: 0; ">
                </component>
                <div class="title">
                    <span :style="{ fontWeight: item.id == activeTypeIndex ? 'bolder' : 'none' }">{{ item.name }}</span>
                    <div class="underline"></div>
                </div>
            </div>
        </div>
        <div style="display: flex;justify-content: center;">
            <div v-if="typeList[activeTypeIndex].name == '通用应用'" class="typeCardList">
                <div class="typeCard" v-for="(item, index) in wjTypeList" @click="creatNew(item)">
                    <div class="left">
                        <div class="title">{{ item.name }}</div>
                        <div class="desc">{{ item.desc1 }}</div>
                        <div class="desc">{{ item.desc2 }}</div>
                    </div>
                    <div class="right">
                        <div class="icon">
                            <component :is="item.icon" :style="{ color: item.color }" style="width: 32px; height: 32px; ">
                            </component>
                        </div>
                        <div class="create">
                            <span style="font-size: 30px;margin-top: -10px;">＋</span>
                            <span style="font-size: 16px;margin-top: -10px;">创建</span>
                        </div>
                    </div>
                </div>

            </div>
            <div v-if="typeList[activeTypeIndex].name == '员工体验管理'">
                <div style="line-height: 100px;">此功能暂未开放</div>
            </div>
            <div v-if="typeList[activeTypeIndex].name == '客户体验管理'">
                <div style="line-height: 100px;">此功能暂未开放</div>
            </div>
            <div v-if="typeList[activeTypeIndex].name == '市场调研管理'">
                <div style="line-height: 100px;">此功能暂未开放</div>
            </div>
        </div>
    </div>
    <div class="footer">
        <img src="/src/assets/logo1.png" style="background-color: #ff399b;height: 16px;border-radius: 5px;">
        <span style="margin: 0 10px 0 3px;">问卷君</span>
        <span style="color: #bebebe;">青青菜鸟提供技术支持</span>
    </div>
</template>

<script setup>
import { ElMessage } from 'element-plus'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
let router = useRouter()

const creatNew = (item) => {
    if (item.name == '考试') {
        router.push({
            path: `/wj/blanknew`,
            query: {
                wjTypeId: item.id,
                wjTypeName: item.name
            }
        }) 
    } else {
        ElMessage({
            message: '该问卷类型暂未开放',
            type: 'warning'
        })
    }
}

const activeTypeIndex = ref(0)
const typeList = ref([
    { id: 0, name: "通用应用", hasIcon: false, icon: "None" },
    { id: 1, name: "员工体验管理", hasIcon: true, icon: "Dessert" },
    { id: 2, name: "客户体验管理", hasIcon: true, icon: "Dessert" },
    { id: 3, name: "市场调研管理", hasIcon: true, icon: "Dessert" }
])

const wjTypeList = ref([
    { id: 0, name: "调查", icon: "Notebook", color: "#1e9ef9", desc1: "丰富题型，强大逻辑", desc2: "问卷密码，红包抽奖" },
    { id: 1, name: "考试", icon: "Finished", color: "#ff399b", desc1: "题库抽题，限时作答", desc2: "成绩查询，自动阅卷" },
    { id: 2, name: "投票", icon: "Filter", color: "#ff9604", desc1: "图文视频，选项随机", desc2: "实时排行，微信投票" },
    { id: 3, name: "表单流程", icon: "Guide", color: "#00cd71", desc1: "信息登记，活动报名", desc2: "数据导入，流程审批" },
    { id: 4, name: "360度评估", icon: "Orange", color: "#3e74ff", desc1: "批量评估，权重维度", desc2: "教学评估，人才盘点" },
    { id: 5, name: "测评", icon: "Monitor", color: "#238cff", desc1: "支持多级测评维度", desc2: "自动呈现测评报告" },
    { id: 6, name: "接龙", icon: "Connection", color: "#609ce1", desc1: "预设名单，进度跟踪", desc2: "两码收集，作业收集" },
    { id: 7, name: "民主评议", icon: "SetUp", color: "#fa7500", desc1: "支持评议规则设置", desc2: "自动进行人才分类" }
])

const changeType = (index) => {
    activeTypeIndex.value = index;
}
</script>

<style lang='scss' scoped>
.wjtype {
    height: 80px;
    background-color: white;
    width: 100%;

    .nav {
        font-size: 16px;
        color: #8c8c8c;
        display: flex;
        justify-content: center;
        align-items: center;
        line-height: 77px;

        .item {
            display: flex;
            align-items: center;
            justify-content: center;

            .title {
                cursor: pointer;
                margin: 0 30px 0 0;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;

                span {
                    margin-left: 3px;
                }
            }

            .underline {
                width: 30px;
                border-bottom: 3px solid white;
            }
        }

        .activeItem {
            color: #262626;
            font-weight: bolder;

            .underline {
                border-bottom: 3px solid black;
            }
        }

    }

    .typeCardList {
        display: flex;
        justify-content: left;
        flex-wrap: wrap;
        width: 1008px;
        margin-top: 15px;
        .typeCard {
            cursor: pointer;
            box-shadow: 1px 1px 2px lightgray;
            margin: 6px;
            width: 323px;
            padding: 15px 15px 15px 30px;
            background-color: white;
            display: flex;
            justify-content: space-between;

            .right {
                display: flex;
                align-items: center;
                justify-content: center;

                .icon {
                    width: 96px;
                    height: 100%;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }

                .create {
                    display: none;
                    width: 96px;
                    height: 100%;
                    background-color: #f0f9ff;
                    border-radius: 2px;

                }
            }



            .left {
                margin: 15px 0;

                .title {
                    font-size: 20px;
                    color: #262626;
                }

                .desc {
                    font-size: 14px;
                    color: #8c8c8c;
                }
            }
        }

        .typeCard:hover .icon {
            display: none;
        }

        .typeCard:hover .create {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            color: #0095ff;
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
    position: fixed;
    bottom: 20px;
}
</style>