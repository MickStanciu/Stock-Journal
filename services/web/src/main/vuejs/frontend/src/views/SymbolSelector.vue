<template>
    <div>
        <ul class="list-group">
            <li class="list-group-item" v-for="(item) in items" v-bind:key="item.symbol">
                <router-link v-bind:to="getLink(item.symbol)" exact>{{ item.symbol }}</router-link>
            </li>
        </ul>
    </div>
</template>

<script>
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
            this.items = [
                {
                    'symbol' : 'MAT'
                }
            ];
        }
    }
</script>

<style scoped>

</style>