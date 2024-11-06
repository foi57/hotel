import {createStore} from "vuex";

const optionStore = createStore({
    state: {
        options: null
    },
    mutations: {
        setOptions(state, options) {
            state.options = options;
        }
    },
    actions: {
        setOptions(context, options) {
            context.commit('setOptions', options);
        }
    },
    getters: {
        getOptions: (state) => state.options
    }

})

export default optionStore