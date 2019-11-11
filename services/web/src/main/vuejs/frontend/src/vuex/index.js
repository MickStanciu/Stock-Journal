import Vue from 'vue'
import Vuex from 'vuex'
import { StockModule } from "./stock";

Vue.use(Vuex);


const store = new Vuex.Store({
    modules: {
        stock: StockModule
    },

    state: {
        isAddOptionModalEnabled: false,
        isEditOptionModelEnabled: false,
        isDeleteOptionModalEnabled: false,

        isAddDividendModalEnabled: false,
        isEditDividendModalEnabled: false,
        isDeleteDividendModalEnabled: false,

        isSyntheticModalEnabled: false,

        isAddErrorEnabled: false
    },

    actions: {
        showAddOptionModal(context) {
            context.commit('showAddOptionModal')
        },
        showEditOptionModal(context) {
            context.commit("showEditOptionModal");
        },
        showDeleteOptionModal(context) {
            context.commit('showDeleteOptionModal')
        },

        showAddDividendModal(context) {
            context.commit('showAddDividendModal');
        },
        showEditDividendModal(context) {
            context.commit('showEditDividendModal');
        },
        showDeleteDividendModal(context) {
            context.commit('showDeleteDividendModal');
        },

        showSyntheticShareModal(context) {
            context.commit('showSyntheticShareModal');
        },

        showErrorModal(context) {
            context.commit('showErrorModal')
        },
        hideErrorModal(context) {
            context.commit('hideErrorModal')
        },
        refreshData(context) {
            context.commit('refreshData')
        },
        hideModalWithoutRefresh(context) {
            context.commit('hideModalWithoutRefresh')
        },

    },

    mutations: {
        showAddOptionModal(state) {
            state.isAddOptionModalEnabled = true;
        },
        showDeleteOptionModal(state) {
            state.isDeleteOptionModalEnabled = true;
        },
        showEditOptionModal(state) {
            state.isEditOptionModelEnabled = true;
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
            state.isEditStockModelEnabled = false;
            state.isDeleteStockModalEnabled = false;

            state.isAddOptionModalEnabled = false;
            state.isEditOptionModelEnabled = false;
            state.isDeleteOptionModalEnabled = false;

            state.isAddDividendModalEnabled = false;
            state.isDeleteDividendModalEnabled = false;

            state.isSyntheticModalEnabled = false;
            state.isAddErrorEnabled = false;
        },

        hideModalWithRefresh(state) {
            state.isAddStockModalEnabled = false;
            state.isEditStockModelEnabled = false;
            state.isDeleteStockModalEnabled = false;

            state.isAddOptionModalEnabled = false;
            state.isEditOptionModelEnabled = false;
            state.isDeleteOptionModalEnabled = false;

            state.isAddDividendModalEnabled = false;
            state.isDeleteDividendModalEnabled = false;

            state.isSyntheticModalEnabled = false;
            state.isAddErrorEnabled = false;
        },

        refreshData() {
            console.log("mutation refresh data")
            //do nothing
        },

        showErrorModal(state) {
            state.isAddErrorEnabled = true;
        },

        hideErrorModal(state) {
            state.isAddErrorEnabled = false;
        }
    }
});

export default store;
