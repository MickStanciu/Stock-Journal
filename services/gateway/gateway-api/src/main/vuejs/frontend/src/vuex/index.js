import Vue from 'vue'
import Vuex from 'vuex'
import { StockModule } from "./stock";
import { OptionModule } from "./option";
import { DividendModule } from "./dividend";

Vue.use(Vuex);


const store = new Vuex.Store({
    modules: {
        stock: StockModule,
        option: OptionModule,
        dividend: DividendModule
    },

    state: {
        isSyntheticModalEnabled: false,
        isAddErrorEnabled: false
    },

    actions: {
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
        showSyntheticShareModal(state) {
            state.isSyntheticModalEnabled = true;
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
