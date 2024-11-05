<script setup>
  import {ref,reactive} from 'vue'
  import userRequest from '../api/user.js'
  import router from "../router/index.js";
  import {ElMessage} from "element-plus";
  const form = ref({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    inputCode: ''
  });
  let code = ref(null);
  const formEl = ref(null);
  // 60秒倒计时变量
  const countdown = ref(0);  // 倒计时，初始为 0

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
   ],
   inputCode:[
     { required: true, message: '请输入验证码', trigger: 'blur' },
   ],
   confirmPassword: [
     { required: true, message: '请确认密码', trigger: 'blur' },
     { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
     // 自定义规则，检查 confirmPassword 是否和 password 一致
     { validator: (rule, value, callback) => {
         if (value !== form.value.password) {
           callback(new Error('两次输入的密码不一致'));
         } else {
           callback();
         }
       }, trigger: 'blur'
     }
   ],
 })
  const onSubmit = () => {
    console.log('code',code.value);
    console.log('incode',form.value.inputCode);
   if (`${code.value}` !== form.value.inputCode) {
     ElMessage.error('验证码错误');
     return ;
   }
    if (!formEl) {
      console.log('!formEl');
      return false
    }
    formEl.value.validate((valid) => {
      if (valid) {
        console.log('submit!')
        userRequest.enroll(form.value)
            .catch(error=>{
              ElMessage.error(error);
            })
        router.push('login');
      }else{
        ElMessage.error('请完成表单填写');
        return false
      }
    });
  };
  const sendCode = () => {
    if (!form.value.email) {
      ElMessage.error('请输入邮箱');
      return;
    }

    // 如果倒计时大于0，表示还在倒计时期间
    if (countdown.value > 0) {
      ElMessage.error(`请等待 ${countdown.value} 秒后再试`);
      return;
    }

    // 开始倒计时
    userRequest.sendCode({ email: form.value.email })
        .then(response => {
          code.value = response.data; // 获取验证码
          ElMessage.success('验证码已发送');

          // 设置倒计时为 60 秒
          countdown.value = 60;

          // 启动倒计时，每秒更新一次
          const interval = setInterval(() => {
            countdown.value--;
            if (countdown.value <= 0) {
              clearInterval(interval); // 倒计时结束，清除定时器
            }
          }, 1000);
        })
        .catch(error => {
          ElMessage.error('发送验证码失败，请重试');
          console.error(error);
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
      <div class="code">
        <el-form-item label="验证码" prop="inputCode">
          <el-input v-model="form.inputCode"></el-input>
          <!-- 显示倒计时，不能再次点击发送验证码 -->
          <el-button
              class="RnButton"
              @click="sendCode"
              :disabled="countdown > 0">
            {{ countdown > 0 ? `${countdown}秒后重发` : '发送验证码' }}
          </el-button>
        </el-form-item>
      </div>
      <el-form-item label="密码"  prop="password">
        <el-input type="password" v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input type="password" v-model="form.confirmPassword"></el-input>
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

.code .el-input{
  width: 80px;
}
.code .el-button{
  margin-left: -70px;
}
</style>