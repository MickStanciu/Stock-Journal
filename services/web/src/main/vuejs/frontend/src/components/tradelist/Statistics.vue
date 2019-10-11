<template>
    <div id="statistics">
<!--        {{ stats_model }}-->

        <table class="table-responsive text-nowrap">
            <tbody>
                <tr>
                    <th scope="row">Shares owned:</th>
                    <td>{{ stats_model.sharesNumber }}</td>
                </tr>
                <tr>
                    <th scope="row">Average bought price:</th>
                    <td>{{ printCurrencyFormat(stats_model.shareAveragePrice) }}</td>
                </tr>
                <tr>
                    <th scope="row">Break even price:</th>
                    <td>{{ printCurrencyFormat(stats_model.breakEvenPrice) }}</td>
                </tr>
                <tr>
                    <th scope="row">Total realised premium:</th>
                    <td>{{ printCurrencyFormat(stats_model.totals.realisedPremium) }}</td>
                </tr>
                <tr>
                    <th scope="row">Selected realised premium:</th>
                    <td>{{ printCurrencyFormat(stats_model.selected.realisedPremium) }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import moneyUtil from "../../utils/money";

    export default {
        name: "Statistics",

        props: {
            datamodel: Object
        },

        data: function () {
            return {
                stats_model: this.datamodel
            }
        },

        methods: {
            printCurrencyFormat: moneyUtil.printCurrencyFormat
        }
    }
</script>

<style scoped>

</style>