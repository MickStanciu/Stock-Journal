import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        isAddStockModalEnabled: false,
        isAddErrorEnabled: false,
    },
    // getters: {
    //     isAddStockModalEnabled: state => {
    //         return state.isAddStockModalEnabled
    //     },
    //     isAddErrorEnabled: state => {
    //         return state.isAddErrorEnabled
    //     },
    //     isRefreshEnabled: state => {
    //         return state.isRefreshEnabled
    //     }
    // },
    actions: {
        showAddStockModal(context) {
            context.commit('showAddStockModal')
        },
        hideAddStockModal(context) {
            context.commit('hideAddStockModalWithoutRefresh')
        },
        hideAddStockModalWithOptions(context, option) {
            //TRUE means refresh screen
            //FALSE means show error
            if (option === true) {
                context.commit('hideAddStockModalWithRefresh')
            } else {
                context.commit('hideAddStockModalWithError')
            }
        },
        hideAddStockErrorModal(context) {
            context.commit('hideAddStockErrorModal')
        }
    },
    mutations: {
        showAddStockModal(state) {
            state.isAddStockModalEnabled = true;
        },
        hideAddStockModalWithoutRefresh(state) {
            state.isAddStockModalEnabled = false;
            state.isAddErrorEnabled = false;
        },
        hideAddStockModalWithRefresh(state) {
            state.isAddStockModalEnabled = false;
            state.isAddErrorEnabled = false;
        },
        hideAddStockModalWithError(state) {
            state.isAddStockModalEnabled = false;
            state.isAddErrorEnabled = true;
        },
        hideAddStockErrorModal(state) {
            state.isAddErrorEnabled = false;
        }
    }
});

export default store;