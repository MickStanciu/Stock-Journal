import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
import store from './vuex/index'
import App from './App.vue'
import router from './router'

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.min.css'


Vue.config.productionTip = false;
Vue.use(BootstrapVue);

const app = new Vue({
  router,
  render: h => h(App),
  store
}).$mount('#app');


export {app, router, store}