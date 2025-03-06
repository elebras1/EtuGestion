<script>
import axios from 'axios';

const API_URL = 'http://localhost:8081';

export default {
    data() {
        return {
            groups: [],
            academicYearData: [],
            isEditing: false,
            isCreating: false,
            currentGroupId: null,
            groupData: {
                name: "",
                academicYearId: null,
                studentsIds: [],
                studentsIdsString: ""
            },
        };
    },

    mounted() {
        this.fetchGroups();
    },
    methods: {
        // Récupérer tous les groupes
        async fetchGroups() {
            try {
                const response = await axios.get(`${API_URL}/groups`);
                this.groups = response.data;
                await this.fetchAcademicYears();
            } catch (error) {
                console.error('Erreur lors de la récupération des groupes', error);
            }
        },

        async fetchAcademicYears() {
            try {
                const academicYearIds = this.groups.map(group => group.academicYearId);
                const academicYears = await Promise.all(
                    academicYearIds.map(id => axios.get(`${API_URL}/academicyears/${id}`).then(response => response.data))
                );
                // Éliminer les doublons
                this.academicYearData = [...new Map(academicYears.map(item => [item.id, item])).values()];
            } catch (error) {
                console.error('Erreur lors de la récupération des formations', error);
            }
        },


        // Récupérer le nom de l'année académique en fonction de son ID
        getAcademicYearName(academicYearId) {
            const academicYear = this.academicYearData.find(item => item.id === academicYearId);
            return academicYear ? academicYear.name : 'Non défini';
        },

        // Créer un groupe
        createGroup() {
            this.isCreating = true;
            this.groupData = {
                name: "",
                academicYearId: null,
                studentsIds: [],
                studentsIdsString: ""
            };
        },

        // Modifier un groupe
        async editGroup(id) {
            this.currentGroupId = id;
            try {
                const response = await axios.get(`${API_URL}/groups/${id}`);
                this.groupData = response.data;
                // Convertir le tableau en chaîne pour préremplir le champ
                this.groupData.studentsIdsString = this.groupData.studentsIds ? this.groupData.studentsIds.join(', ') : "";
                this.isEditing = true;
            } catch (error) {
                console.error('Groupe non trouvé', error);
            }
        },


        // Soumettre le formulaire de création ou de modification
        async handleSubmit() {
            // Conversion de la chaîne en tableau d'entiers
            if (this.groupData.studentsIdsString) {
                this.groupData.studentsIds = this.groupData.studentsIdsString
                    .split(',')
                    .map(id => parseInt(id.trim()))
                    .filter(id => !isNaN(id));
            } else {
                this.groupData.studentsIds = [];
            }

            if (this.isEditing) {
                await this.updateGroup(this.currentGroupId);
            } else if (this.isCreating) {
                await this.createNewGroup();
            }
        },


        // Créer un nouveau groupe
        async createNewGroup() {
            try {
                const response = await axios.post(`${API_URL}/groups`, this.groupData);
                if (response.status === 200 || response.status === 201) {
                    await this.fetchGroups();
                    this.isCreating = false;
                } else {
                    console.error('Erreur de création du groupe');
                }
            } catch (error) {
                console.error('Erreur de connexion', error);
            }
        },


        // Mettre à jour un groupe existant
        async updateGroup(id) {
            try {
                const response = await axios.put(`${API_URL}/groups/${id}`, this.groupData);
                if (response.status === 200) {
                    this.fetchGroups();
                    this.isEditing = false;
                } else {
                    console.error('Erreur de mise à jour du groupe');
                }
            } catch (error) {
                console.error('Erreur de connexion', error);
            }
        },

        // Supprimer un groupe
        async deleteGroup(id) {
            try {
                const response = await axios.delete(`${API_URL}/groups/${id}`);
                if (response.status === 200) {
                    this.groups = this.groups.filter(group => group.id !== id);
                } else {
                    console.error('Erreur de suppression du groupe');
                }
            } catch (error) {
                console.error('Erreur de connexion', error);
            }
        },

        // Annuler la création ou modification
        cancelAction() {
            this.isCreating = false;
            this.isEditing = false;
            this.groupData = {
                name: "",
                academicYearId: null,
                studentsIds: [],
                studentsIdsString: ""
            };
        },

        // Retourner à la liste des groupes
        goBackToList() {
            this.isViewing = false;
            this.groupDetails = null;
        },
    },
};
</script>

<template>
    <div>
        <h1>Gestion des Groupes</h1>

        <!-- Affichage de la liste des groupes -->
        <div v-if="!isEditing && !isCreating">
            <h2>Liste des Groupes</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nom</th>
                        <th>Formation</th>
                        <th>Étudiants</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="group in groups" :key="group.id">
                        <td>{{ group.id }}</td>
                        <td>{{ group.name }}</td>
                        <td>{{ getAcademicYearName(group.academicYearId) }}</td>
                        <td>
                            {{ group.studentsIds ? group.studentsIds.join(', ') : '' }}
                        </td>
                        <td>
                            <button @click="editGroup(group.id)">Modifier</button>
                            <button @click="deleteGroup(group.id)">Supprimer</button>
                        </td>
                    </tr>
                </tbody>
            </table>
            <button @click="createGroup">Créer un groupe</button>
        </div>

        <!-- Formulaire de création ou modification d'un groupe -->
        <div v-if="isCreating || isEditing">
            <h2>{{ isEditing ? 'Modifier' : 'Créer' }} un groupe</h2>
            <form @submit.prevent="handleSubmit">
                <div>
                    <label for="name">Nom du groupe:</label>
                    <input type="text" v-model="groupData.name" required />
                </div>

                <div>
                    <label for="academicYearId">Année académique:</label>
                    <select v-model="groupData.academicYearId" required>
                        <option v-for="year in academicYearData" :key="year.id" :value="year.id">
                            {{ year.name }}
                        </option>
                    </select>
                </div>

                <div>
                    <label for="studentsIds">IDs des étudiants (séparés par des virgules) :</label>
                    <input type="text" v-model="groupData.studentsIdsString" placeholder="ex: 1, 2, 3" />
                </div>


                <div>
                    <button type="submit">{{ isEditing ? 'Mettre à jour' : 'Créer' }} le groupe</button>
                    <button @click="cancelAction">Annuler</button>
                </div>
            </form>
        </div>
    </div>
</template>


<style scoped>
/* Styles pour le composant */
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
