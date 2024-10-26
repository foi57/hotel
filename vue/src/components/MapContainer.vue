<script setup>
import {onMounted, onUnmounted, ref,} from "vue";
import AMapLoader from "@amap/amap-jsapi-loader";
const geocoder = ref(null);
const marker =ref(null);
let map = null;
let locations=ref([]);
const emit = defineEmits();
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
        const loc = result.geocodes[0].location;
        locations.value = loc;
        map.setCenter(loc);
        marker.value.setPosition(loc);
        emit('update:locations', loc); // Emit to parent
      } else {
        console.log('Failed to get location');
      }
    });
  }
};
defineExpose({
  handleInput,
  locations
});

onUnmounted(() => {
  map?.destroy();
});
</script>

<template>
  <div id="container"></div>
</template>

<style scoped>
#container {
  width: 100%;
  height: 800px;
}
#container {
  width: 100%;
  height: 300px;
}
</style>
