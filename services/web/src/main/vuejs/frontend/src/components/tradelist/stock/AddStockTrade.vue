<template>
    <div class="modal" id="addStockModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Add Stock Position for {{form_element.symbol}}</h3>
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
    import ShareApiModel from '../../../models/ShareApiModel'

    export default {
        name: "AddStockTrade",
        props: {
            post: Object
        },
        data: function () {
            return {
                form_element: {
                    symbol : this.post.symbol,
                    date: dateTimeUtil.convertFromOffsetZuluToDisplay(),
                    action : 'BUY',
                    price: '0.00',
                    quantity: 0,
                    fees: '0.00',
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

                let shareDto = new ShareApiModel(this.form_element.symbol);
                shareDto.date = dateTimeUtil.convertToOffsetDateTime(this.form_element.date);
                shareDto.action = this.form_element.action;
                shareDto.price = this.form_element.price;
                shareDto.quantity = this.form_element.quantity;
                shareDto.brokerFees = this.form_element.fees;

                service.recordShareTrade(shareDto).then(data => {
                  if (data === null) {
                      this.$store.dispatch('stock/hideModal');
                      this.$store.dispatch('showErrorModal');
                  } else {
                      this.$store.dispatch('stock/hideModal');
                      this.$store.dispatch('refreshData');
                  }
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
