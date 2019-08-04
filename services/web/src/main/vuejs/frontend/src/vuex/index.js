import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        isAddStockModalEnabled: false,
        isDeleteStockModalEnabled: false,
        isAddOptionModalEnabled: false,
        isDeleteOptionModalEnabled: false,
        isAddDividendModalEnabled: false,
        isDeleteDividendModalEnabled: false,
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
        showDeleteOptionModal(context) {
            context.commit('showDeleteOptionModal')
        },
        showAddDividendModal(context) {
            context.commit('showAddDividendModal');
        },
        showDeleteDividendModal(context) {
            context.commit('showDeleteDividendModal');
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
        showDeleteStockModal(state) {
            state.isDeleteStockModalEnabled = true;
        },
        showAddOptionModal(state) {
            state.isAddOptionModalEnabled = true;
        },
        showDeleteOptionModal(state) {
            state.isDeleteOptionModalEnabled = true;
        },
        showAddDividendModal(state) {
            state.isAddDividendModalEnabled = true;
        },
        showDeleteDividendModal(state) {
            state.isDeleteDividendModalEnabled = true;
        },

        hideModalWithoutRefresh(state) {
            state.isAddStockModalEnabled = false;
            state.isAddOptionModalEnabled = false;
            state.isAddDividendModalEnabled = false;
            state.isDeleteStockModalEnabled = false;
            state.isDeleteOptionModalEnabled = false;
            state.isDeleteDividendModalEnabled = false;
            state.isAddErrorEnabled = false;
        },

        hideModalWithRefresh(state) {
            state.isAddStockModalEnabled = false;
            state.isAddOptionModalEnabled = false;
            state.isAddDividendModalEnabled = false;
            state.isDeleteStockModalEnabled = false;
            state.isDeleteOptionModalEnabled = false;
            state.isDeleteDividendModalEnabled = false;
            state.isAddErrorEnabled = false;
        },

        hideModalWithError(state) {
            state.isAddStockModalEnabled = false;
            state.isAddOptionModalEnabled = false;
            state.isAddDividendModalEnabled = false;
            state.isDeleteStockModalEnabled = false;
            state.isDeleteOptionModalEnabled = false;
            state.isDeleteOptionModalEnabled = false;
            state.isAddErrorEnabled = true;
        },

        hideErrorModal(state) {
            state.isAddErrorEnabled = false;
        }
    }
});

export default store;