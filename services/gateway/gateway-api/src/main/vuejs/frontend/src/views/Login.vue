<template>
    <div>
        <div class="jumbotron">
            <h1 class="display-4">Log In</h1>
            <hr class="my-4">
            <form>
                <div class="form-group">
                    <label for="username" v-bind:class="{'text-danger': form_validation.username === false}">User name:</label>
                    <input v-model="form_element.username" class="form-control" v-bind:class="{'is-invalid': form_validation.username === false}" type="text" placeholder="Bob" id="username"/>
                </div>

                <div class="form-group">
                    <label for="password" v-bind:class="{'text-danger': form_validation.password === false}">Password:</label>
                    <input v-model="form_element.password" class="form-control" v-bind:class="{'is-invalid': form_validation.password === false}" type="password" placeholder="***" id="password"/>
                </div>
            </form>

            <p>If you don't have an account: <a href="#" >click here</a> to create one.</p>
            <p>If you forgot your password: <a href="#" >click here</a> to recover it.</p>

            <hr class="my-4">
            <p class="lead">
                <a class="btn btn-primary btn-lg" href="#" role="button" v-on:click="auth()">Authenticate</a>
            </p>
        </div>
    </div>
</template>

<script>
    import validation from "../utils/validation";

    export default {
        name: "Login",
        data: function () {
            return {
                form_element: {
                    username : "",
                    password: ""
                },

                form_validation: {
                    username: true,
                    password: true,
                    isValid: function () {
                        return this.username && this.password
                    }
                }
            }
        },
        methods: {
            auth: function () {
                if (this.checkForm() === false) {
                    return false;
                }
            },
            checkForm: function() {
                this.form_validation.username = validation.isDate(this.form_element.username) !== false;
                this.form_validation.password = validation.isNumber(this.form_element.password) !== false;
                return this.form_validation.isValid();
            }
        }
    }
</script>

<style scoped>

</style>