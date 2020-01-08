<template>
    <div class="modal" id="deleteStockModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Delete Option Position</h3>
                </div>

                <div class="modal-body">
                    <form>
                        <fieldset class="form-group row" v-bind:disabled="this.is_form_readonly">
                            <label for="date" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.date === false}">Date:</label>
                            <div class="col-sm-9">
                                <input v-model="form_element.date" class="form-control" v-bind:class="{'is-invalid': form_validation.date === false}" type="text" placeholder="dd-MMM-yyyy" id="date"/>
                            </div>
                        </fieldset>

                        <fieldset class="form-group row" v-bind:disabled="this.is_form_readonly">
                            <div class="col-form-label col-sm-3 pt-0">Action</div>
                            <div class="col-sm-9">
                                <div class="form-check form-check-inline">
                                    <input v-model="form_element.action" class="form-check-input" type="radio" name="actionBuySell" id="actionBuy" value="BUY" checked/>
                                    <label class="form-check-label" for="actionBuy">Buy</label>
                                </div>

                                <div class="form-check form-check-inline">
                                    <input v-model="form_element.action" class="form-check-input" type="radio" name="actionBuySell" id="actionSell" value="SELL"/>
                                    <label for="actionSell" class="form-check-label">Sell</label>
                                </div>
                            </div>
                        </fieldset>

                        <fieldset class="form-group row" v-bind:disabled="this.is_form_readonly">
                            <div class="col-form-label col-sm-3 pt-0">Option Type</div>
                            <div class="col-sm-9">
                                <div class="form-check form-check-inline">
                                    <input v-model="form_element.type" class="form-check-input" type="radio" name="actionCallPut" id="actionCall" value="CALL" checked/>
                                    <label class="form-check-label" for="actionCall">Call</label>
                                </div>

                                <div class="form-check form-check-inline">
                                    <input v-model="form_element.type" class="form-check-input" type="radio" name="actionCallPut" id="actionPut" value="PUT"/>
                                    <label for="actionPut" class="form-check-label">Put</label>
                                </div>
                            </div>
                        </fieldset>

                        <fieldset class="form-group row" v-bind:disabled="this.is_form_readonly">
                            <label for="stock-price" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.stock_price === false}">Stock Price:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.stock_price === false}" v-model="form_element.stock_price" type="text" id="stock-price"/>
                            </div>
                        </fieldset>

                        <fieldset class="form-group row" v-bind:disabled="this.is_form_readonly">
                            <label for="strike-price" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.strike_price === false}">Strike Price:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.strike_price === false}" v-model="form_element.strike_price" type="text" id="strike-price"/>
                            </div>
                        </fieldset>

                        <fieldset class="form-group row" v-bind:disabled="this.is_form_readonly">
                            <label for="exp-date" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.exp_date === false}">Expiry Date:</label>
                            <div class="col-sm-9">
                                <input v-model="form_element.exp_date" class="form-control" v-bind:class="{'is-invalid': form_validation.exp_date === false}" type="text" placeholder="MMM dd" id="exp-date"/>
                            </div>
                        </fieldset>

                        <fieldset class="form-group row" v-bind:disabled="this.is_form_readonly">
                            <label for="contracts" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.contracts === false}">Contracts:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.contracts === false}" v-model="form_element.contracts" type="text" id="contracts"/>
                            </div>
                        </fieldset>

                        <fieldset class="form-group row" v-bind:disabled="this.is_form_readonly">
                            <label for="premium" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.premium === false}">Premium:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.premium === false}" v-model="form_element.premium" type="text" id="premium"/>
                            </div>
                        </fieldset>

                        <fieldset class="form-group row" v-bind:disabled="this.is_form_readonly">
                            <label for="fee" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.fees === false}">Fees:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.fees === false}" v-model="form_element.fees" type="text" id="fee"/>
                            </div>
                        </fieldset>

                    </form>
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
    import service from '../../../service';
    import dateTimeUtil from '../../../utils/time'

    export default {
        name: "DeleteOptionTrade",
        props: {
            post: Object
        },
        data: function () {
            return {
                is_form_readonly: true,

                form_element: {
                    date: dateTimeUtil.convertFromOffsetZuluToDisplay(this.post.model.date),
                    action: this.post.model.action,
                    type: this.post.model.optionType,
                    stock_price : this.post.model.stockPrice,
                    strike_price: this.post.model.strikePrice,
                    exp_date: dateTimeUtil.convertExpiryDateForDisplay(this.post.model.expiryDate),
                    contracts: this.post.model.contracts,
                    premium : this.post.model.premium,
                    fees: this.post.model.brokerFees
                },

                form_validation: {
                    date: true,
                    stock_price: true,
                    strike_price: true,
                    exp_date: true,
                    contracts: true,
                    premium: true,
                    fees: true
                }
            }
        },
        methods: {
            closeModal: function () {
                this.$store.dispatch('option/hideModal');
            },

            submitAndClose: function () {
                service.deleteOptionTrade(this.post.model)
                    .then(() => {
                        this.$store.dispatch('option/hideModal');
                        this.$store.dispatch('refreshData');
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

    .form-control {
        font-size: 0.75rem;
    }
</style>
