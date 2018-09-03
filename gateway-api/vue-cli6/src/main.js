import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import { routes } from './routes';

Vue.use(VueRouter);
const router = new VueRouter({routes: routes});

Vue.config.productionTip = false;

new Vue({
    router: router,
    render: h => h(App)
}).$mount('#app');
