<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const URL = 'http://localhost:8080'

const academicYears = ref([])
const selectedAcademicYear = ref(null)

const fetchAcademicYears = async () => {
    try {
        const response = await axios.get(URL + '/academicyears')
        academicYears.value = response.data
    } catch (error) {
        console.error("Erreur lors de la récupération des formations :", error)
    }
}

const getDetails = async (id) => {
    try {
        const [groupsResponse, teachingUnitsResponse, academicYearResponse] = await Promise.all([
            axios.get(URL + `/academicyears/${id}/groups`),
            axios.get(URL + `/academicyears/${id}/teachingunits`),
            axios.get(URL + `/academicyears/${id}`)
        ])

        selectedAcademicYear.value = {
            ...academicYearResponse.data,
            groups: groupsResponse.data,
            teachingUnits: teachingUnitsResponse.data
        }
    } catch (error) {
        console.error("Erreur lors de la récupération des détails de la formation :", error)
    }
}

onMounted(() => {
    fetchAcademicYears()
})
</script>

<template>
    <div>
        <h1>Liste des formations</h1>
        <ul>
            <li v-for="year in academicYears" :key="year.id">
                <!-- Informations basiques de la formation -->
                <p><strong>Nom :</strong> {{ year.name }}</p>
                <p><strong>Taille max TP :</strong> {{ year.praticalWorkSize }}</p>
                <p><strong>Taille max TD :</strong> {{ year.directedWorkSize }}</p>
                <p><strong>Nombre d'UE optionnel :</strong> {{ year.numberOptionalTeachingUnit }}</p>
                <p v-if="year.responsibleId !== undefined">
                    <strong>ID Responsable :</strong> {{ year.responsibleId }}
                </p>
                <button @click="getDetails(year.id)">Détails</button>

                <!-- Détails affichés en dessous de la formation sélectionnée -->
                <div v-if="selectedAcademicYear && selectedAcademicYear.id === year.id">
                    <h2>Détails pour : {{ selectedAcademicYear.name }}</h2>

                    <div>
                        <h3>Groupes</h3>
                        <ul>
                            <li v-for="group in selectedAcademicYear.groups" :key="group.id">
                                <p><strong>Nom :</strong> {{ group.name }}</p>
                                <p><strong>ID Formation :</strong> {{ group.academicYearId }}</p>
                                <p>
                                    <strong>IDs étudiants :</strong>
                                    <span v-if="group.studentsIds && group.studentsIds.length">
                                        {{ group.studentsIds.join(', ') }}
                                    </span>
                                    <span v-else>Aucun étudiant</span>
                                </p>
                            </li>
                        </ul>
                    </div>

                    <div>
                        <h3>Unités d'enseignement</h3>
                        <ul>
                            <li v-for="unit in selectedAcademicYear.teachingUnits" :key="unit.id">
                                <p><strong>Nom :</strong> {{ unit.name }}</p>
                                <p>
                                    <strong>Obligatoire :</strong>
                                    {{ unit.isRequired ? 'Oui' : 'Non' }}
                                </p>
                                <p><strong>Capacité :</strong> {{ unit.capacity }}</p>
                                <p><strong>ID Formation :</strong> {{ unit.academicYearId }}</p>
                                <p v-if="unit.responsibleId !== undefined">
                                    <strong>ID Responsable :</strong> {{ unit.responsibleId }}
                                </p>
                                <p>
                                    <strong>IDs étudiants :</strong>
                                    <span v-if="unit.studentsIds && unit.studentsIds.length">
                                        {{ unit.studentsIds.join(', ') }}
                                    </span>
                                    <span v-else>Aucun étudiant</span>
                                </p>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</template>

<style scoped>
ul {
    list-style: none;
    padding: 0;
}

li {
    border: 1px solid #ddd;
    margin: 10px;
    padding: 10px;
}

button {
    margin-top: 10px;
    cursor: pointer;
}
</style>
