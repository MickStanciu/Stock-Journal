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
                    <td>{{ stats_model.shareAveragePrice }}</td>
                </tr>
                <tr>
                    <th scope="row">Break even price:</th>
                    <td>{{ stats_model.breakEvenPrice }}</td>
                </tr>
                <tr>
                    <th scope="row">Total realised premium:</th>
                    <td>{{ stats_model.totals.realisedPremium }}</td>
                </tr>
                <tr>
                    <th scope="row">Selected realised premium:</th>
                    <td>{{ stats_model.selected.realisedPremium }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: "Statistics",
        props: {
            datamodel: Object
        },
        data: function () {
            return {
                stats_model: this.datamodel
            }
        }
    }
</script>

<style scoped>

</style>