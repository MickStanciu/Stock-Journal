<template>
  <div class="container">
    <app-header v-bind:quote-count="quotes.length" v-bind:max-quotes="maxQuotes"/>
    <new-quote v-on:quoteAdded="newQuote"/>
    <app-quote-grid v-bind:quotes="quotes" v-on:quoteDeleted="deleteQuote"/>
    <div class="row">
      <div class="col-sm-12 text-center">
        <div class="alert alert-info">Info: Click on a Quote to Delete it!</div>
      </div>
    </div>
  </div>
</template>

<script>
  import QuoteGrid from './components/QuoteGrid'
  import NewQuote from './components/NewQuote'
  import Header from './components/Header'
  export default {
    data: function() {
      return {
        maxQuotes: 10,
        quotes: [
          'Just a Quote to see something'
        ],
      }
    },
    components: {
      appQuoteGrid: QuoteGrid,
      newQuote: NewQuote,
      appHeader: Header
    },
    methods: {
      newQuote(quote) {
        if (this.quotes.length >= this.maxQuotes) {
          return alert ('Please delete quotes first!');
        }
        this.quotes.push(quote)
      },
      deleteQuote(index) {
        this.quotes.splice(index, 1)
      }
    }
  }
</script>

<style>
</style>
