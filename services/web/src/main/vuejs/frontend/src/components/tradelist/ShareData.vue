<template>
    <div id="share-data">
        <div class="row table-header text-center align-items-center">
            <div class="col-md-1">Price</div>
            <div class="col-md-2">Sector</div>
            <div class="col-md-1">FinViz Target</div>
            <div class="col-md-1">Computed Target</div>
            <div class="col-md-2">Updated On</div>
        </div>
        <div class="row pb-1 pt-1 text-center">
            <div class="col-md-1">{{ share_model.price }}</div>
            <div class="col-md-2">{{ share_model.sector }}</div>
            <div class="col-md-1">{{ share_model.finvizTarget }}</div>
            <div class="col-md-1">{{ share_model.calculatedTarget }}</div>
            <div class="col-md-2">{{dateTz(share_model.lastUpdatedOn) }}</div>
        </div>

        <div class="row pb-1 pt-1 table-footer">
            <div class="col-md-12 text-center">&nbsp;</div>
        </div>
    </div>
</template>

<script>
    import dateTimeUtil from "../../utils/time";

    export default {
        name: "ShareData",
        props: {
            datamodel: Object
        },
        data: function () {
            return {
                share_model: this.datamodel
            }
        },
        methods: {
            dateTz: function(item) {
                return dateTimeUtil.convertForDisplay(item);
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