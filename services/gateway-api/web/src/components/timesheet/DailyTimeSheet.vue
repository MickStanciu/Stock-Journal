<template>
    <div class="container">
        <div class="row row_default" v-for="(item, idx) in slots" v-bind:key="idx" v-bind:class="rowClass(idx)">
            {{item}} {{idx}}
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
        methods: {
            rowClass(idx) {
                if (idx%2) {
                    return 'row_even';
                } else {
                    return 'row_odd';
                }
            }
        }
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
    .row_default {
        padding: 2px;

    }
    .row_odd {
        background-color: #e7e7fb;
    }

    .row_even {
    }
</style>