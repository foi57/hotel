<script setup>
import RoomForm from "../components/RoomForm.vue";
import room from "../api/room.js";
import router from "../router/index.js";

const roomRef = ref(null);
const updateRoom = (form) => {
  console.log('updateRoom', form)

  room.update(form)
      .then(() => router.push('hotelManagement'))
      .catch((error) => {
        Element.error(error.message);
      });
}
</script>

<template>
  <RoomForm ref="roomRef"></RoomForm>
  <el-button>更新房间</el-button>
</template>

<style scoped>

</style>