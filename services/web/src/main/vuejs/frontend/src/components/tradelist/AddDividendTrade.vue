<template>
    <div class="modal" id="addDividendModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Add Dividend Position for {{form_element.symbol}}</h3>
                </div>

                <div class="modal-body">
                    <form>
                        <div class="form-group row">
                            <label for="date" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.date === false}">Date:</label>
                            <div class="col-sm-9">
                                <input v-model="form_element.date" class="form-control" v-bind:class="{'is-invalid': form_validation.date === false}" type="text" placeholder="dd-MMM-yyyy" id="date"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="dividend" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.dividend === false}">Dividend:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.dividend === false}" v-model="form_element.dividend" type="text" id="dividend"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="quantity" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.quantity === false}">Quantity:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.quantity === false}" v-model="form_element.quantity" type="text" id="quantity"/>
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
    import validation from "../../utils/validation";
    import DividendApiModel from "../../models/DividendApiModel";

    export default {
        name: "AddDividendTrade",
        props: ['post'],
        data: function () {
            return {
                form_element: {
                    symbol : this.post.symbol,
                    date: dateTimeUtil.convertFromOffsetZuluToDisplay(),
                    dividend: '0.00',
                    quantity: 0
                },

                form_validation: {
                    date: true,
                    dividend: true,
                    quantity: true,
                    isValid: function () {
                        return this.date && this.dividend && this.quantity;
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

                let dividendDto = new DividendApiModel(this.form_element.symbol);
                dividendDto.date = dateTimeUtil.convertToOffsetDateTime(this.form_element.date);
                dividendDto.dividend = this.form_element.dividend;
                dividendDto.quantity = this.form_element.quantity;

                service.recordDividendTrade(dividendDto).then(data => {
                    if (data === null) {
                        this.$store.dispatch('hideModalWithError');
                    } else {
                        this.$store.dispatch('hideModalWithRefresh');
                    }
                });
            },

            checkForm: function() {
                this.form_validation.date = validation.isDate(this.form_element.date) !== false;
                this.form_validation.dividend = validation.isPositiveNumber(this.form_element.dividend) !== false;
                this.form_validation.quantity = validation.isPositiveInteger(this.form_element.quantity) !== false;

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