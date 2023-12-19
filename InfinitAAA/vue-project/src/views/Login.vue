<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
//import { ElMessage } from 'element-plus'
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
//定义数据模型
const registerData = ref({
    username:'',
    password:'',
    rePassword:''
})

//校验密码的函数
const checkRePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次确认密码'))
    } else if (value !== registerData.value.password) {
        callback(new Error('请确保两次输入的密码一样'))
    } else {
        callback()
    }
}


//定义表单校验规则
const rules={
    username:[
        { required: true, message: '请输入用户名', trigger: 'blur'},
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur'}
    ],
    password:[
        { required: true, message: '请输入密码', trigger: 'blur'},
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur'}
    ],
    rePassword:[
        {validator:checkRePassword,trigger:'blur'}
    ]
}

//调用后台接口,完成注册
import { userRegisterService, userLoginService} from '@/api/user.js'
const register = async () => {
    //registerData是一个响应式对象,如果要获取值,需要.value
        const { username, password, rePassword } = registerData.value;

    if (password !== rePassword) {
        // 密码和确认密码不一致，给出提示
        alert('密码和确认密码不一致，请重新输入');
        return;
    }
    let result = await userRegisterService(registerData.value);
    alert('注册成功')

    //alert(result.msg ? result.msg : '注册成功');
   // ElMessage.success(result.msg ? result.msg : '注册成功')
}

//绑定数据,复用注册表单的数据模型
//表单数据校验
//登录函数
import {useTokenStore} from '@/stores/token.js'
import {useRouter} from 'vue-router'
const router = useRouter()
//const tokenStore = useTokenStore();
const login =async ()=>{
    //调用接口,完成登录
   let response =  await userLoginService(registerData.value);
     alert('登录成功')

   // alert(result.msg? result.msg : '登录成功')
  //  ElMessage.success(result.msg ? result.msg : '登录成功')
    //把得到的token存储到pinia中
   // tokenStore.setToken(response.data)
    //跳转到首页，push完成跳转
    router.push('/')
}

//定义函数,清空数据模型的数据
const clearRegisterData = ()=>{
    registerData.value={
        username:'',
        password:'',
        rePassword:''
    }
}
</script>

<template>
    <el-row class="login-page"  align="middle">
        <el-col :span="14" :offset="5" class="form">
            <!-- 注册表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules" label-position="top" label-width="100px">
                <el-form-item>
                    <h1>注册</h1>
                </el-form-item>
                <el-form-item prop="username" label="用户名">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword" label="再次确认密码">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码" v-model="registerData.rePassword"></el-input>
                </el-form-item>
                <!-- 注册按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="register" >
                        注册
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false;clearRegisterData()">
                        ← 返回
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- 登录表单 -->
            <el-form ref="form"  size="large" autocomplete="off" v-else :model="registerData" label-position="top" label-width="100px" :rules="rules">
                <el-form-item>
                    <h1>登录到Infinite Music</h1>
                </el-form-item>
                <el-form-item label="用户名" prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>记住我</el-checkbox>
                        <el-link type="primary" :underline="false">忘记密码？</el-link>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true;clearRegisterData()" >
                        注册 →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */

    body {
        background-color: #0F181F;
    }
.login-page {
    height: 700px;
    width: 620px;
    background-color: #0A0C0B;
    border-radius: 20px;
    margin: 110px auto;


    

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;
        background-color: #0A0C0B;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }

        h1 {
            font-size: 36px;
            color: #F2F2F2;
        }
    }
}


 
</style>