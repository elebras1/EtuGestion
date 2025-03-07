// services/GroupService.js
import axios from 'axios';

const API_URL = 'http://localhost:8081';

export default {
    fetchGroups() {
        return axios.get(`${API_URL}/groups`);
    },
    fetchAcademicYears() {
        return axios.get(`${API_URL}/academicyears`);
    },
    createGroup(groupData) {
        return axios.post(`${API_URL}/groups`, groupData);
    },
    updateGroup(id, groupData) {
        return axios.put(`${API_URL}/groups/${id}`, groupData);
    },
    deleteGroup(id) {
        return axios.delete(`${API_URL}/groups/${id}`);
    },
};
