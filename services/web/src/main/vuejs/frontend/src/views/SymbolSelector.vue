<template>
    <div>
        <div class="row mt-3 pb-2 pt-2 table-header">
            <div class="col-md-5">Symbol</div>
            <div class="col-md-5"># Trades</div>
            <div class="col">P&L</div>
        </div>

        <template v-for="(item, idx) in items">
            <router-link v-bind:to="getLink(item)" exact>
                <div class="row pb-1 pt-1" v-bind:class="rowClass(item, idx)" v-bind:key="item.id">
                    <div class="col-md-5">{{ item }}</div>
                    <div class="col-md-5">&nbsp;</div>
                    <div class="col">&nbsp;</div>
                </div>
            </router-link>
        </template>
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
            },
            rowClass: function (item, idx) {
                let className = 'table-cell-odd';
                if (idx%2 === 0) {
                    className = 'table-cell-even';
                }
                return className;
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
    .table-header {
        background-color: #005cbf;
        color: white;
        vertical-align: center;
        border: #005cbf 1px solid;
    }

    .table-cell-odd {
        background-color: whitesmoke;
    }

    .table-cell-even {
        background-color: white;
    }
</style>