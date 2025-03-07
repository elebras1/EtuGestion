<template>
    <div>
        <h2>{{ isEditing ? 'Modifier' : 'Créer' }} un groupe</h2>
        <form @submit.prevent="submitForm">
            <div>
                <label for="name">Nom du groupe:</label>
                <input type="text" v-model="groupData.name" required />
            </div>
            <div>
                <label for="academicYearId">Année académique:</label>
                <select v-model="groupData.academicYearId" required>
                    <option v-for="year in academicYears" :key="year.id" :value="year.id">{{ year.name }}</option>
                </select>
            </div>
            <div>
                <label for="studentsIdsString">IDs des étudiants (séparés par des virgules):</label>
                <input type="text" v-model="groupData.studentsIdsString" placeholder="ex: 1, 2, 3" />
            </div>
            <button type="submit">{{ isEditing ? 'Mettre à jour' : 'Créer' }} le groupe</button>
            <button type="button" @click="$emit('cancel')">Annuler</button>
        </form>
    </div>
</template>

<script>
export default {
    props: ['groupData', 'academicYears'],
    computed: {
        isEditing() {
            return this.groupData && this.groupData.id;
        }
    },
    methods: {
        submitForm() {
            this.$emit('submit', { ...this.groupData });
        }
    }
};
</script>
