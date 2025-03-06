<script setup>
import { defineEmits } from 'vue';

const emit = defineEmits(['update-view']);

const setView = (view) => {
    emit('update-view', view);
};
</script>

<template>
    <nav class="navbar">
        <button @click="setView('manager')">Manager</button>
        <button @click="setView('academicYear')">Formation</button>
        <button @click="setView('teachingUnit')">UE</button>
        <button @click="setView('request')">Demande</button>
    </nav>
</template>

<style scoped>
.navbar {
    display: flex;
    justify-content: center;
    gap: 20px;
    padding: 10px;
}

button {
    background: white;
    border: none;
    padding: 10px 20px;
    cursor: pointer;
    border-radius: 5px;
    transition: 0.3s;
}

button:hover {
    background: lightgray;
}
</style>
