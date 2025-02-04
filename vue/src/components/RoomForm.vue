<!-- components/RoomForm.vue -->
<template>
  <div class="room-form">
    <el-form ref="formRef" :model="room" :rules="rule">
    <el-form-item label="房间名称" prop="room_name">
      <el-input v-model="room.room_name"></el-input>
    </el-form-item>
    <el-form-item label="房间数量" prop="room_count">
      <el-input-number v-model="room.room_count"></el-input-number>
    </el-form-item>
    <el-form-item label="房间图片">
      <el-upload list-type="picture-card" multiple accept="image/*"
                 :before-upload="handlePictureUpdate" :limit="1" :on-exceed="handleExceed"
                 :action="'http://localhost:8080/api/PictureUpload'"
                 :on-success="(response, file) => onRoomPictureUploadSuccess(response, file)"
                 :on-remove="(file) => onRoomPictureRemove(file)"
                  :file-list="fileList">
      </el-upload>
    </el-form-item>
    <el-form-item label="床类型" prop="bed_type">
      <el-select v-model="room.bed_type">
        <el-option v-for="bed in beds" :key="bed" :label="bed" :value="bed"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="床数量">
      <el-input-number v-model="room.bed_count"></el-input-number>
    </el-form-item>
    <h4>设施</h4>
    <div class="facilities">
      <el-checkbox v-for="(facility, index) in facilityList" :key="index" v-model="room.facilities[index]">
        {{ facility }}
      </el-checkbox>
    </div>
    <el-form-item label="价格" prop="price">
      <el-input-number v-model="room.price"></el-input-number>
    </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import {ElMessage} from 'element-plus';
import {useStore} from "vuex";
import hotel from "../api/hotel.js";
import {defineExpose, reactive, ref} from 'vue';

const store = useStore()
const room = reactive({...store.getters.getRoom}); // 使用 reactive 包裹
const fileList = ref(
    room.room_picture_url
        ? [
          {
            name: '房间图片', // 图片名称
            url: room.room_picture_url, // 图片 URL
            uid: 0, // 唯一标识
          },
        ]
        : []
);
console.log(room)
const beds =['单人床','双人床'];


const facilityList = [
  "无线网", "有线网", "毛巾", "拖鞋", "免费洗浴用品", "空调", "电视", "厨房"
];

const handlePictureUpdate = (file) => {
  const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isJPG) ElMessage.error('上传图片只能是 JPG 或 PNG 格式!');
  if (!isLt2M) ElMessage.error('上传图片大小不能超过 2MB!');
  return isJPG && isLt2M;
};

const handleExceed = () => {
  ElMessage.error('上传图片数量达到上限');
};

const onRoomPictureUploadSuccess = (response, file) => {
  room.room_picture_url.push(response.fileUrl)
};

const onRoomPictureRemove = async (file) => {
  const urlIndex = room.room_picture_url.findIndex((url) => url === file.url)
  hotel.deletePicture(file.id)
  room.room_picture_url.splice(urlIndex, 1)
};
const rule = {
  room_name: [{required: true, message: '请填写房间名称', trigger: ['blur']}],
  room_count: [{ required: true, type: 'number', message: '请填写房间数量', trigger: 'change' }],
  price: [{ required: true, type: 'number', min: 1, message: '请填写房间价格', trigger: 'change' }],
  bed_type: [{ required: true, message: '请填写床型', trigger: 'change' }],
};

defineExpose({
  room
});
</script>

<style scoped>
.facilities {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

</style>
