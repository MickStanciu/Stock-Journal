<template>
  <div id="app">
    <TradeList/>
  </div>
</template>

<script>
  require('../node_modules/bootstrap-4-grid/css/grid.css');
  import TradeList from './components/TradeList'

  export default {
  name: 'app',
  components: {
    TradeList
  }
}
</script>

<style>
#app {

}
</style>
