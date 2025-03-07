<template>
    <div>
        <h2>Ajouter une Demande</h2>
        <form @submit.prevent="submit">
            <label>ID Étudiant :</label>
            <input v-model="newRequest.studentId" type="number" required />

            <label>Formation :</label>
            <select v-model="newRequest.academicYearId" required>
                <option v-for="year in academicYears" :key="year.id" :value="year.id">{{ year.name }}</option>
            </select>

            <button type="submit">Soumettre</button>
            <button type="button" @click="$emit('cancel')">Annuler</button>
        </form>
    </div>
</template>
  
<script>
import RequestService from '@/services/RequestService';

export default {
    name: 'RequestForm',
    emits: ['submitted', 'cancel'],
    data() {
        return {
            newRequest: { studentId: '', academicYearId: '' },
            academicYears: []
        };
    },
    methods: {
        async fetchAcademicYears() {
            try {
                const response = await RequestService.fetchAcademicYears();
                this.academicYears = response.data;
            } catch (error) {
                console.error("Erreur chargement formations:", error);
            }
        },
        async submit() {
            try {
                await RequestService.submitRequest(this.newRequest);
                alert("Demande envoyée !");
                this.$emit('submitted');
            } catch (error) {
                console.error("Erreur soumission:", error);
            }
        }
    },
    mounted() {
        this.fetchAcademicYears();
    }
};
</script>
  