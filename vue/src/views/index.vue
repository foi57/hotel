<!-- index.vue -->
<template>
  <heard></heard>
  <div class="Context">
    <div class="Select">
      <el-autocomplete v-model="destination" :fetch-suggestions="querySearch" placeholder="目的地" style="width: 200px;"></el-autocomplete>
      <el-date-picker v-model="checkInDate" type="date" placeholder="入住日期"></el-date-picker>
      <el-date-picker v-model="checkOutDate" type="date" placeholder="退房日期"></el-date-picker>
      <el-select v-model="guestCount" placeholder="入住人数">
        <el-option v-for="n in 10" :key="n" :label="n" :value="n"></el-option>
      </el-select>
      <el-button type="primary">搜酒店</el-button>
    </div>
    <h1>特惠酒店</h1>
    <div class="card" v-if="hotelList.length>0">
      <div v-for="hotel in hotelList" :key="hotel.id">
        <img :src="hotel.picture_url[0]" alt="图片加载失败">
        <p>{{hotel.name}}</p>
        <p>{{hotel.city}},{{hotel.district}}</p>
        </div>
    </div>
    <div v-else>
      <p>暂无数据</p>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted ,reactive} from "vue";
import heard from "../components/heard.vue";
import hotel from "../api/hotel.js";
import locationData from '../assets/locationData.json'
const destination = ref('');
const checkInDate = ref('');
const checkOutDate = ref('');
const guestCount = ref(1);
let hotelList = ref([]);
onMounted(async () => {
  hotel.selectHotel()
      .then(response => {
        hotelList.value = response.data;
        console.log(hotelList)
      })
})
const querySearch = (queryString, cb) => {
  const results= [];
  const query = queryString.toLowerCase();
  locationData.forEach(province => {
    province.citys.forEach(city => {
      if (city.city.toLowerCase().includes(query)) {
        results.push({ value: city.city, text: city.city });
      }
      city.areas.forEach(area => {
        if (area.area.toLowerCase().includes(query)) {
          results.push({ value: `${city.city}-${area.area}`, text: `${city.city}-${area.area}` });
        }
      });
    });
  });
  console.log('results', results)
  cb(results);
}
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
  max-height: 170px;
  max-width: 300px;
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

.Select .el-button{
  margin-left: 10px;
}
.Select{
  margin-top: 50px;
}
.Select .el-select{
  width: 60px;
}
</style>