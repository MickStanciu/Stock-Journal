<template>
    <div class="modal" id="editOptionModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Edit Option Position for {{form_element.symbol}}</h3>
                </div>

                <div class="modal-body">
                    <form>
                        <div class="form-group row">
                            <label for="date" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.date === false}">Date:</label>
                            <div class="col-sm-9">
                                <input v-model="form_element.date" class="form-control" v-bind:class="{'is-invalid': form_validation.date === false}" type="text" placeholder="dd-MMM-yyyy" id="date"/>
                            </div>
                        </div>

                        <fieldset class="form-group row">
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

                        <fieldset class="form-group row">
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

                        <div class="form-group row">
                            <label for="stock-price" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.stock_price === false}">Stock Price:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.stock_price === false}" v-model="form_element.stock_price" type="text" id="stock-price"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="strike-price" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.strike_price === false}">Strike Price:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.strike_price === false}" v-model="form_element.strike_price" type="text" id="strike-price"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="exp-date" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.exp_date === false}">Expiry Date:</label>
                            <div class="col-sm-9">
                                <input v-model="form_element.exp_date" class="form-control" v-bind:class="{'is-invalid': form_validation.exp_date === false}" type="text" placeholder="MMM dd" id="exp-date"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="contracts" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.contracts === false}">Contracts:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.contracts === false}" v-model="form_element.contracts" type="text" id="contracts"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="premium" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.premium === false}">Premium:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.premium === false}" v-model="form_element.premium" type="text" id="premium"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="fee" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.fees === false}">Fees:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.fees === false}" v-model="form_element.fees" type="text" id="fee"/>
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
    import service from '../../../service';
    import dateTimeUtil from '../../../utils/time'
    import validation from "../../../utils/validation";
    import UpdateOptionApiModel from "../../../models/UpdateOptionApiModel";

    export default {
        name: "EditOptionTrade",
        props: {
            post: Object
        },
        data: function () {
            // console.debug(this.stock_model);
            return {
                is_form_readonly: false,
                token: this.post.token,

                form_element: {
                    symbol : this.post.model.stockSymbol,
                    id: this.post.model.transactionId,
                    date: dateTimeUtil.convertFromOffsetZuluToDisplay(this.post.model.date),
                    action : this.post.model.action,
                    stock_price : this.post.model.stockPrice,
                    strike_price: this.post.model.strikePrice,
                    type: this.post.model.optionType,
                    exp_date: dateTimeUtil.convertFromOffsetZuluToExpDateDisplay(this.post.model.expiryDate),
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
                    fees: true,
                    isValid: function () {
                        return this.date
                            && this.stock_price
                            && this.strike_price
                            && this.exp_date
                            && this.contracts
                            && this.premium
                            && this.fees;
                    }

                }
            }
        },
        methods: {
            closeModal: function () {
                this.$store.dispatch('option/hideModal');
            },

            submitAndClose: function () {
                if (this.checkForm() === false) {
                    return false;
                }

                let optionDto = new UpdateOptionApiModel(this.form_element.symbol);
                optionDto.date = dateTimeUtil.convertDateToOffsetDateTime(this.form_element.date);
                optionDto.stockPrice = this.form_element.stock_price;
                optionDto.strikePrice = this.form_element.strike_price;
                optionDto.expiryDate = dateTimeUtil.convertExpToOffsetDateTime(this.form_element.exp_date);

                optionDto.contracts = this.form_element.contracts;
                optionDto.premium = this.form_element.premium;
                optionDto.action = this.form_element.action;
                optionDto.optionType = this.form_element.type;
                optionDto.brokerFees = this.form_element.fees;

                optionDto.transactionId = this.form_element.id;

                service.editOptionTrade(optionDto, this.token).then(data => {
                    this.$store.dispatch('option/hideModal');
                    this.$store.dispatch('refreshData');
                });
            },
            
            checkForm: function () {
                this.form_validation.date = validation.isDate(this.form_element.date) !== false;
                this.form_validation.stock_price = validation.isNumber(this.form_element.stock_price) !== false;
                this.form_validation.strike_price = validation.isPositiveNumber(this.form_element.strike_price) !== false;
                this.form_validation.exp_date = validation.isExpDate(this.form_element.exp_date) !== false;
                this.form_validation.contracts = validation.isPositiveInteger(this.form_element.contracts) !== false;
                this.form_validation.premium = validation.isPositiveOrZeroNumber(this.form_element.premium) !== false;
                this.form_validation.fees = validation.isNumber(this.form_element.fees) !== false;

                return this.form_validation.isValid();
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
