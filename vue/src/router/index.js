import { createRouter, createWebHistory } from 'vue-router';

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
        component: () => import("../views/AddHotel.vue")
    },
    {
        path: "/enroll",
        name: "Enroll",
        component: () => import("../views/Enroll.vue")
    },
    {
        path: "/addHotel",
        name: "AddHotel",
        component: () => import("../views/AddHotel.vue")
    },
    {
        path: "/hotelInnerPages",
        name: "hotelInnerPages",
        component: () => import("../views/hotelInnerPages.vue")
    },
    {
        path: "/booking",
        name: "booking",
        component: () => import("../views/booking.vue")
    }
];

const index = createRouter({
    history: createWebHistory('/'),
    routes
});

export default index;
