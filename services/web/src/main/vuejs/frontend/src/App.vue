<template>
  <div id="app">
    <Header/>
    <div class="container">
      <router-view/>
    </div>
    <Footer/>
  </div>
</template>

<script>
  import Header from "./components/Header";
  import Footer from "./components/Footer";

  export default {
  name: 'app',
    components: {Footer, Header}
  }
</script>

<style>
#app {

}

  .container {
    margin-top: 80px;
    margin-bottom: 80px;
  }
</style>
