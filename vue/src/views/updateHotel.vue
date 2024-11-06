<script setup>
import {ref, onMounted, } from 'vue';
import locationData from '../assets/locationData.json'
import {ElMessage, ElMessageBox} from "element-plus";
import MapContainer from "../components/MapContainer.vue";
import router from "../router/index.js";
import {useStore} from "vuex";
import hotelAPI from "../api/hotel.js"
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
})
const fileList=ref([]);
let hotel= ref(null);
onMounted(() => {
   hotel = store.getters.getHotel;
  console.log('getHotel',hotel);
  if (hotel) {
    form.value = {
      name: hotel.name,
      introduction: hotel.introduction,
      picture_url: hotel.picture_url || [],
      province: hotel.province,
      city: hotel.city,
      district: hotel.district,
      address: hotel.address,
      locations: hotel.locations || [],
    };

    // 转化图片 URL 为 fileList 的格式
    fileList.value = hotel.picture_url.map((url, index) => ({
      name: `图片${index + 1}`,
      url: url,
    }));

    MapContainerRef.value.updateCenter(hotel.locations)

    // 初始化省市区选项
    handleProvinceChange(hotel.province);
    handleCityChange(hotel.city);

    form.value.city=hotel.city
    form.value.district=hotel.district
  }
})
const rules = ref({
  name: [{ required: true, message: '请填写酒店名', trigger: 'blur' }],
  introduction: [{ required: true, message: '请填写酒店介绍', trigger: 'blur' }],
  province: [{ required: true, message: '请选择省份', trigger: 'change' }],
  city: [{ required: true, message: '请选择城市', trigger: 'change' }],
  district: [{ required: true, message: '请选择区县', trigger: 'change' }],
  address: [{ required: true, message: '请填写具体地址', trigger: 'blur' }],
  picture_url: [{ type: 'array', min: 1, message: '请至少上传一张酒店图片', trigger: 'change' }],

});


let allAddress=ref('');

const deleteHotelPictureIds = ref([]);


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

  formRef.value.validate((valid) => {
    if (valid) {
      hotelAPI.updateHotel(form.value)
          // .then(() => router.push('/hotelManagement'));
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

const handleHotelPictureRemove = (fileName) => {
  hotel.deletePicture(fileName);
  const index = deleteHotelPictureIds.value.findIndex(item => item.fileName === fileName);
  form.value.picture_url.splice(index,1);
}

const mapLocation = (location) => {
  MapContainerRef.value.handleInput(location);
}
const updateLocations = (locations) => {
  form.value.locations = locations;
}
</script>

<template>
<div class="form">

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
                   :on-remove="(file)=>handleHotelPictureRemove(file.name)"
                   :file-list="fileList"></el-upload>
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
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </div>
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

.map{
  width: 100%;
  height: 300px;
}
.address-row{
  display: flex;
  justify-content: space-between;
}
.address-row .el-select{
  width: 130px;
}
</style>