import axios from 'axios';

const API_URL = 'http://localhost:8081';
const API_CORE = 'http://localhost:8080';

export default {
    fetchTeachingUnits() {
        return axios.get(`${API_URL}/teachingunits`);
    },
    createTeachingUnit(payload) {
        return axios.post(`${API_URL}/teachingunits`, payload);
    },
    updateTeachingUnit(id, payload) {
        return axios.put(`${API_URL}/teachingunits/${id}`, payload);
    },
    deleteTeachingUnit(id) {
        return axios.delete(`${API_URL}/teachingunits/${id}`);
    },

    fetchAcademicYears() {
        return axios.get(`${API_URL}/academicyears`);
    },

    fetchManagers() {
        return axios.get(`${API_CORE}/managers`);
    },

    registerStudent(unitId, studentId) {
        return axios.post(`${API_URL}/teachingunits/${unitId}/register/${studentId}`);
    },
    unregisterStudent(unitId, studentId) {
        return axios.post(`${API_URL}/teachingunits/${unitId}/unregister/${studentId}`);
    }
};