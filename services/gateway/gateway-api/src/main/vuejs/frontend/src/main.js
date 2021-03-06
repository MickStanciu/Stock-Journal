import Vue from 'vue'
import Vuex from 'vuex'
import store from './vuex/index'
import App from './App.vue'
import router from './router'
import {library} from '@fortawesome/fontawesome-svg-core'
import {
    faCalculator,
    faDollarSign,
    faEdit,
    faLockOpen,
    faPlusCircle,
    faTrashAlt,
    faUnlock
} from '@fortawesome/free-solid-svg-icons'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'

import 'bootstrap/dist/css/bootstrap.min.css'

Vue.component('font-awesome-icon', FontAwesomeIcon);

library.add(faTrashAlt, faLockOpen, faUnlock, faCalculator, faPlusCircle, faDollarSign, faEdit);
Vue.config.productionTip = false;
Vue.use(Vuex);

const app = new Vue({
  router,
  render: h => h(App),
  store
}).$mount('#app');


export {app, router, store}
