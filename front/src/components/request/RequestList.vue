<template>
    <div>
        <h2>Liste des Demandes</h2>
        <button @click="refreshList">Rafraîchir</button>
        <table v-if="requests.length">
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
                        <button @click="$emit('accept', request)">Accepter</button>
                        <button @click="$emit('reject', request)">Refuser</button>
                    </td>
                </tr>
            </tbody>
        </table>
        <p v-else>Aucune demande trouvée.</p>
    </div>
</template>
  
<script>
export default {
    name: 'RequestList',
    props: ['requests'],
    emits: ['accept', 'reject']
};
</script>

<style>
h1,
h2 {
    text-align: center;
}

table {
    width: 100%;
    margin: 20px 0;
    border-collapse: collapse;
}

table,
th,
td {
    border: 1px solid #ddd;
}

th,
td {
    padding: 8px;
    text-align: center;
}

button {
    padding: 5px 10px;
    margin: 5px;
    cursor: pointer;
}
</style>
  