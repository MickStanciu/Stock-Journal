<template>
    <div class="modal" id="editStockModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Edit Stock Position</h3>
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

                        <div class="form-group row">
                            <label for="price" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.price === false}">Price:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.price === false}" v-model="form_element.price" type="text" id="price"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="quantity" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.quantity === false}">Quantity:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.quantity === false}" v-model="form_element.quantity" type="text" id="quantity"/>
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
                    <button type="button" class="btn btn-danger" data-dismiss="modal" v-on:click="submitAndClose()">Submit</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

    import dateTimeUtil from "../../utils/time";
    import validation from "../../utils/validation";
    import service from '../../service';
    import ShareApiModel from "../../models/ShareApiModel";

    export default {
        name: "EditStockTrade",
        props: {
            stock_model: Object
        },
        data: function () {
            return {
                form_element: {
                    symbol : this.stock_model.symbol,
                    id: this.stock_model.transactionId,
                    date: dateTimeUtil.convertFromOffsetZuluToDisplay(this.stock_model.date),
                    action: this.stock_model.action,
                    price: this.stock_model.price,
                    quantity: this.stock_model.quantity,
                    fees: this.stock_model.brokerFees,
                },

                form_validation: {
                    date: true,
                    price: true,
                    quantity: true,
                    fees: true,
                    isValid: function () {
                        return this.date
                            && this.price
                            && this.quantity
                            && this.fees;
                    }
                }
            }
        },
        methods: {
            closeModal: function () {
                this.$store.dispatch('hideModalWithoutRefresh');
            },

            submitAndClose: function () {
                if (this.checkForm() === false) {
                    return false;
                }

                let shareDto = new ShareApiModel(this.form_element.symbol);
                shareDto.date = dateTimeUtil.convertToOffsetDateTime(this.form_element.date);
                shareDto.action = this.form_element.action;
                shareDto.price = this.form_element.price;
                shareDto.quantity = this.form_element.quantity;
                shareDto.brokerFees = this.form_element.fees;
                shareDto.transactionId = this.form_element.id;

                service.editShareTrade(shareDto).then(data => {
                    this.$store.dispatch('hideModalWithRefresh');
                });
            },

            checkForm: function() {
                this.form_validation.date = validation.isDate(this.form_element.date) !== false;
                this.form_validation.price = validation.isNumber(this.form_element.price) !== false;
                this.form_validation.quantity = validation.isPositiveInteger(this.form_element.quantity) !== false;
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
