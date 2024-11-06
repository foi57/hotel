<!-- components/RoomForm.vue -->
<template>
  <div class="room-form">
    <h5>房间 {{ roomIndex + 1 }}</h5>
    <el-form-item label="房间名称" :prop="`rooms[${roomIndex}].room_name`">
      <el-input v-model="room.room_name"></el-input>
    </el-form-item>
    <el-form-item label="房间数量" :prop="`rooms[${roomIndex}].room_count`">
      <el-input-number v-model="room.room_count"></el-input-number>
    </el-form-item>
    <el-form-item label="房间图片">
      <el-upload list-type="picture-card" multiple accept="image/*"
                 :before-upload="handlePictureUpdate" :limit="1" :on-exceed="handleExceed"
                 :action="'http://localhost:8080/api/PictureUpload'"
                 :on-success="(response, file) => onRoomPictureUploadSuccess(response, file)"
                 :on-remove="() => onRoomPictureRemove()"></el-upload>
    </el-form-item>
    <el-form-item label="床类型" :prop="`rooms[${roomIndex}].bed_type`">
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
    <el-form-item label="价格" :prop="`rooms[${roomIndex}].price`">
      <el-input-number v-model="room.price"></el-input-number>
    </el-form-item>
  </div>
</template>

<script setup>
import { ref, toRefs } from 'vue';
import { ElMessage } from 'element-plus';

const props = defineProps({
  room: Object,
  roomIndex: Number,
  beds: Array
});

const emit = defineEmits(['update:room_picture_url', 'removeRoomPicture']);

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
  emit('update:room_picture_url', response.fileUrl);
};

const onRoomPictureRemove = () => {
  emit('removeRoomPicture');
};
</script>

<style scoped>
.facilities {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}
</style>
