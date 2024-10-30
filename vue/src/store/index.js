// store/index.js
import createPersistedState from "vuex-persistedstate"
import {createStore} from "vuex";
const store = createStore({
    state: {
        hotel: null, // 用于存储酒店信息
        book: null
    },
    mutations: {
        setHotel(state, hotel) {
            state.hotel = hotel; // 设置酒店信息
        },
        setBook(state, book) {
            state.book = book
        }
    },
    actions: {
        updateHotel({ commit }, hotel) {
            console.log('Updating hotel:', hotel);
            commit('setHotel', hotel); // 更新酒店信息
        },
        updateBook({ commit }, book) {
            console.log('Updating book:', book);
            commit('setBook', book)
        }
    },
    getters: {
        getHotel: (state) => state.hotel, // 获取酒店信息
        getBook: (state) => state.book
    },
    plugins: [createPersistedState({})],
});

export default store;
