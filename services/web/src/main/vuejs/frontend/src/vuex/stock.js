const stockModule = {
    namespaced: true,
    state: {
        transactionId: undefined,
        accountId: undefined,
        date: undefined,
        symbol: undefined,
        price: undefined,
        preferredPrice: undefined,
        quantity: undefined,
        action: undefined,
        brokerFees: undefined,
        isSynthetic: false,
        groupSelected: undefined,
        legClosed: undefined
    },

    actions: {
        updateModel(context, model) {
            console.log("ACTIONS>");
            context.commit('updateModel', model)
        }
    },

    mutations: {
        updateModel(state, model) {
            console.log("MUTATION>");
            state.transactionId = model.transactionId;
            state.accountId = model.accountId;
            state.date = model.date;
            state.symbol = model.symbol;
            state.price = model.price;
            state.preferredPrice = model.preferredPrice;
            state.quantity = model.quantity;
            state.action = model.action;
            state.brokerFees = model.brokerFees;
            state.isSynthetic = model.isSynthetic;
            state.groupSelected = model.groupSelected;
            state.legClosed = model.legClosed;
        }
    }
};

export default stockModule;
