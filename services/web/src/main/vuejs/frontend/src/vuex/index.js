import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        isAddStockModalEnabled: false,
        isDeleteStockModalEnabled: false,
        isEditStockModelEnabled: false,

        isAddOptionModalEnabled: false,
        isDeleteOptionModalEnabled: false,

        isAddDividendModalEnabled: false,
        isDeleteDividendModalEnabled: false,

        isSyntheticModalEnabled: false,

        isAddErrorEnabled: false
    },
    actions: {
        showAddStockModal(context) {
            context.commit('showAddStockModal')
        },
        showDeleteStockModal(context) {
            context.commit('showDeleteStockModal')
        },
        showEditStockModal(context) {
            context.commit('showEditStockModal')
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
        showSyntheticShareModal(context) {
            context.commit('showSyntheticShareModal');
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
        showEditStockModal(state) {
            state.isEditStockModelEnabled = true;
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
        showSyntheticShareModal(state) {
            state.isSyntheticModalEnabled = true;
        },

        hideModalWithoutRefresh(state) {
            state.isAddStockModalEnabled = false;
            state.isAddOptionModalEnabled = false;
            state.isAddDividendModalEnabled = false;
            state.isDeleteStockModalEnabled = false;
            state.isDeleteOptionModalEnabled = false;
            state.isDeleteDividendModalEnabled = false;
            state.isEditStockModelEnabled = false;
            state.isSyntheticModalEnabled = false;
            state.isAddErrorEnabled = false;
        },

        hideModalWithRefresh(state) {
            state.isAddStockModalEnabled = false;
            state.isAddOptionModalEnabled = false;
            state.isAddDividendModalEnabled = false;
            state.isDeleteStockModalEnabled = false;
            state.isDeleteOptionModalEnabled = false;
            state.isDeleteDividendModalEnabled = false;
            state.isEditStockModelEnabled = false;
            state.isSyntheticModalEnabled = false;
            state.isAddErrorEnabled = false;
        },

        hideModalWithError(state) {
            state.isAddStockModalEnabled = false;
            state.isAddOptionModalEnabled = false;
            state.isAddDividendModalEnabled = false;
            state.isDeleteStockModalEnabled = false;
            state.isDeleteOptionModalEnabled = false;
            state.isDeleteOptionModalEnabled = false;
            state.isDeleteDividendModalEnabled = false;
            state.isEditStockModelEnabled = false;
            state.isSyntheticModalEnabled = false;
            state.isAddErrorEnabled = true;
        },

        hideErrorModal(state) {
            state.isAddErrorEnabled = false;
        }
    }
});

export default store;
