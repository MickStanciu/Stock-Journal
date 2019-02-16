<template>
  <div class="row">
    <app-quote v-for="(quote, index) in quotes" v-on:click.native="deleteQuote(index)">{{quote}}</app-quote>
  </div>
</template>

<script>
  import Quote from './Quote'
  export default {
    props: ['quotes'],
    components: {
      appQuote: Quote
    },
    methods: {
      deleteQuote(index) {
        this.$emit('quoteDeleted', index)
      }
    }
  }
</script>

<style>

</style>
