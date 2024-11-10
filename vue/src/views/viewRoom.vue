<script setup>
import {useStore} from "vuex";
import {computed} from "vue";
import router from "../router/index.js";
const store = useStore()
const hotel = computed(() => {
  const hotel=store.getters.getHotel;
  console.log(hotel)
  return hotel;
})
const rooms =hotel.value.rooms
console.log(rooms)
const updateRoom = (room) => {
  store.dispatch("updateRoom",room)
  router.push("/updateRoom");
}
</script>

<template>
<div class="Content">

  <div class="card" v-if="rooms.length>0">
    <div v-for="room in rooms" :key="room.id">
      <img :src="room.room_picture_url" alt="图片加载失败">
      <p>{{room.room_name }}</p>
      <p>房间数量:{{room.room_count}}</p>
      <p><span>{{room.price}}</span>￥起</p>
      <el-button @click="updateRoom(room)">修改房间</el-button>
    </div>
  </div>
  <div v-else>
    <p>暂无数据</p>
  </div>
</div>
</template>

<style scoped>
.card{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}
</style>