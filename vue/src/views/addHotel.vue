<script setup>
import {ref, onMounted, } from 'vue';
import locationData from '../assets/locationData.json'
import {ElMessage, ElMessageBox} from "element-plus";
import Heard from "../components/heard.vue";
import MapContainer from "../components/MapContainer.vue";
import hotel from "../api/hotel.js";
import router from "../router/index.js";
import {useStore} from "vuex";
const store = useStore()
const MapContainerRef = ref(null);
const provinces = ref([]); // 省份列表
const cities = ref([]); // 城市列表
const districts = ref([]); // 区县列表
const formRef = ref(null);
const form = ref({
  name:'',
  introduction: '',
  picture_url: [],
  province: '',
  city: '',
  district: '',
  address: '',
  locations: [],
  rooms: []
})
onMounted(() => {
  const hotel = store.getters.getHotel;
  console.log('getHotel',hotel)
})
const rules = ref({
  name: [{ required: true, message: '请填写酒店名', trigger: 'blur' }],
  introduction: [{ required: true, message: '请填写酒店介绍', trigger: 'blur' }],
  province: [{ required: true, message: '请选择省份', trigger: 'change' }],
  city: [{ required: true, message: '请选择城市', trigger: 'change' }],
  district: [{ required: true, message: '请选择区县', trigger: 'change' }],
  address: [{ required: true, message: '请填写具体地址', trigger: 'blur' }],
  picture_url: [{ type: 'array', min: 1, message: '请至少上传一张酒店图片', trigger: 'change' }],
  rooms: [{
    type: 'array',
    required: true,
    message: '请添加至少一个房间',
    trigger: 'change'
  }],
  'rooms[].room_name': [{ required: true, message: '请填写房间名称', trigger: 'blur' }],
  'rooms[].room_count': [{ required: true, type: 'number', message: '请填写房间数量', trigger: 'change'  }],
  'rooms[].price': [{ required: true, type: 'number', min: 1, message: '请填写房间价格', trigger:'change'  }],
  'rooms[].bed_type': [{ required: true, message: '请填写床型', trigger: 'change' }],
});


let allAddress=ref('');
const beds =['单人床','双人床'];

// 增加房间
const addRoom = () => {
  form.value.rooms.push({
    room_name: '',
    room_picture_url: '',
    room_count: 1,
    bed_type: '',
    bed_count: 1,
    facilities: [false, false, false, false, false, false, false, false]
    ,price: 1
  });
};
const deleteHotelPictureIds = ref([]);
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
  // 提交前验证表单
  for (let i = 0; i < form.value.rooms.length; i++) {
    if (!form.value.rooms[i].room_name || !form.value.rooms[i].room_count || !form.value.rooms[i].bed_type || !form.value.rooms[i].price) {
      ElMessage.error('请填写完整的房间信息');
      return false;
    }
  }
  formRef.value.validate((valid) => {
    if (valid) {
      hotel.addHotel(form.value)
          .then(() => router.push('/'));
      ElMessageBox.confirm(
          '确认提交吗？',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
    } else {
      ElMessage.error('请完成表单填写');
      return false;
    }
  });
    }
    let upPictureIndex =ref(0)
const HotelHandlePictureUploadSuccess = (response,file) => {
  console.log('upPicture',file)
    form.value.picture_url.push(response.fileUrl);
    console.log('PictureUrl',form.value.picture_url)
    deleteHotelPictureIds.value.push({index:upPictureIndex,fileName:file.name});
    upPictureIndex=upPictureIndex+1;
    console.log('PictureId',deleteHotelPictureIds.value)
}
const RoomHandlePictureUploadSuccess = (response,file,index) => {
  form.value.rooms[index].room_picture_url =response.fileUrl;

}
const handleHotelPictureRemove = (fileName) => {
  hotel.deletePicture(fileName);
  const index = deleteHotelPictureIds.value.findIndex(item => item.fileName === fileName);
  form.value.picture_url.splice(index,1);
}
const handleRoomPictureRemove = (fileName,roomIndex) => {
  hotel.deletePicture(fileName);
  form.value.rooms[roomIndex].room_picture_url='';
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
  <el-form class="form" :rules="rules" ref="formRef" :model="form">
    <el-form-item label="酒店名" prop="name">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="酒店介绍" prop="introduction">
      <el-input type="textarea" v-model="form.introduction"></el-input>
    </el-form-item>
    <el-form-item label="酒店图片" prop="picture_url">
      <el-upload list-type="picture-card" accept="image/*" :before-upload="handlePictureUpdate" :limit="5" :on-exceed="handleExceed"
      :action="'http://localhost:8080/api/PictureUpload'"
      :on-success="(response, file)=>HotelHandlePictureUploadSuccess(response,file)"
      :on-remove="(file)=>handleHotelPictureRemove(file.name)"></el-upload>
    </el-form-item>
    <div class="address-row">
    <el-form-item label="省份" prop="province">
      <el-select v-model="form.province" @change="handleProvinceChange">
        <el-option v-for="province in provinces" :key="province" :label="province" :value="province"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="城市" prop="city">
      <el-select v-model="form.city" @change="handleCityChange">
        <el-option v-for="city in cities" :key="city" :label="city" :value="city"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="区县" prop="district">
      <el-select v-model="form.district" @change="mapLocation(form.province+form.city+form.district)">
        <el-option v-for="district in districts" :key="district" :label="district" :value="district"></el-option>
      </el-select>
    </el-form-item>
    </div>
    <el-form-item label="具体地址" prop="address">
      <el-input v-model="form.address" type="textarea" @input="mapLocation(form.province+form.city+form.district+form.address)"></el-input>
    </el-form-item>
    <div class="map">
      <MapContainer ref="MapContainerRef" @update:locations="updateLocations"></MapContainer>
    </div>

    <h4>住房信息</h4>
    <div class="root" v-for="(room,index) in form.rooms" :key="index">
      <h5>房间 {{ index + 1 }}</h5>
    <el-form-item label="房间名称" :prop="`rooms[${index}].room_name`">
      <el-input v-model="room.room_name"></el-input>
    </el-form-item>
    <el-form-item label="房间数量" :prop="`rooms[${index}].room_count`">
      <el-input-number v-model="room.room_count"></el-input-number>
    </el-form-item>
    <el-form-item label="房间图图片">
      <el-upload list-type="picture-card" multiple accept="image/*" :before-upload="handlePictureUpdate" :limit="1" :on-exceed="handleExceed"
                 :action="'http://localhost:8080/api/PictureUpload'"
                 :on-success="(response, file) => RoomHandlePictureUploadSuccess(response, file,index)"
                 :on-remove="(file)=>handleRoomPictureRemove(file.name,index)"></el-upload>
    </el-form-item>
      <div class="bed">
     <el-form-item label="床类型" :prop="`rooms[${index}].bed_type`">
       <el-select v-model="room.bed_type">
         <el-option v-for="bed in beds" :key="bed" :label="bed" :value="bed"></el-option>
       </el-select>
     </el-form-item>
     <el-form-item label="床数量" >
       <el-input-number v-model="room.bed_count"></el-input-number>
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
      <el-form-item label="价格" :prop="`rooms[${index}].price`">
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
.map{
  width: 100%;
  height: 300px;
}
</style>