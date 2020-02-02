import Vue from 'vue'
import Vuex from 'vuex'
import {StockModule} from "./stock";
import {OptionModule} from "./option";
import {DividendModule} from "./dividend";

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

        hideSyntheticShareModal(context) {
            context.commit('hideSyntheticShareModal');
        },

        showErrorModal(context) {
            context.commit('showErrorModal')
        },
        hideErrorModal(context) {
            context.commit('hideErrorModal')
        },
        refreshData(context) {
            context.commit('refreshData')
        }
    },

    mutations: {
        showSyntheticShareModal(state) {
            state.isSyntheticModalEnabled = true;
        },

        hideSyntheticShareModal(state) {
            state.isSyntheticModalEnabled = false;
        },

        refreshData() {
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
