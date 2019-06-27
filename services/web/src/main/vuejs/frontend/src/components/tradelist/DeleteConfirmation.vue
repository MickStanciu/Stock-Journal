<template>
    <div class="modal" id="deleteModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Confirmation</h3>
                </div>

                <div class="modal-body">
                    <p>Are you sure you want to delete this record?</p>
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
    export default {
        name: "DeleteConfirmation",

        methods: {
            closeModal: function () {
                this.$store.dispatch('hideModalWithoutRefresh');
            },

            submitAndClose: function () {
                this.$store.dispatch('hideModalWithoutRefresh');
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