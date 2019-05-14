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
                                    <input v-model="form_date" type="text" placeholder="dd-MMM-yyyy" id="date"/>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="symbol" class="col-sm-2 col-form-label">Symbol:</label>
                                <div class="col-sm-10">
                                    <input type="text" disabled id="symbol" v-bind:value="form_symbol"/>
                                </div>
                            </div>

                            <fieldset class="form-group row">
                                <div class="col-form-label col-sm-2 pt-0">Action</div>
                                <div class="col-sm-10">
                                    <div class="form-check">
                                        <input v-model="form_action" class="form-check-input" type="radio" name="actionBuySell" id="actionBuy" value="BUY" checked/>
                                        <label class="form-check-label" for="actionBuy">Buy</label>
                                    </div>

                                    <div class="form-check">
                                        <input v-model="form_action" class="form-check-input" type="radio" name="actionBuySell" id="actionSell" value="SELL"/>
                                        <label for="actionSell" class="form-check-label">Sell</label>
                                    </div>
                                </div>
                            </fieldset>

                            <div class="form-group row">
                                <label for="price" class="col-sm-2 col-form-label">Price:</label>
                                <div class="col-sm-10">
                                    <input v-model="form_price" type="text" id="price"/>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="quantity" class="col-sm-2 col-form-label">Quantity:</label>
                                <div class="col-sm-10">
                                    <input v-model="form_quantity" type="text" id="quantity"/>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="fee" class="col-sm-2 col-form-label">Fees:</label>
                                <div class="col-sm-10">
                                    <input v-model="form_fees" type="text" id="fee"/>
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
    import service from '../../service';
    import dateTimeUtil from '../../utils/time'
    import ShareApiModel from '../../models/ShareApiModel'

    export default {
        name: "AddStockTrade",
        props: ['post'],
        data: function () {
            return {
                currency : 'USD',
                form_symbol : this.post.symbol,
                form_date : dateTimeUtil.dateNowFormatted(),
                form_action : 'BUY',
                form_price : '0.00',
                form_quantity: 0,
                form_fees : '0.00'
            }
        },
        methods: {
            closeModal: function () {
                console.log('cancel');
                this.$store.dispatch('hideAddStockModel');
            },
            submitAndClose: function () {
                let shareDto = new ShareApiModel(this.form_symbol);
                shareDto.date = dateTimeUtil.convertToOffsetDateTime(this.form_date);
                shareDto.action = this.form_action;
                shareDto.price = this.form_price;
                shareDto.quantity = this.form_quantity;
                shareDto.brokerFees = this.form_fees;

                service.recordShareTrade(shareDto);
                this.$store.dispatch('hideAddStockModel');
            }
        }
    }
</script>

<style scoped>
    .modal {
        display: block;
    }
</style>