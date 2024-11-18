<!-- index.vue -->
<template>
  <heard></heard>
  <div class="Context">
    <SearchBox></SearchBox>
    <h1>特惠酒店</h1>
   <view-hotel></view-hotel>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import heard from "../components/heard.vue";
import hotel from "../api/hotel.js";
import SearchBox from "../components/SearchBox.vue";
import {useStore} from "vuex";
import ViewHotel from "../components/viewHotel.vue";
const store = useStore()
let hotelList = ref([]);
onMounted(async () => {
  console.log(1)
  hotel.selectHotel()
      .then(response => {
        hotelList.value = response.data;
        store.dispatch("updateHotel",hotelList.value);
        console.log(hotelList.value)
      })
  await store.dispatch("updateSelectHotel", "/hotelInnerPages")
})

</script>

<style scoped>
  .Context {
    width: 1300px;
  }

</style>