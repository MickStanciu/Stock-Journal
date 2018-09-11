<template>
    <div class="container">
        <div class="row" v-for="(item) in slots">
            {{item}}
        </div>
    </div>

</template>

<script>
    export default {
        name: "DailyTimeSheet",
        data: function() {
            return {
                slots: ts_items
            }
        },
    }

    class Item {
        constructor(enabled) {
            this.enabled = enabled;
        }
    }


    const ts_items = [];
    ts_items.push(new Item(true));
    ts_items.push(new Item(true));
    ts_items.push(new Item(false));
    ts_items.push(new Item(false));
    ts_items.push(new Item(true));
    ts_items.push(new Item(true));

</script>

<style scoped>

</style>