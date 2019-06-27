import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        isAddStockModalEnabled: false,
        isDeleteStockModalEnabled: false,
        isAddOptionModalEnabled: false,
        isAddErrorEnabled: false
    },
    actions: {
        showAddStockModal(context) {
            context.commit('showAddStockModal')
        },
        showDeleteStockModal(context) {
            context.commit('showDeleteStockModal')
        },
        showAddOptionModal(context) {
            context.commit('showAddOptionModal')
        },

        hideAddStockModal(context) {
            context.commit('hideModalWithoutRefresh')
        },
        hideAddOptionModal(context) {
            context.commit('hideModalWithoutRefresh')
        },
        hideModalWithError(context) {
            context.commit('hideModalWithError')
        },
        hideModalWithRefresh(context) {
            context.commit('hideModalWithRefresh')
        },
        hideModalWithoutRefresh(context) {
            context.commit('hideModalWithoutRefresh')
        },
        hideErrorModal(context) {
            context.commit('hideErrorModal')
        }
    },
    mutations: {
        showAddStockModal(state) {
            state.isAddStockModalEnabled = true;
        },
        showAddOptionModal(state) {
            state.isAddOptionModalEnabled = true;
        },
        showDeleteStockModal(state) {
            state.isDeleteStockModalEnabled = true;
        },
        hideModalWithoutRefresh(state) {
            state.isAddStockModalEnabled = false;
            state.isAddOptionModalEnabled = false;
            state.isAddErrorEnabled = false;
            state.isDeleteStockModalEnabled = false;
        },
        hideModalWithRefresh(state) {
            state.isAddStockModalEnabled = false;
            state.isAddOptionModalEnabled = false;
            state.isAddErrorEnabled = false;
            state.isDeleteStockModalEnabled = false;
        },
        hideModalWithError(state) {
            state.isAddStockModalEnabled = false;
            state.isAddOptionModalEnabled = false;
            state.isDeleteStockModalEnabled = false;
            state.isAddErrorEnabled = true;
        },
        hideErrorModal(state) {
            state.isAddErrorEnabled = false;
        }
    }
});

export default store;