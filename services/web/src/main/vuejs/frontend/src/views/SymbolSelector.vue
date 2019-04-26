<template>
    <div>
        <ul class="list-group">
            <li class="list-group-item" v-for="(item) in items" v-bind:key="item">
                <router-link v-bind:to="getLink(item)" exact>{{ item }}</router-link>
            </li>
        </ul>
    </div>
</template>

<script>
    import service from '../service';
    export default {
        name: "SymbolSelector",
        data: function () {
            return {
                items : []
            }
        },
        methods: {
          getLink: function (symbol) {
              return "/log/" + symbol.toLowerCase();
          }
        },
        created() {
            service
                .getTradedSymbols()
                .then(data => {
                    this.items = data;
                });
        }
    }
</script>

<style scoped>

</style>