import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const state = {
    isAddStockModalEnabled: false,
};

const store = new Vuex.Store({
    state,
    getters: {
        isAddStockModalEnabled: state => {
            return state.isAddStockModalEnabled
        }
    },
    actions: {
        showAddStockModal(context) {
            context.commit('showAddStockModal')
        },
        hideAddStockModel(context) {
            context.commit('hideAddStockModel')
        }
    },
    mutations: {
        showAddStockModal(state) {
            state.isAddStockModalEnabled = true;
        },
        hideAddStockModel(state) {
            state.isAddStockModalEnabled = false;
        }
    }
});

export default store;