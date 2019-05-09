<template>
    <div>
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

            <div class="row">
                <div class="col">
                    <button type="button" class="btn btn-primary mb-2" v-on:click="clickCancel">Cancel</button>
                </div>
                <div class="col">
                    <button type="button" class="btn btn-primary mb-2" v-on:click="clickSubmit">Submit</button>
                </div>
            </div>
        </form>

        <Br/>
        <Br/>
        <Br/>
        <Br/>
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
            clickCancel: function () {
                console.log('cancel');
            },
            clickSubmit: function () {
                console.log('submit');
            },
        }
    }
</script>

<style scoped>

</style>