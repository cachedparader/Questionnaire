<template>
    <div class="logo">
        <el-image src="/src/assets/logo1.png"></el-image>
        <span>问卷君</span>
    </div>
    <div class="body" :style="{ backgroundImage: 'url(' + bgUrl + ')' }">
        <div class="login">
            <div class="login-tab">
                <div class="item" :class="{ selected: activeIndex == 0 }" @click="changTab(0)">
                    <span>个人用户</span>
                    <div class="none" style="width: 40px;"></div>
                </div>
                <div class="item" :class="{ selected: activeIndex == 1 }" @click="changTab(1)">
                    <span>企业用户</span>
                    <div class="none" style="width: 40px;"></div>
                </div>
            </div>
            <div class="login-form">
                <el-form ref="userFormRef" :model="userForm" :rules="rules" v-if="activeIndex == 0">
                    <el-form-item prop="phone">
                        <el-input v-model="userForm.phone" placeholder="请输入手机号码" prefix-icon="Iphone" />
                    </el-form-item>
                    <el-form-item prop="captcha">
                        <el-input v-model="userForm.captcha" placeholder="验证码" prefix-icon="Message">
                            <template #append>
                                <el-button @click="getCaptcha" :disabled="disableSms">
                                    <span v-if="ttl == ttlInit">获取验证码</span>
                                    <span v-else>重发{{ ttl }}秒</span>
                                </el-button>
                            </template>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input v-model="userForm.password" type="password" placeholder="设置密码（6~20位字母、数字或特殊符号）"
                            prefix-icon="Lock" />
                    </el-form-item>

                    <el-form-item prop="isAgree">
                        <div style="width:100%; display: flex;justify-content: space-between;align-items: center;">
                            <div>
                                <el-checkbox style="height: 20px;line-height: 20px;" v-model="userForm.isAgree"
                                    label="我同意问卷君 《用户服务协议》和 《隐私条款》" size="small" />
                            </div>
                        </div>
                    </el-form-item>
                </el-form>

                <el-form ref="userFormRef" :model="userForm" :rules="rules" v-else>
                    <el-form-item prop="phone">
                        <el-input v-model="userForm.phone" placeholder="请输入手机号码" prefix-icon="Iphone" />
                    </el-form-item>
                    <el-form-item prop="captcha">
                        <el-input v-model="userForm.captcha" placeholder="验证码" prefix-icon="Message">

                            <template #append>
                                <el-button>获取验证码</el-button>
                            </template>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input v-model="userForm.password" type="password" placeholder="设置密码（6~20位字母、数字或特殊符号）"
                            prefix-icon="Lock" />
                    </el-form-item>
                    <el-form-item prop="company">
                        <el-input v-model="userForm.company" placeholder="请输入公司名称" prefix-icon="School" />
                    </el-form-item>
                    <el-form-item>
                        <div style="width:100%; display: flex;justify-content: space-between;align-items: center;">
                            <div>
                                <el-checkbox style="height: 20px;line-height: 20px;" v-model="userForm.isAgree"
                                    label="我同意问卷君 《用户服务协议》和 《隐私条款》" size="small" />
                            </div>
                        </div>
                    </el-form-item>
                </el-form>


                <el-button type="primary"
                    style="width: 100%;height: 40px;background-color: #f79000;border: none; outline: none;"
                    @click="reg">创建用户</el-button>
                <div style="margin:20px 0; height: 30px; display: flex; justify-content: center;">
                    <span>已有帐号？</span>
                    <el-link href="/login" :underline="false"
                        style="text-decoration: underline;color: #0095ff;">立即登录</el-link>
                </div>



            </div>
        </div>
    </div>
    <div style="width: 250px;position: fixed;right: 100px;bottom: 70px;">
        <div style="font-size: 20px;color: white;">《登顶笔架峰》</div>
        <div style="font-size: 14px;color: white;line-height: 30px;padding-left: 10px;">拍摄于青青菜鸟二月家乡徒步</div>
    </div>
</template>

<script setup scoped>
import { ref, watchEffect } from 'vue';
import { ElMessage } from 'element-plus'

const userFormRef = ref()
import smsApi from '@/api/smsApi.js';
let iid = ""
let disableSms = ref(false)
const getCaptcha = async () => {
    // 手机号非空校验
    let isValid = await userFormRef.value.validateField("phone", () => { })
    // 发请求
    if (isValid) {
        disableSms.value = true;
        let res = await smsApi.getCaptcha(userForm.value.phone);
        ElMessage({
            message: '验证码已发送',
            type: 'success',
        })
        // 倒计时
        iid = setInterval(() => {
            ttl.value--;
        }, 1000);
    }
}

const ttlInit = 10;
const ttl = ref(ttlInit)


