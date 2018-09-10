<template>
    <div>
        <h1>The User Page</h1>
        <hr/>
        <button v-on:click="navigateToHome" class="btn btn-primary">Go To Home</button>
        <hr/>
        <router-view></router-view>
    </div>

</template>

<script>
    export default {
        methods: {
            navigateToHome() {
                this.$router.push({path: '/'});
            }
        }
    }
</script>