<script>
import axios from 'axios';

const API_URL = 'http://localhost:8081';
const URLmanager = 'http://localhost:8080';

export default {
    data() {
        return {
            teachingUnits: [],
            academicYears: [],
            managers: [], // Liste des responsables
            newTeachingUnit: {
                name: '',
                isRequired: false,
                capacity: 30,
                academicYearId: null,
                responsibleId: null
            },
            editingTeachingUnit: null,
            showForm: false,
            studentRegistrationIds: {} // Utilisé pour stocker l'ID étudiant par UE
        };
    },
    methods: {
        updateTeachingUnitField(field, value) {
            if (this.editingTeachingUnit) {
                this.editingTeachingUnit[field] = value;
            } else {
                this.newTeachingUnit[field] = value;
            }
        },

        async fetchTeachingUnits() {
            try {
                const response = await axios.get(`${API_URL}/teachingunits`);
                this.teachingUnits = response.data;
            } catch (error) {
                console.error('Erreur lors de la récupération des UEs :', error);
            }
        },

        async fetchAcademicYears() {
            try {
                const response = await axios.get(`${API_URL}/academicyears`);
                this.academicYears = response.data;
            } catch (error) {
                console.error('Erreur lors de la récupération des formations :', error);
            }
        },

        // Récupère les responsables
        async fetchManagers() {
            try {
                const response = await axios.get(`${URLmanager}/managers`);
                this.managers = response.data;
            } catch (error) {
                console.error('Erreur lors de la récupération des responsables :', error);
            }
        },

        async submitForm() {
            try {
                const payload = {
                    ...this.editingTeachingUnit || this.newTeachingUnit,
                    academicYearId: parseInt(this.editingTeachingUnit?.academicYearId || this.newTeachingUnit.academicYearId)
                };

                if (this.editingTeachingUnit) {
                    await axios.put(`${API_URL}/teachingunits/${this.editingTeachingUnit.id}`, payload);
                } else {
                    await axios.post(`${API_URL}/teachingunits`, payload);
                }

                this.fetchTeachingUnits();
                this.resetForm();
            } catch (error) {
                console.error('Erreur lors de la soumission :', error);
                alert("Erreur lors de l'opération");
            }
        },

        async deleteTeachingUnit(id) {
            if (confirm("Confirmer la suppression ?")) {
                try {
                    await axios.delete(`${API_URL}/teachingunits/${id}`);
                    this.fetchTeachingUnits();
                } catch (error) {
                    console.error('Erreur lors de la suppression :', error);
                }
            }
        },

        async manageStudent(unit, action) {
            const studentId = this.studentRegistrationIds[unit.id];
            if (!studentId) return alert("ID étudiant requis");

            try {
                const endpoint = `${API_URL}/teachingunits/${unit.id}/${action}/${studentId}`;
                await axios.post(endpoint);
                this.fetchTeachingUnits();
                this.studentRegistrationIds[unit.id] = ''; // Réinitialiser l'ID étudiant après l'action
            } catch (error) {
                console.error(`Erreur lors de la ${action} :`, error);
                alert(`Échec de la ${action}`);
            }
        },

        resetForm() {
            this.showForm = false;
            this.editingTeachingUnit = null;
            this.newTeachingUnit = {
                name: '',
                isRequired: false,
                capacity: 30,
                academicYearId: null,
                responsibleId: null
            };
        }
    },
    async mounted() {
        await this.fetchTeachingUnits();
        await this.fetchAcademicYears();
        await this.fetchManagers(); // Récupère les responsables
    }
};
</script>

<template>
    <div>
        <h1>Gestion des Unités d'Enseignement</h1>

        <button @click="showForm = true">Nouvelle UE</button>

        <!-- Formulaire UE -->
        <div v-if="showForm" class="form-section">
            <h2>{{ editingTeachingUnit ? 'Modification UE' : 'Création UE' }}</h2>
            <form @submit.prevent="submitForm">
                <div class="form-group">
                    <label>Nom:</label>
                    <input :value="editingTeachingUnit?.name || newTeachingUnit.name"
                        @input="updateTeachingUnitField('name', $event.target.value)" required>
                </div>

                <div class="form-group">
                    <label>Obligatoire:</label>
                    <input type="checkbox" :checked="editingTeachingUnit?.isRequired || newTeachingUnit.isRequired"
                        @change="updateTeachingUnitField('isRequired', $event.target.checked)">
                </div>

                <div class="form-group">
                    <label>Capacité:</label>
                    <input type="number" :value="editingTeachingUnit?.capacity || newTeachingUnit.capacity"
                        @input="updateTeachingUnitField('capacity', $event.target.value)" min="1" required>
                </div>

                <div class="form-group">
                    <label>Formation:</label>
                    <select :value="editingTeachingUnit?.academicYearId || newTeachingUnit.academicYearId"
                        @change="updateTeachingUnitField('academicYearId', $event.target.value)" required>
                        <option v-for="year in academicYears" :key="year.id" :value="year.id">
                            {{ year.name }}
                        </option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Responsable:</label>
                    <select :value="editingTeachingUnit?.responsibleId || newTeachingUnit.responsibleId"
                        @change="updateTeachingUnitField('responsibleId', $event.target.value)">
                        <option value="" disabled>Choisir un responsable</option>
                        <option v-for="manager in managers" :key="manager.id" :value="manager.id">
                            {{ manager.name }}
                        </option>
                    </select>
                </div>

                <button type="submit">{{ editingTeachingUnit ? 'Modifier' : 'Créer' }}</button>
                <button type="button" @click="resetForm">Annuler</button>
            </form>
        </div>

        <!-- Liste des UEs -->
        <table>
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Obligatoire</th>
                    <th>Capacité</th>
                    <th>Formation</th>
                    <th>Responsable</th>
                    <th>Étudiants inscrits</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="unit in teachingUnits" :key="unit.id">
                    <td>{{ unit.name }}</td>
                    <td>{{ unit.isRequired ? 'Oui' : 'Non' }}</td>
                    <td>{{ unit.capacity }}</td>
                    <td>{{academicYears.find(y => y.id === unit.academicYearId)?.name}}</td>
                    <td>{{managers.find(manager => manager.id === unit.responsibleId)?.name || 'Non assigné'}}</td>
                    <td>{{ unit.studentsIds?.join(', ') || '-' }}</td>
                    <td>
                        <button @click="showForm = true; editingTeachingUnit = { ...unit }">Éditer</button>
                        <button @click="deleteTeachingUnit(unit.id)">Supprimer</button>
                        <div class="student-management">
                            <input :value="studentRegistrationIds[unit.id] || ''"
                                @input="studentRegistrationIds[unit.id] = $event.target.value" type="number"
                                placeholder="ID Étudiant">
                            <button @click="manageStudent(unit, 'register')">Inscrire</button>
                            <button @click="manageStudent(unit, 'unregister')">Désinscrire</button>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<style scoped>
.form-section {
    margin: 20px 0;
    padding: 15px;
    border: 1px solid #ddd;
}

.form-group {
    margin: 10px 0;
}

table {
    width: 100%;
    margin-top: 20px;
    border-collapse: collapse;
}

th,
td {
    padding: 10px;
    border: 1px solid #ddd;
    text-align: left;
}

.student-management {
    margin-top: 5px;
    display: flex;
    gap: 5px;
}
</style>
