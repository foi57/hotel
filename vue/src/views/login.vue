<script setup>
import {ref} from "vue";
import {reactive} from "vue";
import userRequest from "../api/user.js";
import router from "../router/index.js";
import {ElMessage} from "element-plus";
const formEl = ref(null)
const form = ref({
  email: '',
  password: ''
})
const rules = {
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
  ]
}
const  handleLogin = () => {
  formEl.value.validate((valid) => {
    if (valid) {
      const forCopy = JSON.parse(JSON.stringify(form.value));
      console.log(forCopy);
      userRequest.login(forCopy)
          .then(response => {
            if (response.data.token) {
              localStorage.setItem('token', response.data.token);
              router.push('/')
            }
            else {
              ElMessage.error(response.data.message || '用户名或密码错误');
            }
          })
          .catch(error => {
            console.log(error)
            ElMessage.error("登录失败")
          });
    }else{
      console.log('error submit!')
    }
  })
}
</script>

<template>
<div>
  <h1>用户登录</h1>

  <el-form :model="form" :rules="rules" ref="formEl">
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="form.email" ></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input type="password" v-model="form.password"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleLogin">登录</el-button>
    </el-form-item>
  </el-form>
<router-link to="/enroll">没有账号？点击注册</router-link>
</div>
</template>

<style scoped>

</style>