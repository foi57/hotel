<script setup>
import {ref,computed} from "vue";
import locationData from "../assets/locationData.json";
import hotel from "../api/hotel.js";
import {useStore} from "vuex";
import router from "../router/index.js";
let form = ref({
  destination: '',
  checkInDate: '',
  checkOutDate: '',
});
const hotels = ref([]);
const store = useStore()
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
computed(form.value= store.getters.getSearchBox)
const handleSearch = (page) => {
  console.log(form.value)
  hotel.selectHotelByCityTime(form.value.destination,form.value.checkInDate,form.value.checkOutDate,1,10)
      .then(response => {
        hotels.value = response.data.hotelFormList
        const page =ref({
          currentPage: response.data.currentPage,
          totalPage: response.data.totalPage,
        })
        store.dispatch("updateHotel",hotels.value)
        store.dispatch("updatePages",page.value)
      })
  store.dispatch("updateSearchBox",form.value)
  router.push("/selectHotel")
}
defineExpose({handleSearch});
</script>

<template>
  <div class="Select">
    <el-autocomplete v-model="form.destination" :fetch-suggestions="querySearch" placeholder="目的地" style="width: 200px;"></el-autocomplete>
    <el-date-picker v-model="form.checkInDate" type="date" placeholder="入住日期"></el-date-picker>
    <el-date-picker v-model="form.checkOutDate" type="date" placeholder="退房日期"></el-date-picker>
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