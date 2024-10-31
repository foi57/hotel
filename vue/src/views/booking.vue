<script setup>
import Heard from "../components/heard.vue";
import {useStore} from "vuex";
import {computed,ref} from "vue";
import book from "../api/book.js";
const store = useStore();
const hotel = computed(() => {
  const hotel=store.getters.getHotel
  console.log(hotel)
  return hotel;
})
const bookInfo = computed(() => {
  const bookInfo = store.getters.getBook
  console.log("bookInfo",bookInfo)
  return {info:bookInfo,
    TimeStartFormatted: formatDate(bookInfo.TimeStart),
    TimeEndFormatted: formatDate(bookInfo.TimeEnd),
  };
})
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return new Intl.DateTimeFormat('zh-CN',{year: 'numeric', month:'numeric', day:'numeric'}).format(date);
}
const form = ref({
  room_id: bookInfo.value.info.room_id,
  room_count: bookInfo.value.info.room_count,
  timeStart: bookInfo.value.info.TimeStart,
  timeEnd: bookInfo.value.info.TimeEnd,
  name: '',
  phone: '',
  email: '',
  arrivalTime: '',
  price: bookInfo.value.info.price
})
const onSubmit = () => {
  console.log(form.value)
  book.booking(form.value,bookInfo.value.TimeStartFormatted,bookInfo.value.TimeEndFormatted)
    .catch((error) => {
      console.log(error)
    })
}
</script>

<template>
  <heard></heard>
<div class="Context">
  <div class="hotelInfo">
    <p>{{hotel.name}}</p>
    <img :src="hotel.picture_url[0]" alt="图片加载失败">
    <p>{{hotel.city}},{{hotel.district}},{{hotel.address}}</p>
    <p>{{bookInfo.info.room_name}}</p>
    <p>入住时间:{{bookInfo.TimeStartFormatted}} 离店时间:{{bookInfo.TimeEndFormatted}}</p>
    <p>数量:{{bookInfo.info.bookCount}}</p>
    <p>总价:{{bookInfo.info.price}}元</p>
  </div>
  <div class="GuestInfo">
  <H2>住客信息</H2>
  <el-form :model="form">
    <el-form-item label="姓名">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="联系电话">
      <el-input v-model="form.phone"></el-input>
    </el-form-item>
    <el-form-item label="邮件（选填）">
      <el-input v-model="form.email"></el-input>
    </el-form-item>
    <el-form-item label="预计到店时间">
      <el-time-select v-model="form.arrivalTime"></el-time-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">预订</el-button>
    </el-form-item>
  </el-form>
  </div>
</div>
</template>

<style scoped>
.Context{
  width: 1280px;
  box-shadow: #646cff 0 0 5px;
  display: flex;
  align-items: center;
  justify-content: space-around;
}
.GuestInfo{
  width: 300px;
  padding: 10px;}
.hotelInfo img{
  width: 300px;
  height: 200px;

}

</style>