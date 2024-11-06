<script setup>
import router from "../router/index.js";
import {computed} from "vue";
import {useStore} from "vuex";
const store = useStore()
const hotelList = computed(() => {
  const hotel=store.getters.getHotel;
  
  console.log(hotel)
  return hotel;
})
const InnerPager = (hotel) => {
  store.dispatch("updateHotel",hotel);
  router.push("/hotelInnerPages");
}
const lowestPrice = (hotel) => {
  let min = hotel.rooms[0].price
  for (let i = 0; i < hotel.rooms.length; i++) {
    if (hotel.rooms[i].price < min) {
      min = hotel.rooms[i].price
    }
  }
  return min
}
</script>

<template>
  <div class="card" v-if="hotelList.length>0">
    <div v-for="hotel in hotelList" :key="hotel.id" @click="InnerPager(hotel)">
      <img :src="hotel.picture_url[0]" alt="图片加载失败">
      <p>{{hotel.name}}</p>
      <p>{{hotel.city}},{{hotel.district}}</p>
      <p><span>{{lowestPrice(hotel)}}</span>￥起</p>
    </div>
  </div>
  <div v-else>
    <p>暂无数据</p>
  </div>
</template>

<style scoped>
.card {
  width: 100%;
  display: flex;
  justify-content: space-around;
  padding: 0;
}
.card div{
  width: 350px;
  box-shadow: #1a1a1a 0 0 5px;
  margin: 10px;
  transition: all 0.5s;
}
.card div:hover{
  transform: scale(1.25);
}
.card div img{
  max-height:100%;
  max-width: 300px;

}

.card div p{
  margin: 2px 2px;
}
.card div p:last-child span:first-child{
  color: red;

}
.card div p:last-child span:last-child{
  font-size: 22px;
}
.card div p:nth-child(4) span:nth-child(1){
  background-color: #535bf2;
  color: #fff;
  padding: 2px 4px;
  border-radius: 4px;
}

</style>