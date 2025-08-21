<template>
    <div class="user">
        <div>
            <span class="title">注册信息</span>
            <div class="info">
                <el-row class="row" style="height: 120px;" >
                    <el-col :span="5" class="label">头像</el-col>
                    <el-col :span="19" class="value">
                        <el-upload
                            :action="uploadAction"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :headers="uploadHeaders"
                        >
                            <img  :src="user.avatarUrl" style="height: 100px;width: 100px; border-radius: 10px" />
                        </el-upload>
                    </el-col>
                </el-row>
                <el-row class="row">
                    <el-col :span="5" class="label">用户名</el-col>
                    <el-col :span="19" class="value">
                        <span>{{ user.username }}</span>
                    </el-col>
                </el-row>
                <el-row class="row">
                    <el-col :span="5" class="label">昵称</el-col>
                    <el-col :span="19" class="value else">
                        <span>{{ user.nickname }}</span>
                        <div class="right">
                            <el-icon @click="openNicknameWindow">
                                <EditPen />
                            </el-icon>
                        </div>
                    </el-col>
                </el-row>
                <el-row class="row">
                    <el-col :span="5" class="label">账号ID</el-col>
                    <el-col :span="19" class="value">{{ user.id }}</el-col>
                </el-row>
                <el-row class="row">
                    <el-col :span="5" class="label">账户类型</el-col>
                    <el-col :span="19" class="value">{{ user.userTypeDesc }}</el-col>
                </el-row>
                <el-row class="row">
                    <el-col :span="5" class="label">邮件地址</el-col>
                    <el-col :span="19" class="value" v-if="user.email">{{ user.email }}</el-col>
                    <el-col :span="19" class="value else" v-else>
                        <span>未设置</span>
                        <div class="right">
                            <span>设置后可用于找回密码</span>
                            <el-icon>
                                <EditPen />
                            </el-icon>
                        </div>
                    </el-col>
                </el-row>
                <el-row class="row">
                    <el-col :span="5" class="label">手机号码</el-col>
                    <el-col :span="19" class="value else">
                        <span>{{ user.phone }}</span>
                        <div class="right">
                            <span>已验证</span>
                            <el-icon>
                                <EditPen />
                            </el-icon>
                        </div>
                    </el-col>
                </el-row>
                <el-row class="row">
                    <el-col :span="5" class="label">微信</el-col>
                    <el-col :span="19" class="value else" v-if="user.wechat">
                        <span>{{ user.wechat }}</span>
                        <div class="right">
                            <el-link :underline="false" style="text-decoration: underline;color: #0095ff;">解绑</el-link>
                        </div>
                    </el-col>
                    <el-col :span="19" class="value else" v-else>
                        <span>未绑定</span>
                        <div class="right">
                            <el-link :underline="false" style="text-decoration: underline;color: #0095ff;">绑定</el-link>
                        </div>
                    </el-col>
                </el-row>
                <el-row class="row">
                    <el-col :span="5" class="label">密码</el-col>
                    <el-col :span="19" class="value else">
                        <el-link @click="openPasswordWindow" :underline="false" style="text-decoration: underline;color: #0095ff;">修改密码</el-link>
                    </el-col>
                </el-row>
            </div>
        </div>
        <div>
            <span class="title">企业信息</span>
            <div class="info">
                <el-row class="row">
                    <el-col :span="5" class="label">企业名称</el-col>
                    <el-col :span="19" class="value">
                        <el-link :underline="false" style="text-decoration: underline;color: #0095ff;">请输入</el-link>
                    </el-col>
                </el-row>
                <el-row class="row">
                    <el-col :span="5" class="label">企业LOGO</el-col>
                    <el-col :span="19" class="value">
                        暂无
                    </el-col>
                </el-row>
                <el-row class="row">
                    <el-col :span="5" class="label">自定义域名</el-col>
                    <el-col :span="19" class="value">
                        <el-link :underline="false" style="text-decoration: underline;color: #0095ff;">设置域名</el-link>
                    </el-col>
                </el-row>
            </div>
        </div>

    </div>

    <el-dialog v-model="dialogFormVisibleNickname" title="修改昵称" width="600" :close-on-click-modal="false">
        <el-form :model="nicknameForm" :rules="nicknameFormRules" ref="nicknameFormRef">
            <el-form-item label="新昵称" :label-width="formLabelWidth" prop="nickname">
                <el-input v-model="nicknameForm.nickname" style="width: 85%;" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogFormVisibleNickname = false">取消</el-button>
                <el-button type="primary" @click="updateNickname">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

    <el-dialog v-model="dialogFormVisiblePassword" @close="clearPasswordForm" title="修改密码" :close-on-click-modal="false" width="600">
        <el-form :model="passwordForm" :rules="passwordFormRules" ref="passwordFormRef">
            <el-form-item label="原密码" :label-width="formLabelWidth" prop="password">
                <el-input type="password" v-model="passwordForm.password" style="width: 85%;" autocomplete="off" />
            </el-form-item>
            <el-form-item label="新密码" :label-width="formLabelWidth" prop="newPassword">
                <el-input type="password" v-model="passwordForm.newPassword" style="width: 85%;" autocomplete="off" />
            </el-form-item>
            <el-form-item label="确认密码" :label-width="formLabelWidth" prop="newPassword2">
                <el-input type="password" v-model="passwordForm.newPassword2" style="width: 85%;" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogFormVisiblePassword = false">取消</el-button>
                <el-button type="primary" @click="updatePassword">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

