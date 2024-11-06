<script setup>
import {useStore} from "vuex";
import {computed} from "vue";
const store = useStore()
const hotel = computed(() => {
  const hotel=store.getters.getHotel;
  console.log(hotel)
  return hotel;
})
const rooms =hotel.value.rooms
console.log(rooms)
</script>

<template>
<!--<div class="Content">-->
<!--  <div class="card" v-if="rooms.length>0">-->
<!--    <div v-for="room in rooms" :key="room.id">-->
<!--      <img :src="room.picture_url[0]" alt="图片加载失败">-->
<!--      <p>{{room.name}}</p>-->
<!--      <p>{{room.room_count}}</p>-->
<!--      <p><span>{{room.price}}</span>￥起</p>-->
<!--    </div>-->
<!--  </div>-->
<!--  <div v-else>-->
<!--    <p>暂无数据</p>-->
<!--  </div>-->
<!--</div>-->
</template>

<style scoped>

</style>