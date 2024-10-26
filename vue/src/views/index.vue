<!-- index.vue -->
<template>
  <heard></heard>
  <div class="Context">
    <h1>特惠酒店</h1>
    <div class="card">

      <div>
        <p>东京,日本</p>
        <p><span>8.8</span>好评如潮</p>
        <p><span>123￥</span> <span>88￥</span></p>
      </div>

      <div>
        <img src="../assets/酒店/18690165.jpg" alt="图片加载失败">
        <p>M’s Plus 四条大宫酒店</p>
        <p>东京,日本</p>
        <p><span>8.8</span>好评如潮</p>
        <p><span>123￥</span> <span>88￥</span></p>
      </div>
      <div>
        <img src="../assets/酒店/18690165.jpg" alt="图片加载失败">
        <p>M’s Plus 四条大宫酒店</p>
        <p>东京,日本</p>
        <p><span>8.8</span>好评如潮</p>
        <p><span>123￥</span> <span>88￥</span></p>
      </div>
      <div>
        <img src="../assets/酒店/18690165.jpg" alt="图片加载失败">
        <p>M’s Plus 四条大宫酒店</p>
        <p>东京,日本</p>
        <p><span>8.8</span>好评如潮</p>
        <p><span>123￥</span> <span>88￥</span></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted ,reactive} from "vue";
import heard from "../components/heard.vue";
import hotel from "../api/hotel.js";
let hotelList = reactive([]);
onMounted(async () => {
  hotel.selectHotel()
      .then(response => {
        hotelList = response.data;
        console.log(hotelList)
      })
})
</script>

<style scoped>
  .Context {
    width: 1300px;
  }
  .card {
   width: 100%;
    display: flex;
    justify-content: space-around;
    padding: 0;
  }
.card div{
  width: 350px;
  padding: 0;

}
.card div img{
  max-height: 100%;
  max-width: 100%;
  transition: all 0.5s;
}
  .card div img:hover{
    transform: scale(1.25);
  }
.card div p{
  margin: 2px 2px;
}
.card div p:last-child span:first-child{
  color: red;
  text-decoration: line-through
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