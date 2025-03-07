<template>
    <div>
        <h2>Liste des Groupes</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Année Académique</th>
                    <th>Étudiants</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="group in groups" :key="group.id">
                    <td>{{ group.id }}</td>
                    <td>{{ group.name }}</td>
                    <td>{{ getAcademicYearName(group.academicYearId) }}</td>
                    <td>{{ group.studentsIds.join(', ') }}</td>
                    <td>
                        <button @click="editGroup(group.id)">Modifier</button>
                        <button @click="deleteGroup(group.id)">Supprimer</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    props: ['groups'],
    methods: {
        editGroup(id) {
            this.$emit('edit', id);
        },
        deleteGroup(id) {
            this.$emit('delete', id);
        },
        getAcademicYearName(academicYearId) {
            return this.$parent.academicYearData.find(year => year.id === academicYearId)?.name || 'Non défini';
        },
    }
};
</script>
