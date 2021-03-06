import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home'
import TradeList from './views/TradeList'
import SymbolSelector from "./views/SymbolSelector";
import Summary from "./views/Summary";
import NotFound from "./views/NotFound";
import Login from "./views/Login";

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
      path: '/summary/',
      name: 'summary',
      component: Summary
    },
    {
      path: '/log/:symbol',
      name: 'log_symbol',
      component: TradeList
    },
    {
      path: '/login/',
      name: 'login',
      component: Login
    },
    {
      path: '*',
      component: NotFound
    }
  ]
})
