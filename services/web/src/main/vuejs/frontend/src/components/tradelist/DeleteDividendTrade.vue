<template>
    <div class="modal" id="deleteDividendModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Delete Dividend Position</h3>
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
                            <label for="dividend" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.dividend === false}">Dividend:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.dividend === false}" v-model="form_element.dividend" type="text" id="dividend"/>
                            </div>
                        </fieldset>

                        <fieldset class="form-group row" v-bind:disabled="this.is_form_readonly">
                            <label for="quantity" class="col-sm-3 col-form-label" v-bind:class="{'text-danger': form_validation.quantity === false}">Quantity:</label>
                            <div class="col-sm-9">
                                <input class="form-control" v-bind:class="{'is-invalid': form_validation.quantity === false}" v-model="form_element.quantity" type="text" id="quantity"/>
                            </div>
                        </fieldset>
                    </form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-info" v-on:click="closeModal()">Close</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal" v-on:click="submitAndClose()">Submit</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import service from '../../service';
    import dateTimeUtil from '../../utils/time'

    export default {
        name: "DeleteDividendTrade",
        props: {
            post: Object
        },
        data: function () {
            return {
                is_form_readonly: true,

                form_element: {
                    symbol : this.post.model.symbol,
                    date: dateTimeUtil.convertFromOffsetZuluToDisplay(this.post.model.date),
                    dividend: this.post.model.dividend,
                    quantity: this.post.model.quantity
                },

                form_validation: {
                    date: true,
                    quantity: true,
                    dividend: true
                }
            }
        },
        methods: {
            closeModal: function () {
                this.$store.dispatch('hideModalWithoutRefresh');
            },

            submitAndClose: function () {
                service.deleteDividendRecord(this.post.model)
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

    .form-control {
        font-size: 0.75rem;
    }
</style>