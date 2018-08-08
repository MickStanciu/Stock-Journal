<template>
  <div class="container">
    <app-header/>
    <hr/>
    <div class="row">
      <app-servers/>
      <app-server-details/>
    </div>
    <app-footer/>
  </div>
</template>

<script>
  import Header from './components/shared/Header'
  import Footer from './components/shared/Footer'
  import Servers from './components/server/Servers'
  import ServerDetails from './components/server/ServerDetails'

  export default {
    components: {
      'app-header': Header,
      'app-servers': Servers,
      'app-server-details':  ServerDetails,
      'app-footer': Footer
    }
  }
</script>

<style>

</style>