</template>

<script setup>
import { ref, toRefs } from 'vue'

import { useUserStore } from "@/stores/user";
const userStore = useUserStore();
const { user } = toRefs(userStore);

const dialogFormVisibleNickname = ref(false)
const dialogFormVisiblePassword = ref(false)
const formLabelWidth = '120px'
const nicknameForm = ref({})
const passwordForm = ref({})

const openNicknameWindow = () => {
    dialogFormVisibleNickname.value = true;
}

const openPasswordWindow = () => {
    dialogFormVisiblePassword.value = true;
}


const nicknameFormRules = ref({
    nickname: [
        { required: true, message: '请输入新昵称', trigger: 'blur' }
    ]
})

const checkNewPassword2 = (rule, value, callback) => {
  if (value != passwordForm.value.newPassword) {
    return callback(new Error('两次密码输入不一致'))
  }
  callback()
}

const passwordFormRules = ref({
    password: [
        { required: true, message: '请输入原密码', trigger: 'blur' },
        { min: 6, max: 20, message: "密码长度必须6~20位", trigger: 'blur'}
    ],
    newPassword: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        { min: 6, max: 20, message: "密码长度必须6~20位", trigger: 'blur'}
    ],
    newPassword2: [
        { required: true, message: '请输入确认密码', trigger: 'blur' },
        { min: 6, max: 20, message: "密码长度必须6~20位", trigger: 'blur'},
        { validator: checkNewPassword2, trigger: 'blur' }
    ]
})

import { ElMessage } from 'element-plus'
const nicknameFormRef = ref();
import userApi from '@/api/userApi';
const updateNickname = async() => {
    let valid = await nicknameFormRef.value.validate(() => {});
    if(valid){
        let res = await userApi.updateNickname(user.value.id,nicknameForm.value.nickname)
        ElMessage({
            message: res.message,
            type: 'success',
        })
        dialogFormVisibleNickname.value = false;
        userStore.refreshToken()
    }
}

const clearPasswordForm = () => {
    passwordForm.value = {}
    passwordFormRef.value.clearValidate()
}

import { useRouter, useRoute } from 'vue-router'
const router = useRouter();
const route = useRoute();
const passwordFormRef = ref()
const updatePassword = async() => {
    let valid = await passwordFormRef.value.validate(() => {});
    if(valid){
        await userApi.updatePassword(user.value.id,passwordForm.value.password,passwordForm.value.newPassword)
        ElMessage({
            message: '密码修改成功，请重新登录',
            type: 'success',
        })
        setTimeout(() => {
            userStore.logout().then(res => {
                router.replace({
                    path: '/login'
                })
            });
        }, 2000);
    }
}


const uploadHeaders = {
    "Authorization": userStore.token
}
//const imageUrl = ref("/src/assets/images/avatar.gif")
const uploadAction = import.meta.env.VITE_APP_BASE_API + "/file/upload";
const handleAvatarSuccess = async(response) => {
    // 更新用户表头像值
    await userApi.updateAvatar(user.value.id, response.data.fileName)
    // 刷新token
    userStore.refreshToken()
    ElMessage({
            message: '更新头像成功',
            type: 'success',
        })
}

</script>

<style lang='scss' scoped>
.user {
    width: 1000px;
    display: flex;
    justify-content: space-between;
    padding-top: 20px;

    .title {
        color: #323232;
        font-size: 18px;
        font-weight: bolder;
        line-height: 50px;

    }

    .info {

        width: 496px;
        background-color: white;
        box-shadow: 0px 0px 1px lightgrey;
        padding: 20px 30px;

        .row {
            height: 36px;
            line-height: 36px;

            .label {
                font: 13px;
                color: #8c8c8c;
            }

            .value {
                font: 13px;
                color: #262626;
            }

            .else {
                display: flex;
                align-items: center;
                justify-content: space-between;

                .right {
                    display: flex;
                    align-items: center;
                    justify-content: flex-end;

                    .el-icon {
                        margin-left: 3px;
                        cursor: pointer;
                        color: #8c8c8c;
                    }

                    .el-icon:hover {
                        color: #ff8000;
                    }
                }
            }
        }
    }


}
</style>