export const AuthModule = {
    namespaced: true,

    state: {
        isAuthenticated: false,
        model: null,
    },

    actions: {
        success(context, model) {
            context.commit('success', model)
        },
        fail(context) {
            context.commit('fail')
        }
    },

    mutations: {
        success(state, model) {
            if (typeof model !== undefined && model != null) {
                state.model = model;
                state.isAuthenticated = true;
                localStorage.setItem('auth', JSON.stringify(model));
            }
        },
        fail(state) {
            state.isAuthenticated = false;
            state.model = null;
            localStorage.removeItem('auth')
        }
    }
};