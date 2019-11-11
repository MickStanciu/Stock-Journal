export const DividendModule = {
    namespaced: true,
    state: {
        isAddDividendModalEnabled: false,
        isEditDividendModalEnabled: false,
        isDeleteDividendModalEnabled: false,
    },

    actions: {
        showAddDividendModal(context) {
            context.commit('showAddDividendModal')
        },
        showEditDividendModal(context) {
            context.commit('showEditDividendModal')
        },
        showDeleteDividendModal(context) {
            context.commit('showDeleteDividendModal')
        },
        hideModal(context) {
            context.commit('hideModal')
        }
    },

    mutations: {
        showAddDividendModal(state) {
            state.isAddDividendModalEnabled = true;
        },
        showEditOptionModal(state) {
            state.isEditDividendModalEnabled = true;
        },
        showDeleteDividendModal(state) {
            state.isDeleteDividendModalEnabled = true;
        },
        hideModal(state) {
            state.isAddDividendModalEnabled = false;
            state.isEditDividendModalEnabled = false;
            state.isDeleteDividendModalEnabled = false;
        }
    }
};
