<template>
    <div>
        <h1>Gestion des Unités d'Enseignement</h1>

        <button @click="showForm = true">Nouvelle UE</button>

        <teaching-unit-form v-if="showForm" :teaching-unit="editingTeachingUnit || newTeachingUnit"
            :academic-years="academicYears" :managers="managers" :is-editing="!!editingTeachingUnit"
            @update-field="updateTeachingUnitField" @submit="submitForm" @cancel="resetForm" />

        <teaching-unit-list :teaching-units="teachingUnits" :academic-years="academicYears" :managers="managers"
            @edit="startEditing" @delete="deleteTeachingUnit" @register-student="registerStudent"
            @unregister-student="unregisterStudent" />
    </div>
</template>

<script>
import TeachingUnitService from '@/services/TeachingUnitService';
import TeachingUnitForm from './TeachingUnitForm.vue';
import TeachingUnitList from './TeachingUnitList.vue';

export default {
    components: {
        TeachingUnitForm,
        TeachingUnitList
    },
    data() {
        return {
            teachingUnits: [],
            academicYears: [],
            managers: [],
            newTeachingUnit: {
                name: '',
                isRequired: false,
                capacity: 30,
                academicYearId: null,
                responsibleId: null
            },
            editingTeachingUnit: null,
            showForm: false
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
                const response = await TeachingUnitService.fetchTeachingUnits();
                this.teachingUnits = response.data;
            } catch (error) {
                console.error('Erreur lors de la récupération des UEs :', error);
            }
        },

        async fetchAcademicYears() {
            try {
                const response = await TeachingUnitService.fetchAcademicYears();
                this.academicYears = response.data;
            } catch (error) {
                console.error('Erreur lors de la récupération des formations :', error);
            }
        },

        async fetchManagers() {
            try {
                const response = await TeachingUnitService.fetchManagers();
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
                    await TeachingUnitService.updateTeachingUnit(this.editingTeachingUnit.id, payload);
                } else {
                    await TeachingUnitService.createTeachingUnit(payload);
                }

                this.fetchTeachingUnits();
                this.resetForm();
            } catch (error) {
                console.error('Erreur lors de la soumission :', error);
                alert("Erreur lors de l'opération");
            }
        },

        startEditing(unit) {
            this.showForm = true;
            this.editingTeachingUnit = { ...unit };
        },

        async deleteTeachingUnit(id) {
            if (confirm("Confirmer la suppression ?")) {
                try {
                    await TeachingUnitService.deleteTeachingUnit(id);
                    this.fetchTeachingUnits();
                } catch (error) {
                    console.error('Erreur lors de la suppression :', error);
                }
            }
        },

        async registerStudent(unitId, studentId) {
            if (!studentId) return alert("ID étudiant requis");

            try {
                await TeachingUnitService.registerStudent(unitId, studentId);
                this.fetchTeachingUnits();
            } catch (error) {
                console.error('Erreur lors de l\'inscription :', error);
                alert('Échec de l\'inscription');
            }
        },

        async unregisterStudent(unitId, studentId) {
            if (!studentId) return alert("ID étudiant requis");

            try {
                await TeachingUnitService.unregisterStudent(unitId, studentId);
                this.fetchTeachingUnits();
            } catch (error) {
                console.error('Erreur lors de la désinscription :', error);
                alert('Échec de la désinscription');
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
        await this.fetchManagers();
    }
};
</script>