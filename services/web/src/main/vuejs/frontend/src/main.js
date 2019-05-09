import Vue from 'vue'
import store from './vuex/index'
import Vuex from 'vuex'
import App from './App.vue'
import router from './router'

import 'bootstrap/dist/css/bootstrap.min.css'


Vue.config.productionTip = false;
Vue.use(Vuex);

const app = new Vue({
  router,
  render: h => h(App),
  store
}).$mount('#app');


export {app, router, store}