<script setup>
import {useStore} from "vuex";
import {computed, ref, onMounted} from "vue";
import {Swiper, SwiperSlide} from "swiper/vue";
import 'swiper/swiper-bundle.css'
import Heard from "../components/heard.vue";
import MapContainer from "../components/MapContainer.vue";
import router from "../router/index.js";
import hotelAPI from "../api/hotel.js";
const store = useStore()
const hotel = computed(() => {
  return store.getters.getHotel;
});
const SearchBoxRef = computed(() => {
  return store.getters.getSearchBox;
})
const TimeStart = ref('');
const TimeEnd = ref('');
const bookCount = ref(1);
const MapContainerRef = ref(null);
const CountDay = ref(1);
let selectRoom=ref(false)
console.log(hotel.value);
onMounted(() => {
  MapContainerRef.value.updateCenter(hotel.value.locations);
  if (SearchBoxRef.value) {
    TimeStart.value = SearchBoxRef.value.checkInDate;
    TimeEnd.value = SearchBoxRef.value.checkOutDate;
    computeCountDay();
    OnTimeChange();
  }
})
const book = (room) => {
  const bookInfo = {
    room_id: room.id,
    room_name: room.room_name,
    room_count: bookCount.value,
    CountDay: CountDay.value,
    TimeStart: TimeStart.value,
    TimeEnd: TimeEnd.value,
    price: room.price*CountDay.value*bookCount.value,
  }
  store.dispatch("updateBook", bookInfo);
  store.dispatch("updateHotel", hotel.value);
  router.push("/booking");
}
const computeCountDay = () => {
  if (TimeStart.value && TimeEnd.value) {
    CountDay.value = Math.ceil((new Date(TimeEnd.value) - new Date(TimeStart.value)) / (1000 * 60 * 60 * 24));
  } else {
    CountDay.value = 1; // 默认值，或处理无效输入
  }
}
const OnTimeChange = () => {
  computeCountDay();
  if (TimeStart.value && TimeEnd.value) {
    hotelAPI.selectRoomByHotelIdTime(hotel.value.id, TimeStart.value, TimeEnd.value).then((res) => {
      hotel.value.rooms = res.data;
      selectRoom=true;
      console.log('OnTimeChange' ,hotel.value);
    })
  }
}
</script>

<template>
  <heard></heard>
  <div class="Context">
    <div class="hotel-info">
      <div class="left">
        <swiper
            :loop="true"
            :pagination="{ clickable: true }"
            :navigation="true"

        >
          <swiper-slide v-for="(picture, index) in hotel.picture_url" :key="index">
            <img :src="picture" alt="图片加载失败" />
          </swiper-slide>
        </swiper>
        <div class="thumbnail-container">
          <img
              v-for="(picture, index) in hotel.picture_url"
              :key="index"
              :src="picture"
              class="thumbnail"
              alt="缩略图加载失败"
          />
        </div>
      </div>

<div class="right">
  <h1>{{hotel.name}}</h1>
  <p>{{hotel.city}},{{hotel.district}},{{hotel.address}}</p>
  <p>{{hotel.introduction}}</p>
  <h3>在地图上查看</h3>
  <div class="map"><MapContainer ref="MapContainerRef"></MapContainer></div>
</div>

    </div>
    <div>
      <div class="time">
        <el-form-item label="入住时间">
          <el-date-picker v-model="TimeStart" @change="OnTimeChange"></el-date-picker>
        </el-form-item>
        <el-form-item label="离店时间">
          <el-date-picker v-model="TimeEnd" @change="OnTimeChange"></el-date-picker>
        </el-form-item>
      </div>
      <h2>房间情况</h2>
      <div v-if="hotel.rooms && hotel.rooms.length > 0">
      <div v-for="room in hotel.rooms" :key="room" class="room">
        <img :src="room.room_picture_url">
        <div class="room-info">
          <h2>{{room.room_name}}</h2>
          <h3>{{room.bed_type}},数量{{room.bed_count}}</h3>
          <div class="item">
            <el-form-item>
              <el-checkbox v-model="room.facilities[0]" :disabled="true">无线网</el-checkbox>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="room.facilities[1]" :disabled="true">有线网</el-checkbox>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="room.facilities[2]" :disabled="true">毛巾</el-checkbox>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="room.facilities[3]" :disabled="true">拖鞋</el-checkbox>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="room.facilities[4]" :disabled="true">免费洗浴用品</el-checkbox>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="room.facilities[5]" :disabled="true">空调</el-checkbox>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="room.facilities[6]" :disabled="true">电视</el-checkbox>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="room.facilities[7]" :disabled="true">厨房</el-checkbox>
            </el-form-item>

          </div>
        </div>
        <div class="book-info">
          <P v-if="selectRoom">剩余{{room.available_rooms}}间</P>
          <el-form-item label="请输入房间数量">
            <el-input-number v-model="bookCount"></el-input-number>
          </el-form-item>
          <h2>{{room.price*CountDay*bookCount}}￥,{{CountDay}}晚</h2>
          <el-button type="primary" @click="book(room)">预订</el-button>
        </div>
      </div>
    </div>
      <h2 v-else>暂无房间</h2>
      <br>
    </div>
  </div>

</template>

<style scoped>
.swiper-slide img {
  width: 100%; /* 使图片宽度自适应 */
  height: 100%; /* 使图片高度自适应 */
  object-fit: cover; /* 保持图片比例，避免变形 */
}
.swiper{
  width: 100%;
}
.thumbnail-container {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}
.thumbnail {
  width: 80px; /* 缩略图宽度 */
  height: auto;
  margin: 0 5px; /* 缩略图间距 */
  cursor: pointer;
  opacity: 0.7; /* 缩略图透明度 */
}
.thumbnail:hover {
  opacity: 1; /* 鼠标悬停时增加透明度 */
}
.Context{
  width: 1280px;
  box-shadow: #646cff 0 0 5px;
}
.hotel-info{
  display: flex;
  justify-content: center;
  margin: 10px;
  height: 500px;
}
.left{
  width: 50%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.right{
  width: 50%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.room img{
  max-height: 200px;
}
.room{
  display: flex;
  justify-content: space-around;
}
.item{
  display: flex;
}
.el-button{
  width: 100px;
}
.MapContainer{
  width: 100px;
  height: 200px;
}
.map{
  width: 300px;
  height: 200px;
}
.time{
  display: flex;
  margin-left: 100px;
}
</style>