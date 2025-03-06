<script>
import axios from 'axios';

const API_URL = 'http://localhost:8081';

export default {
    data() {
        return {
            requests: [],
            academicYears: [],
            newRequest: { studentId: '', academicYearId: '' },
            editingRequest: null,
            showForm: false,  // Contrôle l'affichage du formulaire
        };
    },
    methods: {
        // Récupérer les demandes existantes
        async fetchRequests() {
            try {
                const response = await axios.get(`${API_URL}/requests`);
                this.requests = response.data;
            } catch (error) {
                console.error('Erreur lors de la récupération des demandes :', error);
            }
        },
        // Récupérer toutes les formations pour les afficher dans une liste déroulante
        async fetchAcademicYears() {
            try {
                const response = await axios.get(`${API_URL}/academicyears`);
                this.academicYears = response.data;
            } catch (error) {
                console.error('Erreur lors de la récupération des formations :', error);
            }
        },
        // Fonction pour accepter une demande
        async acceptRequest(request) {
            try {
                const response = await axios.post(`${API_URL}/academicyears/${request.academicYearId}/accept/${request.studentId}`);
                if (response.data) {
                    alert("Étudiant accepté avec succès");
                    this.fetchRequests();  // Rafraîchir la liste des demandes
                } else {
                    alert("Échec de l'acceptation de l'étudiant");
                }
            } catch (error) {
                console.error('Erreur lors de l\'acceptation de l\'étudiant :', error);
                alert("Erreur lors de l'acceptation de l'étudiant");
            }
        },
        // Fonction pour refuser une demande
        async rejectRequest(request) {
            try {
                const response = await axios.post(`${API_URL}/academicyears/${request.academicYearId}/reject/${request.studentId}`);
                if (response.data) {
                    alert("Étudiant refusé avec succès");
                    this.fetchRequests();  // Rafraîchir la liste des demandes
                } else {
                    alert("Échec du refus de l'étudiant");
                }
            } catch (error) {
                console.error('Erreur lors du refus de l\'étudiant :', error);
                alert("Erreur lors du refus de l'étudiant");
            }
        },
        // Fonction pour afficher le formulaire d'ajout
        showAddForm() {
            this.showForm = true;
            this.fetchAcademicYears();  // Récupérer les formations pour la liste déroulante
        },
        // Fonction pour soumettre la formulaire de demande
        async submitRequest() {
            try {
                const response = await axios.post(`${API_URL}/academicyears/${this.newRequest.academicYearId}/register/${this.newRequest.studentId}`);
                if (response.data) {
                    alert("Demande d'inscription envoyée avec succès");
                    this.fetchRequests();  // Rafraîchir la liste des demandes
                    this.showForm = false;  // Cacher le formulaire après soumission
                } else {
                    alert("Échec de l'envoi de la demande");
                }
            } catch (error) {
                console.error('Erreur lors de la soumission de la demande :', error);
                alert("Erreur lors de la soumission de la demande");
            }
        }
    },
    mounted() {
        this.fetchRequests();
    },
};
</script>

<template>
    <div>
        <h1>Gestion des Demandes d'Inscription</h1>

        <!-- Boutons Lister et Ajouter -->
        <button @click="fetchRequests">Lister les demandes</button>
        <button @click="showAddForm">Ajouter une demande</button>

        <!-- Affichage du tableau des demandes -->
        <table v-if="!showForm">
            <thead>
                <tr>
                    <th>ID Étudiant</th>
                    <th>Formation</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="request in requests" :key="request.id">
                    <td>{{ request.studentId }}</td>
                    <td>{{ request.academicYearId }}</td>
                    <td>
                        <button @click="acceptRequest(request)">Accepter</button>
                        <button @click="rejectRequest(request)">Refuser</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <!-- Formulaire d'ajout de demande -->
        <div v-if="showForm">
            <h2>Ajouter une demande d'inscription</h2>
            <form @submit.prevent="submitRequest">
                <label for="studentId">ID Étudiant:</label>
                <input v-model="newRequest.studentId" type="number" id="studentId" required />

                <label for="academicYearId">Formation:</label>
                <select v-model="newRequest.academicYearId" id="academicYearId" required>
                    <option v-for="year in academicYears" :key="year.id" :value="year.id">{{ year.name }}</option>
                </select>

                <button type="submit">Soumettre la demande</button>
            </form>
        </div>
    </div>
</template>
