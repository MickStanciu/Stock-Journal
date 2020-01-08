import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home'
import TradeList from './views/TradeList'
import SymbolSelector from "./views/SymbolSelector";
import NotFound from "./views/NotFound";

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  linkActiveClass: 'active',
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/log/',
      name: 'log',
      component: SymbolSelector
    },
    {
      path: '/log/:symbol',
      name: 'log_symbol',
      component: TradeList
    },
    {
      path: '*',
      component: NotFound
    }
  ]
})
