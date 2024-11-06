// store/index.js
import createPersistedState from "vuex-persistedstate"
import {createStore} from "vuex";
const store = createStore({
    state: {
        hotel: null, // 用于存储酒店信息
        book: null,
        pages: 0,
        searchBox: {
            destination: '',
            checkInDate: '',
            checkOutDate: '',
        },
    },
    mutations: {
        setHotel(state, hotel) {
            state.hotel = hotel; // 设置酒店信息
        },
        setBook(state, book) {
            state.book = book
        },
        setPages(state, pages) {
            state.pages = pages
        },
        setSearchBox(state, searchBox) {
            state.searchBox = searchBox
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
        },
        updatePages({ commit }, pages) {
            console.log('Updating pages:', pages);
            commit('setPages', pages)
        },
        updateSearchBox({ commit }, searchBox) {
            console.log('Updating searchBox:', searchBox);
            commit('setSearchBox', searchBox)
        }
    },
    getters: {
        getHotel: (state) => state.hotel, // 获取酒店信息
        getBook: (state) => state.book,
        getPages: (state) => state.pages,
        getSearchBox: (state) => state.searchBox
    },
    plugins: [createPersistedState({})],
});

export default store;
