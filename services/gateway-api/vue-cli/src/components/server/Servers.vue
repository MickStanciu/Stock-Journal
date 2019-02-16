<template>
  <div class="col-xs-12 col-sm-6">
   <ul class="list-group" v-for="index in 5">Server #{{index}}</ul>
  </div>
</template>

<script>
  export default {
    data: function () {
      return {
        status: 'Critical'
      }
    },
    methods: {
      changeStatus() {
        this.status = 'Normal'
      }
    }
  }
</script>

<style scoped>
  div {
    border: 1px solid blue;
  }
</style>
