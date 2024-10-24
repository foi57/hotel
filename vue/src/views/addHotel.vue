<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import AMapLoader from '@amap/amap-jsapi-loader';
import locationData from '../assets/locationData.json'
import {ElMessage} from "element-plus";
import Heard from "../components/heard.vue";
let map = null;
const searchInput = ref('');
const geocoder = ref(null);
const marker =ref(null);
const provinces = ref([]); // 省份列表
const cities = ref([]); // 城市列表
const districts = ref([]); // 区县列表
const form = ref({
  name:'',
  province: '',
  city: '',
  district: '',
  address: ''
})
const roomName=ref('');
let allAddress=ref('');
const beds =['单人床','双人床'];
const rooms = ref([{
  roomName: '',
  bedType: '',
  bedCount: 1,
  facilities: []
}]);

// 增加房间
const addRoom = () => {
  rooms.value.push({
    roomName: '',
    bedType: '',
    bedCount: 1,
    facilities: []
  });
};


onMounted(() => {
  window._AMapSecurityConfig = {
    securityJsCode: '29c04f7c3a37e8f1e6539352e52c9d96',
  };

  AMapLoader.load({
    key: '91fdc2dff3ba5ddd5e27fe3ee98932af',
    version: '2.0',
    plugins: ['AMap.Geocoder'],
  })
      .then((AMap) => {
        map = new AMap.Map('container', {
          viewMode: '3D',
          zoom: 11,
          center: [116.397428, 39.90923],
        });

        geocoder.value = new AMap.Geocoder({
          city: '全国', // 设置搜索范围
        });
        marker.value = new AMap.Marker({
          map:map,
          position:[116.397428,39.90923]
        });
        provinces.value = locationData.map(province=>province.province);
      })
      .catch((e) => {
        console.log(e);
      });
});

onUnmounted(() => {
  map?.destroy();
});

const handleInput = (location) => {
  if (geocoder.value) {
    geocoder.value.getLocation(location, (status, result) => {
      if (status === 'complete' && result.geocodes.length) {
        const location = result.geocodes[0].location;
        console.log('Coordinates:', location);
        map.setCenter(location);
        marker.value.setPosition(location)
        // 将坐标存入数据库
      } else {
        console.log('Failed to get location');
      }
    });
  }
};


const handleProvinceChange = (value) =>{
  allAddress='';
  const selectedProvince = locationData.find(province => province.province === value);
  cities.value=selectedProvince.citys.map(city =>city.city)
  form.value.city='';
  form.value.district='';
  districts.value=[];
  allAddress=allAddress+form.value.province;
  handleInput(allAddress)
}
const handleCityChange = (value) => {
  allAddress='';
  const selectedProvince = locationData.find(province => province.province === form.value.province);
  const selectedCity = selectedProvince.citys.find(city => city.city === value);
  districts.value = selectedCity.areas.map(area => area.area);
  form.value.district = '';
  allAddress=form.value.province+form.value.city;
  handleInput(allAddress);
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
</script>

<template>
  <div class="Context">
  <heard></heard>
  <el-form class="form">
    <el-form-item label="酒店名">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="酒店图片">
      <el-upload list-type="picture-card" :auto-upload="false" accept="image/*" :before-upload="handlePictureUpdate" :limit="5" :on-exceed="handleExceed"></el-upload>
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
      <el-select v-model="form.district" @change="handleInput(form.district)">
        <el-option v-for="district in districts" :key="district" :label="district" :value="district"></el-option>
      </el-select>
    </el-form-item>
    </div>
    <el-form-item label="具体地址">
      <el-input v-model="form.address" type="textarea" @input="handleInput(allAddress=form.province+form.district+form.address)"></el-input>
    </el-form-item>
    <div id="container"></div>
    <h4>住房信息</h4>
    <div class="root">
    <el-form-item label="房间名称">
      <el-input v-model="roomName"></el-input>
    </el-form-item>
    <el-form-item label="房间数量">
      <el-input-number></el-input-number>
    </el-form-item>
    <el-form-item label="房间图图片">
      <el-upload list-type="picture-card" :auto-upload="false" accept="image/*" :before-upload="handlePictureUpdate" :limit="1" :on-exceed="handleExceed"></el-upload>
    </el-form-item>
      <div class="bed">
     <el-form-item label="床类型">
       <el-select>
         <el-option v-for="bed in beds" :key="bed" :label="bed" :value="bed"></el-option>
       </el-select>
     </el-form-item>
     <el-form-item label="床数量">
       <el-input-number></el-input-number>
     </el-form-item>
        </div>
      <h4>设施</h4>
      <div class="function" v-for="(room,index) in rooms" :key="index">
        <h5>房间 {{ index + 1 }}</h5>
     <el-form-item>
       <el-checkbox>无线网</el-checkbox>
     </el-form-item>
        <el-form-item>
          <el-checkbox>有线网</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-checkbox>毛巾</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-checkbox>拖鞋</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-checkbox>免费洗浴用品</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-checkbox>空调</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-checkbox>电视</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-checkbox>厨房</el-checkbox>
        </el-form-item>
      </div>
    </div>
    <el-button @click="addRoom">增加房间</el-button>
  </el-form>
  </div>
</template>

<style scoped>
#container {
  width: 100%;
  height: 300px;
}
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