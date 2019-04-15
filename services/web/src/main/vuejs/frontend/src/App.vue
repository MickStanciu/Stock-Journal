<template>
  <div id="app">
    <div class="container">
      <TradeList/>
    </div>
  </div>
</template>

<script>
  require('../node_modules/bootstrap/dist/css/bootstrap.min.css');
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
