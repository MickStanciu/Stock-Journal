<template>
  <div class="login">
    <h1>{{ pageName }}</h1>
    <label for="email">E-Mail</label>
    <input id="email" type="text" v-model="emailAddress"/>
    <label for="password">Password</label>
    <input id="password" type="password" v-model="password"/>
    <button v-on:click="onSubmit()">Submit</button>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      pageName: 'Login Page',
      emailAddress: '',
      password: ''
    }
  },
  methods: {
    onSubmit: function () {
      alert('On Submit: ' + this.emailAddress)
    }
  }
}
</script>

<style scoped>

</style>
