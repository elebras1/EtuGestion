<template>
    <div>
        <h1>Gestion des Demandes</h1>
        <button v-if="!showForm" @click="showForm = true">Ajouter une Demande</button>

        <RequestList v-if="!showForm" :requests="requests" @accept="acceptRequest" @reject="rejectRequest" />
        <RequestForm v-if="showForm" @submitted="onSubmitted" @cancel="showForm = false" />
    </div>
</template>
  
<script>
import RequestList from './RequestList.vue';
import RequestForm from './RequestForm.vue';
import RequestService from '@/services/RequestService';

export default {
    name: 'RequestManager',
    components: { RequestList, RequestForm },
    data() {
        return {
            requests: [],
            showForm: false
        };
    },
    methods: {
        async loadRequests() {
            try {
                const response = await RequestService.fetchRequests();
                this.requests = response.data;
            } catch (error) {
                console.error("Erreur chargement demandes:", error);
            }
        },
        async acceptRequest(request) {
            try {
                await RequestService.acceptRequest(request);
                alert("Accepté !");
                this.loadRequests();
            } catch (error) {
                console.error("Erreur acceptation:", error);
            }
        },
        async rejectRequest(request) {
            try {
                await RequestService.rejectRequest(request);
                alert("Refusé !");
                this.loadRequests();
            } catch (error) {
                console.error("Erreur refus:", error);
            }
        },
        onSubmitted() {
            this.showForm = false;
            this.loadRequests();
        }
    },
    mounted() {
        this.loadRequests();
    }
};
</script>
  