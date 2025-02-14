<script setup>
import RoomForm from "../components/RoomForm.vue";
import room from "../api/room.js";
import router from "../router/index.js";
import {ref} from "vue";
import {ElMessage} from "element-plus";

const roomRef = ref(null);
const updateRoom = () => {
  const form = roomRef.value.room
  console.log('updateRoom', form)

  room.update(form)
      .then(() => router.push('hotelManagement'))
      .catch((error) => {
        ElMessage.error(error.message);
      });
}
</script>

<template>
  <RoomForm ref="roomRef"></RoomForm>
  <el-button @click="updateRoom">更新房间</el-button>
</template>

<style scoped>

</style>