<template>
    <div id="symbol-list">
        <div class="row mt-3 pb-2 pt-2 table-header">
            <div class="col-md-5">Symbol</div>
            <div class="col-md-5"># Trades</div>
            <div class="col text-right">Realised P&L</div>
        </div>

        <template v-for="(item, idx) in items">
            <router-link v-bind:to="getLink(item.symbol)" v-bind:key="idx" exact>
                <div class="row pb-1 pt-1" v-bind:class="rowClass(item, idx)" v-bind:key="item.id">
                    <div class="col-md-5">{{ item.symbol }}</div>
                    <div class="col-md-5">{{ item.trades }}</div>
                    <div class="col text-right" v-bind:class="colorClass(item.total)">{{ printCurrencyFormat(item.total) }}</div>
                </div>
            </router-link>
        </template>

        <div class="row mt-3 pb-2 pt-2 table-footer">
            <div class="col-md-5">&nbsp;</div>
            <div class="col-md-5">&nbsp;</div>
            <div class="col text-right">TOTAL: {{ printCurrencyFormat(total) }}</div>
        </div>
    </div>
</template>

<script>
    import service from '../service';

    export default {
        name: "SymbolSelector",
        data: function () {
            return {
                items: [],
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
            },

            colorClass: function (item) {
                if (item > 0.00) {
                    return 'text-green';
                }
                if (item < 0.00) {
                    return 'text-red';
                }
            },

            printCurrencyFormat: function (value) {
                if (typeof value === 'undefined') {
                    value = 0;
                }
                let params = {
                    style: 'currency',
                    currency: 'USD',
                    minimumFractionDigits: 2
                };
                return new Intl.NumberFormat('en-US', params).format(value);
            },
        },
        computed: {
            total: function () {
                let total = 0.00;
                this.items.forEach(function (item) {
                   total += item.total;
                });
                return total;
            }
        },
        created() {
            service
                .getSummary()
                .then(data => {
                    let localItems = [];
                    data.items.forEach(function (item) {
                        let obj = {};
                        obj.symbol = item.symbol;
                        if (typeof item.trades === 'undefined') {
                            obj.trades = 0;
                        } else {
                            obj.trades = item.trades;
                        }
                        if (typeof item.total === 'undefined') {
                            obj.total = 0;
                        } else {
                            obj.total = item.total;
                        }
                        localItems.push(obj);
                    });
                    this.items = localItems;
                });
        }
    }
</script>

<style scoped>
    .table-header, .table-footer {
        background-color: #005cbf;
        color: white;
        vertical-align: center;
        border: #005cbf 1px solid;
        font-weight: bold;
    }


    .table-cell-odd {
        background-color: whitesmoke;
    }

    .table-cell-even {
        background-color: white;
    }

    #symbol-list {
        font-size: 0.75rem;
    }

    .text-red {
        color: red;
    }

    .text-green {
        color: green;
    }
</style>