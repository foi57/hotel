import { createApp } from 'vue'
import './style.css'
// import ElementPlus from 'element-plus'
import App from './App.vue'
import router from './router'
const app = createApp(App)
app.use(router).mount('#app')
// app.use(ElementPlus, { size: 'small', zIndex: 3000 })

