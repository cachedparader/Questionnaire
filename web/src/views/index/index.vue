<template>
    <div style="display:flex;width:100%;height:100vh;flex-direction: column;">
        <div class="header">
            <div class="nav">
                <div class="logo" @click="home">
                    <el-image src="/src/assets/logo1.png"></el-image>
                    <span>问卷君</span>
                </div>
                <div style="display: flex;align-items: center;">
                    <div style="margin-right: 20px;">
                        <el-button style="color:#0095ff">升级</el-button>
                    </div>
                    <div style="display: flex;align-items: center;cursor: pointer;" @click="myWj">
                        <el-icon size="18" color="#0095ff">
                            <HomeFilled />
                        </el-icon>
                        <span style="margin-left: 3px; color: #8c8c8c;font-size: 14px;">我的问卷</span>
                    </div>
                    <el-divider direction="vertical" style="margin: 0 14px;" />
                    <div style="display: flex;align-items: center;cursor: pointer;">
                        <el-icon size="18" color="#0095ff">
                            <List />
                        </el-icon>
                        <span style="margin-left: 3px; color: #8c8c8c;font-size: 14px;">联系人</span>
                    </div>
                    <el-divider direction="vertical" style="margin: 0 14px;" />
                    <el-icon style="margin-right: 20px;" color="#999999" size="18">
                        <QuestionFilled />
                    </el-icon>
                    <el-badge :hidden="false" :value="3" style="height: 18px; width: 20px; margin-right:20px;">
                        <el-icon style=" padding: 0; margin-right: 15px;" color="#999999" size="18">
                            <Comment />
                        </el-icon>
                    </el-badge>
                    <el-badge :hidden="false" :value="2" style="height: 18px; width: 20px; margin-right: 20px;">
                        <el-icon style=" padding: 0; margin-right: 15px;" color="#999999" size="18">
                            <Bell />
                        </el-icon>
                    </el-badge>


                    <div class="userinfo">
                        <el-image :src="user.avatarUrl"></el-image>
                        <div style="display: flex;align-items: center;">
                            <el-dropdown trigger="click">
                                <span
                                    style="cursor: pointer; display: flex;align-items: center;border: none;outline: none;">{{user.nickname}}<el-icon
                                        class="el-icon--right"><arrow-down /></el-icon></span>
                                <template #dropdown>
                                    <el-dropdown-menu>
                                        <el-dropdown-item icon="User" @click="userinfo">用户信息</el-dropdown-item>
                                        <el-dropdown-item icon="Headset">客服中心</el-dropdown-item>
                                        <el-dropdown-item @click="logout" icon="SwitchButton" divided>退出</el-dropdown-item>
                                    </el-dropdown-menu>
                                </template>
                            </el-dropdown>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="main">
            <RouterView/>
        </div>
    </div>
    <div class="footer">
        <img src="/src/assets/logo1.png" style="background-color: #ff399b;height: 16px;border-radius: 5px;">
        <span style="margin: 0 10px 0 3px;">问卷君</span>
        <span style="color: #bebebe;">青青菜鸟提供技术支持</span>
    </div>
</template>

<script setup>
import { ref , toRefs} from 'vue'

import { useRouter, useRoute } from 'vue-router'
let router = useRouter()
router.push({ path: '/wj/manage' })


import { useUserStore } from "@/stores/user";
const userStore = useUserStore();
const { user } = toRefs(userStore);

const logout = async() => {
    await userStore.logout()
    router.replace({
        path: '/login'
    })
}

const myWj = () => {
    router.push({ path: '/wj/list' })
}

const home = () => {
    router.push({ path: '/wj/list' })
}

const userinfo = () => {
    router.push({ path: '/user/info' })
}



const activeMenuIndex = ref(0)
const changeMenuItem = (index, path) => {
    activeMenuIndex.value = index;
    router.push({
        path: path
    })

}
const menuList = ref([
    { id: 0, name: "全部问卷", path: "/test", icon: "Document", border: false },
    { id: 1, name: "星标问卷", path: "/test", icon: "Star", border: false },
    { id: 2, name: "回收站", path: "/test", icon: "Delete", border: false },
    { id: 3, name: "文件夹", path: "/test", icon: "Folder", border: true }
]);



</script>

<style lang='scss' scoped>
.header {
    display: flex;
    justify-content: center;
    background-color: white;
    border-bottom: 1px solid #ededed;

    .nav {
        height: 58px;
        width: 1392px;
        display: flex;
        justify-content: space-between;
        align-items: center;

        .logo {
            display: flex;
            align-items: center;
            color: #ff399b;
            font-size: 16px;
            cursor: pointer;
            .el-image {
                background-color: #ff399b;
                border-radius: 10px;
                width: 32px;
                margin-right: 10px;
            }
        }

        .userinfo {
            height: 36px;
            display: flex;
            align-items: center;
            font-size: 14px;
            background-color: #f0f0f0;
            padding: 0 10px 0 5px;
            border-radius: 18px;

            .el-image {
                width: 28px;
                height: 28px;
                border-radius: 14px;
                margin-right: 8px;
            }
        }
    }
}

.main {
    padding: 0;
    display: flex;
    justify-content: center;
    background-color: #f7f7f7;
    flex: 1;

}

.footer {
    font-size: 14px;
    color: #8c8c8c;
    height: 50px;
    line-height: 50px;
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    bottom: 20px;
    text-align: center;
    width: 100%;
}
</style>