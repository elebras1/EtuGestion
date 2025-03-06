<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const URL = 'http://localhost:8081'
const URLmanager = 'http://localhost:8080'

const academicYears = ref([])
const selectedAcademicYear = ref(null)
const managers = ref([])
const newAcademicYear = ref({
    name: '',
    praticalWorkSize: 0,
    directedWorkSize: 0,
    numberOptionalTeachingUnit: 0,
    responsibleId: undefined
})
const editAcademicYear = ref({
    name: '',
    praticalWorkSize: 0,
    directedWorkSize: 0,
    numberOptionalTeachingUnit: 0,
    responsibleId: undefined
})

const showAddForm = ref(false)
const showScraperInfo = ref(false)
const showEditForm = ref(false)
const errorMessage = ref('')

// Récupérer les formations depuis le serveur
const fetchAcademicYears = async () => {
    try {
        const response = await axios.get(URL + '/academicyears')
        academicYears.value = response.data
    } catch (error) {
        errorMessage.value = "Erreur lors de la récupération des formations : " + error.message
        console.error(errorMessage.value)
    }
}

// Récupérer la liste des managers
const fetchManagers = async () => {
    try {
        const response = await axios.get(URLmanager + '/managers')
        managers.value = response.data
    } catch (error) {
        errorMessage.value = "Erreur lors de la récupération des managers : " + error.message
        console.error(errorMessage.value)
    }
}

// Obtenir les détails d'une formation spécifique
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
        errorMessage.value = "Erreur lors de la récupération des détails de la formation : " + error.message
        console.error(errorMessage.value)
    }
}

// Fonction pour afficher le formulaire d'ajout
const toggleAddForm = () => {
    showAddForm.value = !showAddForm.value
    showScraperInfo.value = false // Masque la vue scraper
}

// Fonction pour démarrer le scraper
const startScraper = async () => {
    try {
        await axios.post(URL + '/academicyears/scraper')
        await fetchAcademicYears()
        showScraperInfo.value = true
        showAddForm.value = false
    } catch (error) {
        errorMessage.value = "Erreur lors du démarrage du scraper : " + error.message
        console.error(errorMessage.value)
    }
}


// Fonction pour afficher les formations
const showAcademicYears = () => {
    showAddForm.value = false
    showScraperInfo.value = false
    fetchAcademicYears()
}

// Fonction pour modifier une formation
const toggleEditForm = (academicYear) => {
    showEditForm.value = !showEditForm.value
    if (showEditForm.value) {
        editAcademicYear.value = { ...academicYear }
    }
}

// Fonction pour ajouter une nouvelle formation
const addAcademicYear = async () => {
    try {
        const response = await axios.post(URL + '/academicyears', newAcademicYear.value)
        const addedYear = response.data
        // Ajouter la nouvelle formation à la liste
        academicYears.value.push(addedYear)
        // Réinitialiser le formulaire
        newAcademicYear.value = {
            name: '',
            praticalWorkSize: 0,
            directedWorkSize: 0,
            numberOptionalTeachingUnit: 0,
            responsibleId: undefined
        }
        // Masquer le formulaire d'ajout après l'ajout
        showAddForm.value = false
    } catch (error) {
        errorMessage.value = "Erreur lors de l'ajout de la formation : " + error.message
        console.error(errorMessage.value)
    }
}

// Fonction pour mettre à jour une formation
const updateAcademicYear = async () => {
    try {
        const response = await axios.put(URL + `/academicyears/${editAcademicYear.value.id}`, editAcademicYear.value)
        const updatedYear = response.data
        // Mettre à jour la formation dans la liste après la mise à jour
        const index = academicYears.value.findIndex(year => year.id === updatedYear.id)
        if (index !== -1) {
            academicYears.value[index] = updatedYear
        }
        // Masquer le formulaire après la mise à jour
        showEditForm.value = false
    } catch (error) {
        errorMessage.value = "Erreur lors de la mise à jour de la formation : " + error.message
        console.error(errorMessage.value)
    }
}

// Fonction pour supprimer une formation
const deleteAcademicYear = async (id) => {
    try {
        await axios.delete(URL + `/academicyears/${id}`)
        // Supprimer la formation de la liste locale après suppression
        academicYears.value = academicYears.value.filter(year => year.id !== id)
    } catch (error) {
        errorMessage.value = "Erreur lors de la suppression de la formation : " + error.message
        console.error(errorMessage.value)
    }
}

// Fonction pour obtenir le nom complet d'un manager à partir de son ID
const getManagerName = (managerId) => {
    if (!managerId) return 'Aucun responsable';
    const manager = managers.value.find(m => m.id === managerId);
    return manager ? `${manager.prenom} ${manager.nom}` : `ID: ${managerId}`;
}

// Charger les managers dès l'initialisation du composant
onMounted(() => {
    fetchManagers();
    fetchAcademicYears();
})
</script>

