<template>
    <div class="body">
        <el-row class="row">
            <el-col :span="6">
                <div class="sidebar">
                    <div>
                        <el-button type="primary" @click="newWj"
                            style="width:240px;height:56px;font-size:16px;color:white;font-weight:bold;">
                            <span style="font-size: 24px;">＋</span>
                            <span style="position: relative;top: 1px;">创建问卷</span>
                        </el-button>
                    </div>

                    <div style="margin: 24px 0 0;">
                        <div @click="changeMenuItem(index, item)"
                            :class="{ activeMenuItem: index == activeMenuIndex, border: item.border }" class="menu-item"
                            v-for="(item, index) in menuList">
                            <component :is="item.icon" style="width: 16px; height: 16px; margin: 0 10px 0 22px; ">
                            </component>
                            {{ item.name }}
                        </div>
                    </div>


                </div>
            </el-col>
            <el-col :span="18">
                <el-scrollbar :style="{ height: 'calc(100vh - 60px)' }">
                    <div class="content">
                        <RouterView :key="route.fullPath" />
                    </div>
                </el-scrollbar>
            </el-col>
        </el-row>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElNotification } from 'element-plus'
let router = useRouter()
let route = useRoute()
router.push({ path: '/wj/list' })

const newWj = () => {
    router.push({ path: '/wj/choosetype' })
}


const activeMenuIndex = ref(0)
const changeMenuItem = (index, item) => {
    
    if (item.accessFlag) {
        activeMenuIndex.value = index;
        router.push({
            path: item.path,
            query:{
                star: item.star
            }
        })
    } else {
        ElNotification({
            type: 'warning',
            title: '权限不足',
            message: "此功能仅对企业用户开放！",
            position: 'top-left',
        })
    }


}
const menuList = ref([
    { id: 0, name: "全部问卷", path: "/wj/list", icon: "Document", border: false, accessFlag: true },
    { id: 1, name: "星标问卷", path: "/wj/list", icon: "Star", border: false, accessFlag: true , star:1},
    { id: 2, name: "回收站", path: "/wj/recycle", icon: "Delete", border: false, accessFlag: true },
    { id: 3, name: "文件夹", path: "/wj/list", icon: "Folder", border: true, accessFlag: false }
]);

</script>

<style lang='scss' scoped>
.body {
    width: 1392px;

    .row {
        flex-grow: 1;

        .sidebar {
            margin-top: 20px;

            .menu-item {
                width: 240px;
                height: 44px;
                display: flex;
                align-items: center;
                font-size: 14px;
                color: gray;
                cursor: pointer;

            }

            .activeMenuItem {
                color: black;
                font-weight: bold;
                background-color: #ededed;
            }

            .border {
                border-bottom: 1px solid #e8e8e8;
                border-top: 1px solid #e8e8e8;
            }
        }

        .el-scrollbar {
            :deep(.el-scrollbar__bar.is-vertical>div) {
                width: 10%;
            }

            --el-scrollbar-bg-color: red;

            :deep(.el-scrollbar__thumb) {
                left: 7px;
            }
        }
    }


}
</style>