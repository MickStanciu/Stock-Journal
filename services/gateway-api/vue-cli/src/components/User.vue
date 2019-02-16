<template>
  <div class="component">
    <h1>The User Component</h1>
    <p>I'm an awesome User!</p>
    <p>User Age: {{age}}</p>
    <button v-on:click="changeName">Change my name</button>
    <hr>
    <div class="row">
      <div class="col-xs-12 col-sm-6">
        <app-user-detail
          v-bind:myName="name"
          v-on:nameWasReset="name=$event"
          v-bind:resetFn="resetName"
          v-bind:userAge="age"
        ></app-user-detail>
      </div>
      <div class="col-xs-12 col-sm-6">
        <app-user-edit
          v-bind:userAge="age"
          v-on:ageWasEdited="age=$event"
        ></app-user-edit>
      </div>
    </div>
  </div>
</template>

<script>
  import UserDetail from './UserDetail';
  import UserEdit from './UserEdit';

  export default {
    data: function() {
      return {
        name: 'Max',
        age: 27
      }
    },
    methods: {
      changeName() {
        this.name = 'Anna';
      },
      resetName() {
        this.name = 'Max';
      }
    },
    components: {
      appUserDetail: UserDetail,
      appUserEdit: UserEdit
    }
  }
</script>

<style scoped>
  div {
    background-color: lightblue;
  }
</style>
