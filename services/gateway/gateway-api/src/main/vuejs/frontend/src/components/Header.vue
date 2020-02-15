<template>
    <div>
        <nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <router-link class="nav-link" to="/" exact>Home</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link class="nav-link" to="/log" exact>Log</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link class="nav-link" to="/summary" exact>Summary</router-link>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
</template>

<script>
    export default {
        name: "Header"
    }
</script>

<style scoped>

</style>