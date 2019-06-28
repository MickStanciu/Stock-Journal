<template>
    <div class="modal" id="deleteStockModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Delete Stock Position</h3>
                </div>

                <div class="modal-body">
                    <stock-fragment v-bind:post="{model: this.post.model}"/>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-info" v-on:click="closeModal()"> Close </button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal" v-on:click="submitAndClose()">Submit</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

    import service from '../../service';
    import StockFragment from "./StockFragment";

    export default {
        name: "DeleteStockTrade",
        components: {StockFragment},
        props: ['post'],
        methods: {
            closeModal: function () {
                this.$store.dispatch('hideModalWithoutRefresh');
            },

            submitAndClose: function () {
                service.deleteTrade(this.post.model);
                this.$store.dispatch('hideModalWithRefresh');
            }
        }
    }
</script>

<style scoped>
    .modal {
        display: block;
        font-size: 0.75rem;
    }

    .modal-title {
        font-size: 1.5rem;
    }
</style>