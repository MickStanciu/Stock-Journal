import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home'
import TradeList from './views/TradeList'

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/log',
      name: 'log',
      component: TradeList
    }
  ]
})
