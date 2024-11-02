<script setup>
import { ref, computed,watch} from "vue";
import SearchBox from "../components/SearchBox.vue";
import {useStore,mapGetters} from "vuex";
import Heard from "../components/heard.vue";
import ViewHotel from "../components/viewHotel.vue";
const store = useStore();
const Pages = computed(() => {
  const Pages = store.getters.getPages
  console.log('pages',Pages)
  return store.getters.getPages
})
const SearchBoxRef = ref(null);

watch(() => Pages.value.currentPage,
    (newPage) => {
      SearchBoxRef.value.handleSearch(newPage)
    })
const handleSearch = (page) => {
  SearchBoxRef.value.handleSearch(page)
}
</script>

<template>
  <heard></heard>
<SearchBox ref="SearchBoxRef"></SearchBox>
  <div class="Context">
    <div class="hotel">
      <view-hotel></view-hotel>
      <p>当前页: {{ Pages.currentPage }} / 总页数: {{ Pages.totalPage }}</p>

    </div>
    <el-pagination
        v-model="Pages.currentPage"
        :page-size="10"
        :total="Pages.totalPage * 10"
        layout="prev,pager,next"
        @current-change="handleSearch"></el-pagination>
  </div>
</template>

<style scoped>
.Context{
  width: 1300px;
  box-shadow: 0 0 5px #888888;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>