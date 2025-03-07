<template>
    <div class="form-section">
        <h2>{{ isEditing ? 'Modification UE' : 'Création UE' }}</h2>
        <form @submit.prevent="submitForm">
            <div class="form-group">
                <label>Nom:</label>
                <input :value="teachingUnit.name" @input="updateField('name', $event.target.value)" required>
            </div>

            <div class="form-group">
                <label>Obligatoire:</label>
                <input type="checkbox" :checked="teachingUnit.isRequired" 
                    @change="updateField('isRequired', $event.target.checked)">
            </div>

            <div class="form-group">
                <label>Capacité:</label>
                <input type="number" :value="teachingUnit.capacity" 
                    @input="updateField('capacity', $event.target.value)" min="1" required>
            </div>

            <div class="form-group">
                <label>Formation:</label>
                <select :value="teachingUnit.academicYearId" 
                    @change="updateField('academicYearId', $event.target.value)" required>
                    <option v-for="year in academicYears" :key="year.id" :value="year.id">
                        {{ year.name }}
                    </option>
                </select>
            </div>

            <div class="form-group">
                <label>Responsable:</label>
                <select :value="teachingUnit.responsibleId" 
                    @change="updateField('responsibleId', $event.target.value)">
                    <option value="" disabled>Choisir un responsable</option>
                    <option v-for="manager in managers" :key="manager.id" :value="manager.id">
                        {{ manager.prenom }} {{ manager.nom }}
                    </option>
                </select>
            </div>

            <button type="submit">{{ isEditing ? 'Modifier' : 'Créer' }}</button>
            <button type="button" @click="cancel">Annuler</button>
        </form>
    </div>
</template>

<script>
export default {
    props: {
        teachingUnit: {
            type: Object,
            required: true
        },
        academicYears: {
            type: Array,
            required: true
        },
        managers: {
            type: Array,
            required: true
        },
        isEditing: {
            type: Boolean,
            default: false
        }
    },
    methods: {
        updateField(field, value) {
            this.$emit('update-field', field, value);
        },
        submitForm() {
            this.$emit('submit');
        },
        cancel() {
            this.$emit('cancel');
        }
    }
}
</script>

<style scoped>
.form-section {
    margin: 20px 0;
    padding: 15px;
    border: 1px solid #ddd;
}

.form-group {
    margin: 10px 0;
}
</style>