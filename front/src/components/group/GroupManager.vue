<template>
    <div>
        <h1>Gestion des Groupes</h1>

        <button @click="createGroup" v-if="!isCreating">Ajouter un groupe</button>

        <GroupList v-if="!isCreating" :groups="groups" @edit="editGroup" @delete="deleteGroup" @create="createGroup" />

        <GroupForm v-if="isCreating || isEditing" :groupData="groupData" :academicYears="academicYearData"
            @submit="handleSubmit" @cancel="cancelAction" />
    </div>
</template>


<script>
import GroupService from '@/services/GroupService';
import GroupList from './GroupList.vue';
import GroupForm from './GroupForm.vue';

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
        async fetchGroups() {
            try {
                const response = await GroupService.fetchGroups();
                this.groups = response.data;
                await this.fetchAcademicYears();
            } catch (error) {
                console.error('Erreur lors de la récupération des groupes', error);
            }
        },
        async fetchAcademicYears() {
            try {
                const response = await GroupService.fetchAcademicYears();
                this.academicYearData = response.data;
            } catch (error) {
                console.error('Erreur lors de la récupération des années académiques', error);
            }
        },
        async createGroup() {
            this.isCreating = true;
            this.groupData = {
                name: "",
                academicYearId: null,
                studentsIds: [],
                studentsIdsString: ""
            };
        },
        async editGroup(id) {
            this.currentGroupId = id;
            try {
                const group = this.groups.find(group => group.id === id);
                this.groupData = { ...group, studentsIdsString: group.studentsIds.join(', ') };
                this.isEditing = true;
            } catch (error) {
                console.error('Erreur lors de l\'édition du groupe', error);
            }
        },
        async handleSubmit(groupData) {
            groupData.studentsIds = groupData.studentsIdsString
                .split(',')
                .map(id => parseInt(id.trim()))
                .filter(id => !isNaN(id));

            if (this.isEditing) {
                await this.updateGroup(this.currentGroupId, groupData);
            } else if (this.isCreating) {
                await this.createNewGroup(groupData);
            }
        },
        async createNewGroup(groupData) {
            try {
                await GroupService.createGroup(groupData);
                await this.fetchGroups();
                this.isCreating = false;
            } catch (error) {
                console.error('Erreur lors de la création du groupe', error);
            }
        },
        async updateGroup(id, groupData) {
            try {
                await GroupService.updateGroup(id, groupData);
                await this.fetchGroups();
                this.isEditing = false;
            } catch (error) {
                console.error('Erreur lors de la mise à jour du groupe', error);
            }
        },
        async deleteGroup(id) {
            try {
                await GroupService.deleteGroup(id);
                this.groups = this.groups.filter(group => group.id !== id);
            } catch (error) {
                console.error('Erreur lors de la suppression du groupe', error);
            }
        },
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
    },
    components: {
        GroupList,
        GroupForm
    }
};
</script>
