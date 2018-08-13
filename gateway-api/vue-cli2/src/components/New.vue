<template>
  <div>
    <h3>New Quote</h3>
    <button v-on:click="counter++">Increase!</button>
    <p>{{counter}}</p>
  </div>
</template>

<script>
  export default {
    data: function() {
      return {
        counter: 0
      }
    }
  }
</script>

<style scoped>
</style>
