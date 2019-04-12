<template>
    <div>
        <span>HELLO {{msg}}</span>
        <ul>
            <li v-for="item in items">
                {{item.symbol}}
            </li>
        </ul>

    </div>
</template>

<script>
    export default {
        name: "TradeList",
        data() {
            return {
                msg : "WORLD",
                items : [
                    {id : 'ID1', symbol: 'TRIP'},
                    {id : 'ID2', symbol: 'TEAM'},
                ]
            }
        },
        created() {

        }
    }
</script>

<style scoped>

</style>