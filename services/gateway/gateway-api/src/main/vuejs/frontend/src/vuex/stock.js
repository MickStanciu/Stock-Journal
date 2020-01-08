export const StockModule = {
    namespaced: true,
    state: {
        isAddStockModalEnabled: false,
        isEditStockModelEnabled: false,
        isDeleteStockModalEnabled: false,
    },

    actions: {
        showAddStockModal(context) {
            context.commit('showAddStockModal')
        },
        showEditStockModal(context) {
            context.commit('showEditStockModal')
        },
        showDeleteStockModal(context) {
            context.commit('showDeleteStockModal')
        },
        hideModal(context) {
            context.commit('hideModal')
        }
    },

    mutations: {
        showAddStockModal(state) {
            state.isAddStockModalEnabled = true;
        },
        showEditStockModal(state) {
            state.isEditStockModelEnabled = true;
        },
        showDeleteStockModal(state) {
            state.isDeleteStockModalEnabled = true;
        },
        hideModal(state) {
            state.isAddStockModalEnabled = false;
            state.isEditStockModelEnabled = false;
            state.isDeleteStockModalEnabled = false;
        }
    }
};
