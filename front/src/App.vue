<script setup>
import { ref } from 'vue';
import AcademicYear from './components/AcademicYear.vue';
import Manager from './components/Manager.vue';
import Navbar from './components/Navbar.vue';
import RequestManager from './components/request/RequestManager.vue';
import TeachingUnit from './components/TeachingUnit.vue';
import GroupManager from './components/group/GroupManager.vue';

const currentView = ref('manager');
</script>

<template>
  <header>
    <Navbar @update-view="(view) => currentView = view" />
  </header>

  <main>
    <Manager v-if="currentView === 'manager'" />
    <AcademicYear v-if="currentView === 'academicYear'" />
    <TeachingUnit v-if="currentView === 'teachingUnit'" />
    <GroupManager v-if="currentView === 'group'" />
    <RequestManager v-if="currentView === 'request'" />
  </main>
</template>

<style scoped>
header {
  width: 100%;
  flex-shrink: 0;
  background: #333;
  padding: 10px 0;
}

main {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 20px;
}
</style>
