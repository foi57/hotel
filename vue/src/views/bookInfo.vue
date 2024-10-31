<script setup>
import Heard from "../components/heard.vue";
import book from "../api/book.js";
import { ref, onMounted } from "vue";

const orders = ref([]); // 用于存储订单信息

onMounted(async () => {
  try {
    const response = await book.showBook();
    orders.value = response.data; // 假设 response 是订单数组
  } catch (error) {
    console.error("Error fetching orders:", error);
  }
});
</script>

<template>
  <heard></heard>
  <div class="order-list">
    <h2>订单信息</h2>
    <div v-if="orders.length === 0">暂无订单信息</div>
    <div v-else>
      <div v-for="order in orders" :key="order.id" class="order-item">
        <h3>{{ order.name }}</h3>
        <p>房间名: {{ order.room_name }}</p>
        <p>入住时间: {{ order.timeStart }}</p>
        <p>离店时间: {{ order.timeEnd }}</p>
        <p>预计到店时间: {{ order.arrivalTime }}</p>
        <p>房间数量: {{ order.room_count }}</p>
        <p>价格: {{ order.price }} 元</p>
        <p>订单时间: {{ order.bookTime }}</p>
        <hr />
      </div>
    </div>
  </div>
</template>

<style scoped>
.order-list {
  padding: 20px;
}
.order-item {
  margin-bottom: 20px;
  border: 1px solid #ccc;
  padding: 10px;
}
</style>
