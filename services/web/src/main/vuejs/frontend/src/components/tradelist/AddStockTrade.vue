<template>
    <div class="modal" id="exampleModal">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title">Add Stock Position</h3>
                    </div>

                    <div class="modal-body">
                        <form>
                            <div class="form-group row">
                                <label for="date" class="col-sm-2 col-form-label">Date:</label>
                                <div class="col-sm-10">
                                    <input type="text" placeholder="dd-MMM-yyyy" id="date"/>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="symbol" class="col-sm-2 col-form-label">Symbol:</label>
                                <div class="col-sm-10">
                                    <input type="text" disabled id="symbol" v-bind:value="symbol"/>
                                </div>
                            </div>

                            <fieldset class="form-group row">
                                <div class="col-form-label col-sm-2 pt-0">Action</div>
                                <div class="col-sm-10">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="actionBuySell" id="actionBuy" value="BUY" checked/>
                                        <label class="form-check-label" for="actionBuy">Buy</label>
                                    </div>

                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="actionBuySell" id="actionSell" value="SELL"/>
                                        <label for="actionSell" class="form-check-label">Sell</label>
                                    </div>
                                </div>
                            </fieldset>

                            <div class="form-group row">
                                <label for="price" class="col-sm-2 col-form-label">Price:</label>
                                <div class="col-sm-10">
                                    <input type="text" id="price"/>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="quantity" class="col-sm-2 col-form-label">Quantity:</label>
                                <div class="col-sm-10">
                                    <input type="text" id="quantity"/>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="fee" class="col-sm-2 col-form-label">Fees:</label>
                                <div class="col-sm-10">
                                    <input type="text" id="fee"/>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-info" v-on:click="closeModal()"> Close </button>
                        <button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click="submitAndClose()">Submit</button>
                    </div>
                </div>
            </div>
        </div>
</template>

<script>
    export default {
        name: "AddStockTrade",
        props: ['post'],
        data: function () {
            return {
                timeZone : 'Australia/Sydney',
                currency : 'USD',
                symbol : this.post.symbol
            }
        },
        methods: {
            closeModal: function () {
                console.log('cancel');
                this.$store.dispatch('hideAddStockModel');
            },
            submitAndClose: function () {
                console.log('submit');
                this.$store.dispatch('hideAddStockModel');
            },
        }
    }
</script>

<style scoped>
    .modal {
        display: block;
    }
</style>