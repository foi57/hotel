<script setup>
import {ref, onMounted, reactive} from 'vue';
import locationData from '../assets/locationData.json'
import {ElMessage} from "element-plus";
import Heard from "../components/heard.vue";
import MapContainer from "../components/MapContainer.vue";
import hotel from "../api/hotel.js";
import router from "../router/index.js";
const MapContainerRef = ref(null);
const provinces = ref([]); // 省份列表
const cities = ref([]); // 城市列表
const districts = ref([]); // 区县列表
const form = ref({
  name:'',
  introduction: '',
  picture_url: [],
  province: '',
  city: '',
  district: '',
  address: '',
  locations: [],
  rooms: [{
    roomName: '',
    roomPictures: [],
    roomCount: 1,
    bedType: '',
    bedCount: 1,
    facilities: [false, false, false, false, false, false, false, false],
    price: 0
  }]
})
let allAddress=ref('');
const beds =['单人床','双人床'];

// 增加房间
const addRoom = () => {
  form.value.rooms.push({
    roomName: '',
    roomPictures: [],
    roomCount: 1,
    bedType: '',
    bedCount: 1,
    facilities: [false, false, false, false, false, false, false, false]
    ,price: 0
  });
};
const removeRoom = () => {
  form.value.rooms.pop();
}

onMounted(() => {
  provinces.value = locationData.map(province=>province.province);
});


const handleProvinceChange = (value) =>{
  allAddress='';
  const selectedProvince = locationData.find(province => province.province === value);
  cities.value=selectedProvince.citys.map(city =>city.city)
  form.value.city='';
  form.value.district='';
  districts.value=[];
  allAddress=allAddress+form.value.province;
  MapContainerRef.value.handleInput(allAddress);
}
const handleCityChange = (value) => {
  allAddress='';
  const selectedProvince = locationData.find(province => province.province === form.value.province);
  const selectedCity = selectedProvince.citys.find(city => city.city === value);
  districts.value = selectedCity.areas.map(area => area.area);
  form.value.district = '';
  allAddress=form.value.province+form.value.city;
  MapContainerRef.value.handleInput(allAddress);
};
const handlePictureUpdate=(file)=>{
  const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isJPG) {
    ElMessage.error('上传图片只能是 JPG 或 PNG 格式!');
  }
  if (!isLt2M) {
    ElMessage.error('上传图片大小不能超过 2MB!');
  }
  return isJPG&&isLt2M;
}
const handleExceed = () => {

  ElMessage.error('上传图片数量达到上限')
}
const onSubmit = () => {
  console.log(form.value)
  hotel.addHotel(form.value)
     // router.push('/');
    }
const HotelHandlePictureUploadSuccess = (response) => {
  form.value.picture_url.push(response);
}
const RoomHandlePictureUploadSuccess = (response,index) => {
  form.value.rooms[index].roomPictures.push(response);
}
const handleHotelPictureRemove = (fileName,index) => {
  console.log(fileName);
  hotel.deletePicture(fileName);
  form.value.picture_url.splice(index,1);
}
const handleRoomPictureRemove = (fileName,index,roomIndex) => {
  hotel.deletePicture(fileName);
  form.value.rooms[roomIndex].roomPictures.splice(index,1);
}
const mapLocation = (location) => {
  MapContainerRef.value.handleInput(location);
}
const updateLocations = (locations) => {
  form.value.locations = locations;
}
</script>

<template>
  <div class="Context">
  <heard></heard>
  <el-form class="form">
    <el-form-item label="酒店名">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="酒店介绍">
      <el-input type="textarea" v-model="form.introduction"></el-input>
    </el-form-item>
    <el-form-item label="酒店图片">
      <el-upload list-type="picture-card" accept="image/*" :before-upload="handlePictureUpdate" :limit="5" :on-exceed="handleExceed"
      :action="'http://localhost:8080/api/PictureUpload'"
      :on-success="HotelHandlePictureUploadSuccess"
      :on-remove="(file)=>handleHotelPictureRemove(file.name,file.index)"></el-upload>
    </el-form-item>
    <div class="address-row">
    <el-form-item label="省份">
      <el-select v-model="form.province" @change="handleProvinceChange">
        <el-option v-for="province in provinces" :key="province" :label="province" :value="province"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="城市">
      <el-select v-model="form.city" @change="handleCityChange">
        <el-option v-for="city in cities" :key="city" :label="city" :value="city"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="区县">
      <el-select v-model="form.district" @change="mapLocation(form.province+form.city+form.district)">
        <el-option v-for="district in districts" :key="district" :label="district" :value="district"></el-option>
      </el-select>
    </el-form-item>
    </div>
    <el-form-item label="具体地址">
      <el-input v-model="form.address" type="textarea" @input="mapLocation(form.province+form.city+form.district+form.address)"></el-input>
    </el-form-item>
    <MapContainer ref="MapContainerRef" @update:locations="updateLocations"></MapContainer>
    <h4>住房信息</h4>
    <div class="root" v-for="(room,index) in form.rooms" :key="index">
      <h5>房间 {{ index + 1 }}</h5>
    <el-form-item label="房间名称">
      <el-input v-model="room.roomName"></el-input>
    </el-form-item>
    <el-form-item label="房间数量">
      <el-input-number v-model="room.roomCount"></el-input-number>
    </el-form-item>
    <el-form-item label="房间图图片">
      <el-upload list-type="picture-card" multiple accept="image/*" :before-upload="handlePictureUpdate" :limit="1" :on-exceed="handleExceed"
                 :action="'http://localhost:8080/api/PictureUpload'"
                 :on-success="(response)=>RoomHandlePictureUploadSuccess(response,index)"
                 :on-remove="(file)=>handleRoomPictureRemove(file.name,file.index,index)"></el-upload>
    </el-form-item>
      <div class="bed">
     <el-form-item label="床类型">
       <el-select v-model="room.bedType">
         <el-option v-for="bed in beds" :key="bed" :label="bed" :value="bed"></el-option>
       </el-select>
     </el-form-item>
     <el-form-item label="床数量" >
       <el-input-number v-model="room.bedCount"></el-input-number>
     </el-form-item>
        </div>
      <h4>设施</h4>
      <div class="function" >

     <el-form-item>
       <el-checkbox v-model="room.facilities[0]">无线网</el-checkbox>
     </el-form-item>
        <el-form-item>
          <el-checkbox v-model="room.facilities[1]">有线网</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="room.facilities[2]">毛巾</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="room.facilities[3]">拖鞋</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="room.facilities[4]">免费洗浴用品</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="room.facilities[5]">空调</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="room.facilities[6]">电视</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="room.facilities[7]">厨房</el-checkbox>
        </el-form-item>
      </div>
      <el-form-item label="价格">
        <el-input-number v-model="room.price"></el-input-number>
      </el-form-item>
    </div>
    <el-button @click="addRoom">增加房间</el-button><el-button @click="removeRoom">删除房间</el-button><br>
    <el-button type="primary" @click="onSubmit">提交</el-button>
  </el-form>
  </div>
</template>

<style scoped>

.form{
  width: 500px;
}
.address-row{
  display: flex;
  justify-content: space-between;
}
.address-row .el-select{
  width: 130px;
}
.function .el-input{
  width: 50px;
}
.function{
  display: flex;
  justify-content: space-between;
}
.bed{
  display: flex;
  justify-content: space-between;
}
.bed .el-select{
  width: 100px;
}
.Context{
  margin-top: 20px;
}
</style>