<template>
  <div class="container">
    <div class="row">
      <div class="col-xs-12">
        <button v-on:click="selectedComponent='app-quote'">Quote</button>
        <button v-on:click="selectedComponent='app-author'">Author</button>
        <button v-on:click="selectedComponent='app-new'">New</button>
        <hr/>

        <keep-alive>
          <component v-bind:is="selectedComponent">
            <p>Default Content</p>
          </component>
        </keep-alive>

        <!--<app-quote>-->
          <!--<h2 slot="title">{{quoteTitle}}</h2>-->
          <!--<p>A wonderful quote!</p>-->
        <!--</app-quote>-->
      </div>
    </div>
  </div>
</template>

<script>
import Quote from './components/Quote'
import Author from './components/Author'
import New from './components/New'

export default {
  data: function() {
    return {
      quoteTitle: 'The Quote',
      selectedComponent: 'app-quote'
    }
  },
  components: {
    appQuote: Quote,
    appAuthor: Author,
    appNew: New
  }
}
</script>

<style scoped>

</style>
