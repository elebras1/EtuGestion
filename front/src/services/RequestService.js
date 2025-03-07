import axios from 'axios';

const API_URL = 'http://localhost:8081';

export default {
    fetchRequests() {
        return axios.get(`${API_URL}/requests`);
    },
    fetchAcademicYears() {
        return axios.get(`${API_URL}/academicyears`);
    },
    acceptRequest(request) {
        return axios.post(`${API_URL}/academicyears/${request.academicYearId}/accept/${request.studentId}`);
    },
    rejectRequest(request) {
        return axios.post(`${API_URL}/academicyears/${request.academicYearId}/reject/${request.studentId}`);
    },
    submitRequest(newRequest) {
        return axios.post(`${API_URL}/academicyears/${newRequest.academicYearId}/register/${newRequest.studentId}`);
    }
};
