<template>
  <div id="app">
    <Header/>
    <div class="container" style="margin-top: 80px;">
      <router-view/>
    </div>
    <Footer/>
  </div>
</template>

<script>
  import Header from "./components/Header";
  import Footer from "./components/Footer";
  require('../node_modules/bootstrap/dist/css/bootstrap.min.css');

  export default {
  name: 'app',
    components: {Footer, Header}
  }
</script>

<style>
#app {

}
</style>