<template>
    <div>
        <!-- Affichage des messages d'erreur -->
        <div v-if="errorMessage" class="error-message">
            <p>{{ errorMessage }}</p>
        </div>

        <!-- Boutons de gestion -->
        <button @click="toggleAddForm">Ajouter</button>
        <button @click="startScraper">Scraper</button>
        <button @click="showAcademicYears">Afficher</button>

        <!-- Formulaire d'ajout d'une formation -->
        <div v-if="showAddForm">
            <h2>Ajouter une nouvelle formation</h2>
            <form @submit.prevent="addAcademicYear">
                <div>
                    <label for="name">Nom :</label>
                    <input v-model="newAcademicYear.name" id="name" type="text" required />
                </div>
                <div>
                    <label for="praticalWorkSize">Taille max TP :</label>
                    <input v-model="newAcademicYear.praticalWorkSize" id="praticalWorkSize" type="number" required />
                </div>
                <div>
                    <label for="directedWorkSize">Taille max TD :</label>
                    <input v-model="newAcademicYear.directedWorkSize" id="directedWorkSize" type="number" required />
                </div>
                <div>
                    <label for="numberOptionalTeachingUnit">Nombre d'UE optionnel :</label>
                    <input v-model="newAcademicYear.numberOptionalTeachingUnit" id="numberOptionalTeachingUnit"
                        type="number" required />
                </div>
                <div>
                    <label for="responsibleId">Responsable :</label>
                    <select v-model="newAcademicYear.responsibleId" id="responsibleId">
                        <option :value="undefined">Aucun responsable</option>
                        <option v-for="manager in managers" :key="manager.id" :value="manager.id">
                            {{ manager.prenom }} {{ manager.nom }} ({{ manager.email }})
                        </option>
                    </select>
                </div>
                <button type="submit">Ajouter</button>
            </form>
        </div>

        <!-- Formulaire de modification d'une formation -->
        <div v-if="showEditForm">
            <h2>Modifier la formation</h2>
            <form @submit.prevent="updateAcademicYear">
                <div>
                    <label for="editName">Nom :</label>
                    <input v-model="editAcademicYear.name" id="editName" type="text" required />
                </div>
                <div>
                    <label for="editPraticalWorkSize">Taille max TP :</label>
                    <input v-model="editAcademicYear.praticalWorkSize" id="editPraticalWorkSize" type="number"
                        required />
                </div>
                <div>
                    <label for="editDirectedWorkSize">Taille max TD :</label>
                    <input v-model="editAcademicYear.directedWorkSize" id="editDirectedWorkSize" type="number"
                        required />
                </div>
                <div>
                    <label for="editNumberOptionalTeachingUnit">Nombre d'UE optionnel :</label>
                    <input v-model="editAcademicYear.numberOptionalTeachingUnit" id="editNumberOptionalTeachingUnit"
                        type="number" required />
                </div>
                <div>
                    <label for="editResponsibleId">Responsable :</label>
                    <select v-model="editAcademicYear.responsibleId" id="editResponsibleId">
                        <option :value="undefined">Aucun responsable</option>
                        <option v-for="manager in managers" :key="manager.id" :value="manager.id">
                            {{ manager.prenom }} {{ manager.nom }} ({{ manager.email }})
                        </option>
                    </select>
                </div>
                <button type="submit">Mettre à jour</button>
            </form>
        </div>

        <!-- Affichage des formations -->
        <div v-if="!showAddForm && !showScraperInfo && !showEditForm">
            <h1>Gestion des formations</h1>
            <ul>
                <li v-for="year in academicYears" :key="year.id">
                    <p><strong>Nom :</strong> {{ year.name }}</p>
                    <p><strong>Taille max TP :</strong> {{ year.praticalWorkSize }}</p>
                    <p><strong>Taille max TD :</strong> {{ year.directedWorkSize }}</p>
                    <p><strong>Nombre d'UE optionnel :</strong> {{ year.numberOptionalTeachingUnit }}</p>
                    <p>
                        <strong>Responsable :</strong> {{ getManagerName(year.responsibleId) }}
                    </p>
                    <button @click="getDetails(year.id)">Détails</button>
                    <button @click="toggleEditForm(year)">Modifier</button>
                    <button @click="deleteAcademicYear(year.id)">Supprimer</button>

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
                                    <p><strong>Obligatoire :</strong> {{ unit.isRequired ? 'Oui' : 'Non' }}</p>
                                    <p><strong>Capacité :</strong> {{ unit.capacity }}</p>
                                    <p><strong>ID Formation :</strong> {{ unit.academicYearId }}</p>
                                    <p>
                                        <strong>Responsable :</strong> {{ getManagerName(unit.responsibleId) }}
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

        <!-- Affichage Scraper info -->
        <div v-if="showScraperInfo">
            <h2>Information Scraper</h2>
            <p>Les données seront récupérées et mises à jour...</p>
            <!-- Info scraper, could be more detailed -->
        </div>
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

.error-message {
    color: red;
    font-weight: bold;
    margin-bottom: 10px;
}

select {
    width: 100%;
    padding: 8px;
    margin: 5px 0;
}
</style>