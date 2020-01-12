<template>
    <div class="modal" id="exampleModal" v-on:click="closeModal()">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Fuckin' Shit ... didn't worked</h3>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "AddError",
        methods: {
            closeModal: function () {
                console.log('error modal was pressed');
                this.$store.dispatch('hideErrorModal');
            },
        }
    }
</script>

<style scoped>
    .modal {
        display: block;
        cursor: pointer;
    }

    .modal-header {
        justify-content: center;
    }
</style>