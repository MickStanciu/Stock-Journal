<template>
  <div id="app">
    <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
      <h1>HTTP</h1>
      <div class="form-group">
        <label>Username</label>
        <input class="form-control" type="text" v-model="user.username"/>
      </div>
      <div class="form-group">
        <label>Mail</label>
        <input class="form-control" type="text" v-model="user.email"/>
      </div>
      <button class="btn btn-primary" v-on:click="submit">Submit</button>
      <hr/>
      <button class="btn btn-primary" v-on:click="fetchData">Get Data</button>
      <ul class="list-group">
        <li class="list-group-item" v-for="u in users">{{u.username}} = {{u.email}}</li>
      </ul>
    </div>
  </div>
</template>

<script>

export default {
    data() {
        return {
            user: {
                username: '',
                email: ''
            },
            users: []
        }
    },
    methods: {
        submit() {
            console.log(this.user);
            this.$http.post('https://393b84a6-cfb0-4dd2-86e5-3048844c514a.mock.pstmn.io', this.user)
                .then(response => {
                    console.log(response);
                }, error => {
                    console.log(error);
                });
        },
        fetchData() {
          this.$http.get('https://393b84a6-cfb0-4dd2-86e5-3048844c514a.mock.pstmn.io')
              .then(response => {
                  // console.log(data);
                  return response.json();
              }, error => {
                  console.log(error);
              })
              .then(data => {
                  const resultArray = [];
                  for (let key in data) {
                      resultArray.push(data[key]);
                  }
                  this.users = resultArray;
              })
        }
    }
}
</script>

<style>
</style>
