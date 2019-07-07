<template>
    <div id="share-data">
        <div class="row mt-3 pb-2 pt-2 table-header">
            <div class="col-md-12 text-center">Share Data</div>
        </div>

        <div class="row pb-1 pt-1">symbol {{ share_model.symbol }}</div>
        <div class="row pb-1 pt-1">book value {{ share_model.bookValue }}</div>
        <div class="row pb-1 pt-1">calc tg {{ share_model.calculatedTarget }}</div>
        <div class="row pb-1 pt-1">eps {{ share_model.eps }}</div>
        <div class="row pb-1 pt-1">eps 2 {{ share_model.epsFuture }}</div>
        <div class="row pb-1 pt-1">finv tg {{ share_model.finvizTarget }}</div>
        <div class="row pb-1 pt-1">mcap {{ share_model.marketCapitalization }}</div>
        <div class="row pb-1 pt-1">pe {{ share_model.peRatio }}</div>
        <div class="row pb-1 pt-1">pe2 {{ share_model.peRatioFuture }}</div>
        <div class="row pb-1 pt-1">price {{ share_model.price }}</div>
        <div class="row pb-1 pt-1">sector {{ share_model.sector }}</div>

        <div class="row pb-1 pt-1 table-footer">
            <div class="col-md-12 text-center">&nbsp;</div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "datamodel",
        props: {
            datamodel: Object
        },
        data: function () {
            return {
                share_model : this.datamodel
            }
        }

    }
</script>

<style scoped>
    .table-header {
        background-color: #005cbf;
        color: white;
        vertical-align: center;
        border: #005cbf 1px solid;
        font-weight: bold;
    }

    .table-footer {
        background-color: #9fcdff;
        color: #005cbf;
        font-weight: bold;
        vertical-align: center;
        border: #9fcdff 1px solid;
    }
</style>