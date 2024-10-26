import axios from 'axios'
import router from "./router/index.js";

const request = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json'
    }
})
request.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config;
}, error => {
    return  Promise.reject(error)
})
request.interceptors.response.use(response => {
    return response;
},error => {
    if (error.response && error.response.status === 401) {
        localStorage.removeItem('token')
        router.push('/login')
    }
    return Promise.reject(error)
})
export default request