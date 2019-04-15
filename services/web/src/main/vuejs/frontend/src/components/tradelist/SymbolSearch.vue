<template>
    <div>
        <form>
            <div class="form-group">
                <label for="inputSymbol">Enter Symbol</label>
                <input v-model="symbol" id="inputSymbol" class="form-control" placeholder="type symbol" />
            </div>
            <button type="button" class="btn btn-primary" v-bind:disabled="isSearchDisabled" v-on:click="executeSearch">Search</button>
        </form>
    </div>
</template>

<script>
    export default {
        name: "SymbolSearch",
        data: function () {
            return {
                symbol : '',
                isSearchDisabled: true
            }
        },
        methods: {
            executeSearch: function () {
                console.log(this.symbol);
            }
        }
    }
</script>

<style scoped>

</style>