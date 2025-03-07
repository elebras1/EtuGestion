<template>
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
                <td>{{ getAcademicYearName(unit.academicYearId) }}</td>
                <td>{{ getManagerEmail(unit.responsibleId) }}</td>
                <td>{{ unit.studentsIds?.join(', ') || '-' }}</td>
                <td>
                    <button @click="$emit('edit', unit)">Éditer</button>
                    <button @click="$emit('delete', unit.id)">Supprimer</button>
                    <div class="student-management">
                        <input :value="studentIds[unit.id] || ''" @input="updateStudentId(unit.id, $event.target.value)"
                            type="number" placeholder="ID Étudiant">
                        <button @click="registerStudent(unit.id)">Inscrire</button>
                        <button @click="unregisterStudent(unit.id)">Désinscrire</button>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<script>
export default {
    props: {
        teachingUnits: {
            type: Array,
            required: true
        },
        academicYears: {
            type: Array,
            required: true
        },
        managers: {
            type: Array,
            required: true
        }
    },
    data() {
        return {
            studentIds: {}
        };
    },
    methods: {
        getAcademicYearName(id) {
            const year = this.academicYears.find(y => y.id === id);
            return year ? year.name : 'Non trouvé';
        },
        getManagerEmail(id) {
            const manager = this.managers.find(m => m.id === id);
            return manager ? manager.email : 'Non assigné';
        },
        updateStudentId(unitId, value) {
            this.studentIds[unitId] = value;
        },
        registerStudent(unitId) {
            this.$emit('register-student', unitId, this.studentIds[unitId]);
            this.clearInput(unitId);
        },
        unregisterStudent(unitId) {
            this.$emit('unregister-student', unitId, this.studentIds[unitId]);
            this.clearInput(unitId);
        },
        clearInput(unitId) {
            this.studentIds[unitId] = '';
        }
    }
}
</script>

<style scoped>
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
