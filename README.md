# EtuGestion
[![java](https://img.shields.io/badge/Java-orange.svg)](https://www.java.com)
[![Vuejs](https://img.shields.io/badge/Vue.js-green.svg)](https://vuejs.org/)
[![Servlet](https://img.shields.io/badge/Servlet-red.svg)](https://jakarta.ee/specifications/servlet/)
[![MongoDB](https://img.shields.io/badge/MongoDB-darkgreen.svg)](https://www.mongodb.com/)
[![MariaDB](https://img.shields.io/badge/MariaDB-blue.svg)](https://mariadb.org/)


## Description Générale
Ce projet gère l’inscription d’étudiants à une année universitaire.Deux types d’utilisateurs interagissent avec le système : les étudiants, qui s’inscrivent, choisissent des options et consultent leurs affectations, et le responsable des formations, qui définit les formations, valide les inscriptions et gère les affectations en TD/TP. 

Pour réaliser cette application plusieurs micro-services ont été conceptualisé : authentication, core, academic-year-scraper, academic-year, student, message, responsable et student-parser. Nous avons implémenter les services core, academic-year et manager.

## Core (Spring Boot)
Le service Core constitue le cœur de l’application EtuGestion. Il centralise et fournit les API indispensables à la gestion des étudiants, des utilisateurs ainsi que d’autres entités centrales du système.

### Fonctionnalités

#### Gestion des utilisateurs et authentification  
Mise en place des mécanismes d’inscription et de connexion  

#### Gestion des données des étudiants  
Création, lecture, mise à jour et suppression (CRUD) des informations relatives aux étudiants  

#### Gestion des responsables  
Permet de gérer les responsables  
Cela inclut la création et la gestion de leurs informations personnelles (CRUD)  

#### Gestion des formations  
Administration des programmes de formation (par exemple, Master Tiila ou L3 IFA)  
Cette fonctionnalité permet de définir, planifier et gérer les cursus d’études proposés  

#### Fonctionnalités supplémentaires (implémentées)  
Création de compte suite à la création d'un étudiant  
Création de message suite à la création d'un étudiant et à sa validation dans une formation  


## AcademicYear
Description...
BDD...

## Manager
Description...
BDD...