watchEffect(() => {
    if (ttl.value <= 0) {
        // 结束定时器
        clearInterval(iid)
        // ttl恢复成初始值
        ttl.value = ttlInit;
        disableSms.value = false;
    }
})

import { ElMessageBox } from 'element-plus'
import { useRouter, useRoute } from 'vue-router'
const router = useRouter();
const route = useRoute();
import userApi from '@/api/userApi';
const reg = async () => {
    let isValid = await userFormRef.value.validate((valid, fields) => { })
    if (isValid) {
        await userApi.register(userForm.value)
        userForm.value = {}
        userFormRef.value.clearValidate();
        ElMessageBox.confirm(
            '注册成功，去登录吧',
            '恭喜你',
            {
                confirmButtonText: '确定',
                showCancelButton: false,
                type: 'success',
                closeOnClickModal:false
            }
        )
            .then(() => {
                router.push("/login")
            })
            .catch(() => {
                ElMessage({
                    type: 'info',
                    message: 'Delete canceled',
                })
            })
    }
}



let otherLogins = ref([
    { id: 1, desc: 'QQ登录', img: '/src/assets/images/qq0.png', img0: '/src/assets/images/qq0.png', img1: '/src/assets/images/qq1.png' },
    { id: 2, desc: '微信登录', img: '/src/assets/images/wx0.png', img0: '/src/assets/images/wx0.png', img1: '/src/assets/images/wx1.png' },
    { id: 3, desc: '支付宝登录', img: '/src/assets/images/zfb0.png', img0: '/src/assets/images/zfb0.png', img1: '/src/assets/images/zfb1.png' },
])
const switchImg = (index, img) => {
    otherLogins.value[index].img = img;
}


const checkPhone = (rule, value, callback) => {
    let re = /^1(3|4|5|6|7|8|9)\d{9}/
    if (!re.test(value)) {
        return callback(new Error('手机号输入错误'))
    }
    callback()
}

const checkCaptcha = (rule, value, callback) => {
    smsApi.checkCaptcha(userForm.value.phone, value).then(res => {
        if (!res.data) {
            return callback(new Error('验证码错误'))
        }
        callback()
    })
}


const userForm = ref({})
const rules = ref({
    phone: [
        { required: true, message: '请输入手机号码', trigger: 'blur' },
        { validator: checkPhone, trigger: 'blur' }
    ],
    captcha: [
        { required: true, message: '请输入验证码', trigger: 'blur' },
        { min: 6, max: 6, message: '请输入6位验证码', trigger: 'blur' },
        { validator: checkCaptcha, trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 20, message: '密码长度6~20', trigger: 'blur' }
    ],
    isAgree: [
        { required: true, message: '请同意条款', trigger: 'blur' }
    ],
})

const bgUrl = ref('/src/assets/images/bjf.jpg');

const activeIndex = ref(0)
const changTab = (index) => {
    activeIndex.value = index;
}



</script>

<style lang="scss">
.logo {
    position: fixed;
    left: 80px;
    top: 50px;
    color: #ff399b;
    font-size: 25px;
    font-weight: bold;
    display: flex;
    align-items: center;
    text-shadow: -2px -2px 5px white;

    .el-image {
        width: 50px;
        border-radius: 50px;
        background-color: #ff399b;
        margin-right: 10px;
        opacity: 0.8;
        box-shadow: 0 0 10px white;
    }
}

.body {
    height: 100vh;
    background-color: lightgray;
    display: flex;
    justify-content: center;
    align-items: center;
    background-position: center;
    background-size: cover;
    background-repeat: no-repeat;

    .login {
        width: 480px;

        background-color: white;
        border-radius: 12px;
        padding: 40px;

        .login-tab {
            display: flex;
            color: #8c8c8c;
            font-weight: 600;
            font-family: Microsoft YaHei, Helvetica Neue, Helvetica, Arial, sans-serif;

            .item {
                cursor: pointer;
                margin-right: 20px;
                display: flex;
                flex-direction: column;
                align-items: center;
                font-size: 20px;

                .none {
                    color: white;
                }
            }

            .selected {
                color: #262626;
                font-family: '微软雅黑';

                .none {
                    border: 1px solid #f79000;
                }
            }
        }

        .login-form {
            margin-top: 40px;

            .el-input {
                height: 40px;
            }

            /* 修改输入后获得焦点的颜色 */
            .el-input {
                --el-input-focus-border-color: #f79000;
            }
        }

        .otherLogin {
            display: flex;
            justify-content: space-around;
            color: #8c8c8c;
            font-size: 12px;

            .item {
                cursor: pointer;
                display: flex;
                flex-direction: column;
                align-items: center;

                .el-image {
                    margin-bottom: 6px;
                    width: 50px;
                    height: 50px;
                    border-radius: 25px;
                    border: 1px solid lightgray;
                    padding: 0;
                }
            }
        }
    }
}
</style>