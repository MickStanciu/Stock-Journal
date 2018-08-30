import Vue from 'vue'
import App from './App.vue'
import VueResource from 'vue-resource'

Vue.use(VueResource);
// Vue.http.options.root = 'http://localhost:8085/api/v1/sample';
Vue.http.interceptors.push((req, next) => {
  console.log(req);
});

Vue.config.productionTip = false;

new Vue({
  render: h => h(App)
}).$mount('#app');
