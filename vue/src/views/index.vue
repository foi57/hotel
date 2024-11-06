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
  .card {
   width: 100%;
    display: flex;
    justify-content: space-around;
    padding: 0;
  }
.card div{
  width: 350px;
  box-shadow: #1a1a1a 0 0 5px;
  margin: 10px;
  transition: all 0.5s;
}
.card div:hover{
  transform: scale(1.25);
}
.card div img{
  max-height:100%;
  max-width: 300px;

}

.card div p{
  margin: 2px 2px;
}
.card div p:last-child span:first-child{
  color: red;

}
.card div p:last-child span:last-child{
  font-size: 22px;
}
.card div p:nth-child(4) span:nth-child(1){
  background-color: #535bf2;
  color: #fff;
  padding: 2px 4px;
  border-radius: 4px;
}


</style>