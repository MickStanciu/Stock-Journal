<template>
    <form>
        <fieldset class="form-group row" v-bind:disabled="isReadOnly">
            <label for="date" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.date === false}">Date:</label>
            <div class="col-sm-9">
                <input v-model="form_element.date" class="form-control" v-bind:class="{'is-invalid': form_validation.date === false}" type="text" placeholder="dd-MMM-yyyy" id="date"/>
            </div>
        </fieldset>

        <fieldset class="form-group row" v-bind:disabled="isReadOnly">
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

        <fieldset class="form-group row" v-bind:disabled="isReadOnly">
            <label for="price" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.price === false}">Price:</label>
            <div class="col-sm-9">
                <input class="form-control" v-bind:class="{'is-invalid': form_validation.price === false}" v-model="form_element.price" type="text" id="price"/>
            </div>
        </fieldset>

        <fieldset class="form-group row" v-bind:disabled="isReadOnly">
            <label for="quantity" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.quantity === false}">Quantity:</label>
            <div class="col-sm-9">
                <input class="form-control" v-bind:class="{'is-invalid': form_validation.quantity === false}" v-model="form_element.quantity" type="text" id="quantity"/>
            </div>
        </fieldset>

        <fieldset class="form-group row" v-bind:disabled="isReadOnly">
            <label for="fee" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.fees === false}">Fees:</label>
            <div class="col-sm-9">
                <input class="form-control" v-bind:class="{'is-invalid': form_validation.fees === false}" v-model="form_element.brokerFees" type="text" id="fee"/>
            </div>
        </fieldset>
    </form>
</template>

<script>
    import dateTimeUtil from "../../utils/time";

    export default {
        name: "StockFragment",
        props: ['post'],
        data: function () {
            return {
                currency: 'USD',
                isReadOnly: true,
                form_element: {
                    date: this.formattedDate(),
                    action: this.post.model.action,
                    price: this.post.model.price,
                    quantity: this.post.model.quantity,
                    brokerFees: this.post.model.brokerFees,
                },

                form_validation: {
                    date: true,
                    price: true,
                    quantity: true,
                    fees: true
                }
            }
        },
        methods: {
            formattedDate: function () {
                return dateTimeUtil.convertFromOffsetZuluToDisplay(this.post.model.date);
            }
        }
    }
</script>

<style scoped>
    .form-control {
        font-size: 0.75rem;
    }
</style>