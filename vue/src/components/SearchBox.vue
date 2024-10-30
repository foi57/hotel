<script setup>
import {ref} from "vue";
import locationData from "../assets/locationData.json";
import hotel from "../api/hotel.js";
const form = ref({
  destination: '',
  checkInDate: '',
  checkOutDate: '',
  guestCount: 1
});

const querySearch = (queryString, cb) => {
  const results= [];
  const query = queryString.toLowerCase();
  locationData.forEach(province => {
    province.citys.forEach(city => {
      if (city.city.toLowerCase().includes(query)) {
        results.push({ value: city.city, text: city.city });
      }
    })
  })
  cb(results);
}

const handleSearch = () => {
  hotel.selectHotelBySearchBox(form.value)
      .then(response => {
        console.log(response.data)
      })
}
</script>

<template>
  <div class="Select">
    <el-autocomplete v-model="form.destination" :fetch-suggestions="querySearch" placeholder="目的地" style="width: 200px;"></el-autocomplete>
    <el-date-picker v-model="form.checkInDate" type="date" placeholder="入住日期"></el-date-picker>
    <el-date-picker v-model="form.checkOutDate" type="date" placeholder="退房日期"></el-date-picker>
    <el-select v-model="form.guestCount" placeholder="入住人数">
      <el-option v-for="n in 10" :key="n" :label="n" :value="n"></el-option>
    </el-select>
    <el-button type="primary" @click="handleSearch">搜酒店</el-button>
  </div>
</template>

<style scoped>
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