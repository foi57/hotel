<script setup>
import { ref, onMounted } from 'vue';
import {jwtDecode} from "jwt-decode";

const userName = ref('');

const getUserNameFromToken = () => {
  const token = localStorage.getItem('token');
  if (token) {
    const decoded = jwtDecode(token);
    userName.value = '你好,' + (decoded.userName);
  }else {
    userName.value = '登录';
  }
};
onMounted(() => {
  getUserNameFromToken();
})
const showDropdown = ref(false);
const handleLogout = () => {
  localStorage.removeItem('token');
  window.location.reload();
}
const handleLogin = () => {
  if (userName.value==='登录') {
    window.location.href = '/login'
  }
}
</script>

<template>
  <div class="heard" @mouseleave="showDropdown=false">
    <label class="user" @mouseover="showDropdown=true" @click="handleLogin">{{userName}}</label>
    <div class="dropdown" v-if="showDropdown&&userName!=='登录'" >
      <ul>
        <li>个人信息</li>
        <li @click="handleLogout">退出</li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.heard{
  width: 100%;
  height: 50px;
  background-color: #535bf2;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  font-size: 20px;
  font-weight: 600;
  color: white;
  z-index: 1000;
  position: absolute;
  top: 0;
  left: 0;
}
.user{
  margin-right: 20px;
}
.dropdown ul {
  list-style: none;
  padding: 0;
  margin: 0;
  position: absolute;
  top: 50px;
  right: 0;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  z-index: 1000;
}
.dropdown li {
  color: #333;
  padding: 10px;
  cursor: pointer;
}
.dropdown li:hover {
  background-color: #f0f0f0;
}
</style>