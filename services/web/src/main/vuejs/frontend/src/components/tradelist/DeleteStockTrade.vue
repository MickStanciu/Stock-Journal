<template>
    <div class="modal" id="deleteStockModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Delete Stock Position</h3>
                </div>

                <div class="modal-body">
                    <stock-fragment v-bind:stock_model="this.post.model" v-bind:readonly="this.is_form_readonly"/>
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
        data: function () {
            return {
                is_form_readonly: true,
            }
        },
        methods: {
            closeModal: function () {
                this.$store.dispatch('hideModalWithoutRefresh');
            },

            submitAndClose: function () {
                service.deleteShareTrade(this.post.model)
                    .then(() => {
                        // console.debug("submitAndClose");
                        this.$store.dispatch('hideModalWithRefresh');
                    });
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