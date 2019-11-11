export const OptionModule = {
    namespaced: true,
    state: {
        isAddOptionModalEnabled: false,
        isEditOptionModelEnabled: false,
        isDeleteOptionModalEnabled: false,
    },

    actions: {
        showAddOptionModal(context) {
            context.commit('showAddOptionModal')
        },
        showEditOptionModal(context) {
            context.commit('showEditOptionModal')
        },
        showDeleteOptionModal(context) {
            context.commit('showDeleteOptionModal')
        },
        hideModal(context) {
            context.commit('hideModal')
        }
    },

    mutations: {
        showAddOptionModal(state) {
            state.isAddOptionModalEnabled = true;
        },
        showEditOptionModal(state) {
            state.isEditOptionModelEnabled = true;
        },
        showDeleteOptionModal(state) {
            state.isDeleteOptionModalEnabled = true;
        },
        hideModal(state) {
            state.isAddOptionModalEnabled = false;
            state.isEditOptionModelEnabled = false;
            state.isDeleteOptionModalEnabled = false;
        }
    }
};
