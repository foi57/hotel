import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import store  from "./store";
import VueAwesomeSwiper from 'vue-awesome-swiper';
import 'swiper/css';
const app = createApp(App)
app.use(router)
app.use(store)
app.use(VueAwesomeSwiper)
app.mount('#app')

