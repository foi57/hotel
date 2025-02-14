<script setup>
import {ref, onMounted} from "vue";
import hotel from "../api/hotel.js";
import appraise from "../api/appraise.js";
import {ElMessage} from "element-plus";
import {useRoute} from 'vue-router';

const rating = ref(0);
const comment = ref('');
const images = ref([]);
const route = useRoute();
const roomId = route.params.roomId;
const userId = route.params.userId;
const HandlePictureUploadSuccess = (response, file) => {
  console.log('upPicture', file)
  images.value.push(response.fileUrl);
}

const handlePictureRemove = async (file) => {
  const urlIndex = images.value.findIndex(url => file.response.fileUrl === url);
  await hotel.deletePicture(file.response.id)
  if (urlIndex !== -1) {
    images.value.splice(urlIndex, 1);
  }
}
const submitAppraisal = () => {
  const form = {
    userId: userId,
    roomId: roomId,
    rating: rating.value,
    comment: comment.value,
    pictureURL: images.value,
  };
  console.log(form)
  // 发送评价请求
  appraise.updateAppraise(form)
      .then(() => {
        // 评价成功，执行相应操作
        ElMessage.success('评价成功');
      })
      .catch((error) => {
        // 评价失败，处理错误
        ElMessage.error('评价失败');
      })
}
</script>

<template>
  <el-form>
    <el-form-item label="评分">
      <el-rate v-model="rating" :max="5" show-score></el-rate>
    </el-form-item>
    <el-form-item label="评论">
      <el-input v-model="comment" placeholder="请输入评论内容" type="textarea"></el-input>
    </el-form-item>
    <el-form-item label="图片">
      <el-upload :action="'http://localhost:8080/api/PictureUpload'" :on-remove="handlePictureRemove"
                 :on-success="HandlePictureUploadSuccess" list-type="picture-card">
      </el-upload>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitAppraisal">提交评价</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>

</style>