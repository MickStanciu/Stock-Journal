<template>
    <div class="modal" id="editStockModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Edit Stock Position for {{form_element.symbol}}</h3>
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
                            <label for="price" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.price === false}">Price:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.price === false}" v-model="form_element.price" type="text" id="price"/>
                            </div>
                        </fieldset>

                        <fieldset class="form-group row">
                            <label for="quantity" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.quantity === false}">Quantity:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.quantity === false}" v-model="form_element.quantity" type="text" id="quantity"/>
                            </div>
                        </fieldset>

                        <fieldset class="form-group row">
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

    import dateTimeUtil from "../../../utils/time";
    import validation from "../../../utils/validation";
    import service from '../../../service';
    import UpdateShareApiModel from "../../../models/UpdateShareApiModel";

    export default {
        name: "EditStockTrade",
        props: {
            post: Object
        },
        data: function () {
            return {
                token: this.post.token,

                form_element: {
                    symbol : this.post.model.symbol,
                    id: this.post.model.transactionId,
                    date: dateTimeUtil.convertFromOffsetZuluToDisplay(this.post.model.date),
                    action: this.post.model.action,
                    price: this.post.model.price,
                    quantity: this.post.model.quantity,
                    fees: this.post.model.brokerFees,
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
                this.$store.dispatch('stock/hideModal');
            },

            submitAndClose: function () {
                if (this.checkForm() === false) {
                    return false;
                }

                let shareDto = new UpdateShareApiModel(this.form_element.symbol);
                shareDto.date = dateTimeUtil.convertDateToOffsetDateTime(this.form_element.date);
                shareDto.action = this.form_element.action;
                shareDto.price = this.form_element.price;
                shareDto.quantity = this.form_element.quantity;
                shareDto.brokerFees = this.form_element.fees;
                shareDto.transactionId = this.form_element.id;

                service.editShareTrade(shareDto, this.token).then(data => {
                    this.$store.dispatch('stock/hideModal');
                    this.$store.dispatch('refreshData');
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
