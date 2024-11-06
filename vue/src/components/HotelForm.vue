<!-- components/HotelForm.vue -->
<template>
  <el-form class="form" :rules="rules" ref="formRef" :model="form">
    <el-form-item label="酒店名" prop="name">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="酒店介绍" prop="introduction">
      <el-input type="textarea" v-model="form.introduction"></el-input>
    </el-form-item>
    <el-form-item label="酒店图片" prop="picture_url">
      <el-upload list-type="picture-card" accept="image/*" :before-upload="handlePictureUpdate" :limit="5"
                 :on-exceed="handleExceed" :action="'http://localhost:8080/api/PictureUpload'"
                 :on-success="HotelHandlePictureUploadSuccess" :on-remove="handleHotelPictureRemove"></el-upload>
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
        <el-select v-model="form.district">
          <el-option v-for="district in districts" :key="district" :label="district" :value="district"></el-option>
        </el-select>
      </el-form-item>
    </div>
    <el-form-item label="具体地址" prop="address">
      <el-input v-model="form.address" type="textarea" @input="mapLocation"></el-input>
    </el-form-item>

    <h4>住房信息</h4>
    <RoomForm
        v-for="(room, index) in form.rooms"
        :key="index"
        :room="room"
        :roomIndex="index"
        :beds="beds"
        @update:room_picture_url="updateRoomPictureUrl(index, $event)"
        @removeRoomPicture="removeRoomPicture(index)"
    />
    <el-button @click="addRoom">增加房间</el-button>
    <el-button @click="removeRoom">删除房间</el-button>
    <br>
    <el-button type="primary" @click="onSubmit">提交</el-button>
  </el-form>
</template>

<script setup>
import { ref } from 'vue';
import RoomForm from './RoomForm.vue';
import { ElMessage, ElMessageBox } from "element-plus";
import locationData from '../assets/locationData.json';

const formRef = ref(null);
const form = ref({
  name: '',
  introduction: '',
  picture_url: [],
  province: '',
  city: '',
  district: '',
  address: '',
  rooms: []
});
const rules = { /* ...省略其他规则，保持和你之前一致... */ };

const provinces = ref(locationData.map(province => province.province));
const cities = ref([]);
const districts = ref([]);
const beds = ['单人床', '双人床'];

// 房间操作
const addRoom = () => {
  form.value.rooms.push({ room_name: '', room_count: 1, bed_type: '', bed_count: 1, price: 1, facilities: Array(8).fill(false) });
};
const removeRoom = () => form.value.rooms.pop();

// 房间图片上传
const updateRoomPictureUrl = (index, url) => form.value.rooms[index].room_picture_url = url;
const removeRoomPicture = (index) => form.value.rooms[index].room_picture_url = '';

// 省市区联动
const handleProvinceChange = () => { /* ...保持不变... */ };
const handleCityChange = () => { /* ...保持不变... */ };

// 提交表单
const onSubmit = () => { /* ...保持不变... */ };
</script>

<style scoped>
/* 保持样式一致 */
</style>
