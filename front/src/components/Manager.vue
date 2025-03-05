<script>
import axios from 'axios';

const URL = 'http://localhost:8080';

export default {


  data() {
    return {
      managers: [],
      newManager: { prenom: '', nom: '', email: '' },
      editingManager: null,
      showManagers: false,
    };
  },
  methods: {
    async getManagers() {
      const response = await axios.get(`${URL}/managers`);
      this.managers = response.data;
      this.showManagers = this.managers.length > 0;
    },
    async postManager() {
      if (!this.newManager.prenom || !this.newManager.nom || !this.newManager.email) return;
      await axios.post(`${URL}/managers`, this.newManager);
      this.newManager = { prenom: '', nom: '', email: '' };
      await this.getManagers();
    },
    editManager(manager) {
      this.editingManager = { ...manager };
    },
    async putManager() {
      const response = await axios.put(`${URL}/managers/${this.editingManager.id}`, this.editingManager,);
      this.editingManager = null;
      await this.getManagers();
    }
    ,
    async deleteManager(id) {
      await axios.delete(`${URL}/managers/${id}`);
      await this.getManagers();
    },
  }
};
</script>

<template>
  <div class="container">
    <h1>Gestion des Responsables</h1>

    <button @click="getManagers">Afficher les Responsables</button>

    <div v-if="showManagers">
      <input v-model="newManager.prenom" placeholder="Prénom" />
      <input v-model="newManager.nom" placeholder="Nom" />
      <input v-model="newManager.email" placeholder="Email" />
      <button @click="postManager">Ajouter</button>

      <div v-if="editingManager">
        <input v-model="editingManager.prenom" placeholder="Prénom" />
        <input v-model="editingManager.nom" placeholder="Nom" />
        <input v-model="editingManager.email" placeholder="Email" />
        <button @click="putManager">Mettre à jour</button>
      </div>

      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>Prénom</th>
          <th>Nom</th>
          <th>Email</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="manager in managers" :key="manager.id">
          <td>{{ manager.id }}</td>
          <td>{{ manager.prenom }}</td>
          <td>{{ manager.nom }}</td>
          <td>{{ manager.email }}</td>
          <td>
            <button @click="editManager(manager)">Modifier</button>
            <button @click="deleteManager(manager.id)">Supprimer</button>
          </td>
        </tr>
        </tbody>
      </table>


    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 800px;
  margin: auto;
  text-align: center;
}
input {
  margin: 5px;
  padding: 5px;
}
button {
  margin: 5px;
  padding: 5px;
  cursor: pointer;
}
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}
th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}
th {
  background-color: #f2f2f2;
}
</style>