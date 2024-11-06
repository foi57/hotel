<script setup>
import router from "../router/index.js";
import {computed,ref,onUnmounted} from "vue";
import {useStore} from "vuex";
import optionStore from "../store/optionStore.js";
import hotelAPI from "../api/hotel.js";
const store = useStore()
const hoveredHotelId = ref(null);
const hotelList = computed(() => {
  const hotel=store.getters.getHotel;
  
  console.log(hotel)
  return hotel;
})
const overOption= computed(()=>{
  const option =optionStore.getters.getOptions;
  console.log(option)
  return option
})
const InnerPager = (hotel) => {
  store.dispatch("updateHotel",hotel);
  router.push(store.getters.getSelectHotel);
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
onUnmounted(() => {
  optionStore.dispatch("setOptions","")
})
const updateHotel = (hotel) => {
  console.log('updateHotel',hotel)
  store.dispatch("updateHotel",hotel)
  router.push("/updateHotel");
}
const deleteHotel = async (hotel) => {
  try {
    console.log('deleteHotelId',hotel.id)
    await hotelAPI.deleteHotelById(hotel.id)
     await store.dispatch("updateRemoveHotelFromList", hotel.id)
  }catch (error){
    console.log('删除酒店失败')
  }

}
</script>

<template>
  <div class="card" v-if="hotelList.length>0">
    <div v-for="hotel in hotelList" :key="hotel.id" @click="InnerPager(hotel)"
         @mouseover="hoveredHotelId = hotel.id"
         @mouseleave="hoveredHotelId = null"
    class="hotelCard">
      <img :src="hotel.picture_url[0]" alt="图片加载失败">
      <p>{{hotel.name}}</p>
      <p>{{hotel.city}},{{hotel.district}}</p>
      <p><span>{{lowestPrice(hotel)}}</span>￥起</p>
      <!-- 仅当悬浮的酒店与当前酒店匹配时显示按钮 -->
      <div class="hotelOptions" v-if="hoveredHotelId === hotel.id && overOption==='hotel'">
        <el-button @click.stop="updateHotel(hotel)">修改酒店信息</el-button>
        <el-button >
          查看房间
        </el-button>
        <el-button @click.stop="deleteHotel(hotel)">删除酒店</el-button>
      </div>
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
.hotelCard{
  width: 350px;
  height: 360px;
  box-shadow: #1a1a1a 0 0 5px;
  margin: 10px;
  transition: all 0.5s;
}
.hotelCard:hover{
  transform: scale(1.25);
}
.hotelCard img{
  height:auto;
  max-width: 300px;

}

.hotelCard p{
  margin: 2px 2px;
}
.hotelCard p:last-child span:first-child{
  color: red;

}
.hotelCard p:last-child span:last-child{
  font-size: 22px;
}
.hotelCard p:nth-child(4) span:nth-child(1){
  background-color: #535bf2;
  color: #fff;
  padding: 2px 4px;
  border-radius: 4px;
}
.hotelOptions{
   position: absolute;
   top: 50%;
   left: 50%;
   transform: translateX(-50%) translateY(-50%);
}
</style>