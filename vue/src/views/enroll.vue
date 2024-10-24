<script setup>
  import {ref,reactive} from 'vue'
  import userRequest from '../api/user.js'
  import router from "../router/index.js";
  const form = ref({
    username: '',
    email: '',
    password: ''
  });
  const formEl = ref(null);
 const rules= ref({
   username:[
     { required: true, message: '请输入用户名', trigger: 'blur' },
     { min: 1, max: 16, message: '长度在 1 到 16 个字符', trigger: 'blur' }
   ],
   email:[
     { required: true, message: '请输入邮箱', trigger: 'blur' },
     { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }],
   password:[
     { required: true, message: '请输入密码', trigger: 'blur' },
     { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
   ]
 })
  const onSubmit = () => {
    if (!formEl) {
      console.log('!formEl');
      return false
    }
    formEl.value.validate((valid) => {
      if (valid) {
        userRequest.enroll(form.value)
        router.push('login');
      }else{
        console.log('error submit!')
        return false
      }
    });
  };
</script>

<template>
  <div class="Context">
    <el-form  :model="form" :rules="rules" ref="formEl">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="邮箱"  prop="email">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="密码"  prop="password">
        <el-input type="password" v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="RnButton" @click="onSubmit">注册</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<style scoped>
.Context{
  width: 300px;
  box-shadow: 0 0 5px #888888;
  padding: 10px;
  border-radius: 10px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
}

.RnButton{
  position: relative;
  left: 40%;
}
</style>