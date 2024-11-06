<script setup>
import {onMounted,ref} from "vue";
import Heard from "../components/heard.vue";
import LeftSidebar from "../components/LeftSidebar.vue";
import hotel from "../api/hotel.js";
import ViewHotel from "../components/viewHotel.vue";
import {useStore} from "vuex";
const page = ref(1);
const pageSize = ref(20);
const store = useStore()
let MyHotel =ref(null);
onMounted(() => {
  MyHotel=hotel.selectHotelByUserId(page.value,pageSize.value)
      .then(response => {
        console.log(response.data)
        MyHotel.value = response.data
        store.dispatch("updateHotel",response.data)
      })
})
</script>

<template>
<heard></heard>
  <LeftSidebar></LeftSidebar>
  <div class="Content">
<view-hotel></view-hotel>
  </div>
</template>

<style scoped>
.Content{
  width: 100%;
  height: 100%;
  margin-left: 250px;
}

</style>