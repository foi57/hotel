import { createRouter, createWebHistory } from 'vue-router';
import {ElMessage} from "element-plus";


const routes = [
    {
        path: '/',
        name: 'index',
        component: () => import('../views/index.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: "/addHotel",
        name: "AddHotel",
        component: () => import("../views/AddHotel.vue"),
        meta: { requiresAuth: true }
    },
    {
        path: "/enroll",
        name: "Enroll",
        component: () => import("../views/Enroll.vue")
    },
    {
        path: "/hotelInnerPages",
        name: "hotelInnerPages",
        component: () => import("../views/hotelInnerPages.vue")
    },
    {
        path: "/booking",
        name: "booking",
        component: () => import("../views/booking.vue"),
        meta: { requiresAuth: true }
    },
    {
        path: "/bookInfo",
        name: "bookInfo",
        component: () => import("../views/bookInfo.vue"),
        meta: {requiresAuth: true}
    },
    {
        path: "/selectHotel",
        name: "selectHotel",
        component: () => import("../views/selectHotel.vue")
    }
];

const index = createRouter({
    history: createWebHistory('/'),
    routes,
});


index.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!localStorage.getItem('token')) {
            next({ path: '/login' });
            ElMessage.error("请先登录");
        } else {
            next();
        }
    } else {
        next();
    }
});

export default index;
