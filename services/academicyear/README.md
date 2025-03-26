# AcademicYear (Spring Boot - MariaDB)
Ce micro-service permet la gestion des formations.  
Cela inclut une gestion CRUD des formations, des groupes, des unités d'enseignement et des demandes d'inscription.

## Fonctionnalités spécifiques
### Inscription à un groupe
Lorsqu'un étudiant fait une demande d'inscription, elle est enregistrée dans la table des demandes. Il est possible de l'accepter ou de la refuser. Lorsqu'elle est acceptée, l'étudiant est ajouté dans deux groupes de TD et de TP contenant le moins d'étudiants. Si aucun groupe n'a de place disponible, l'étudiant n'est pas ajouté.
Amélioration possible : création automatique d'un nouveau groupe avec équilibrage.

### Inscription à une unité d'enseignement
Lors de l'ajout d'une unité d'enseignement, si elle est obligatoire, tous les étudiants présents dans la formation sont ajoutés. Lors de l'inscription à une UE optionnelle, avant d'ajouter l'étudiant, on vérifie que l'unité d'enseignement n'est pas pleine
## Base de données
academic_years(id, name, praticalWorkSize, directedWorkSize, numberOptionalTeachingUnit, responsibleId)
  - id: Long
  - name: String
  - praticalWorkSize: Short
  - directedWorkSize: Short
  - numberOptionalTeachingUnit: Short
  - responsibleId: Long

groups(id, name, academic_year_id, studentsIds)
  - id: Long
  - name: String
  - academic_year_id: Long (FK)
  - studentsIds: List<Long>

requests(id, studentId, academicYearId)
  - id: Long
  - studentId: Long
  - academicYearId: Long (FK)

teaching_units(id, name, isRequired, capacity, academic_year_id, responsibleId, studentsIds)
  - id: Long
  - name: String
  - isRequired: Boolean
  - capacity: Short
  - academic_year_id: Long (FK)
  - responsibleId: Long
  - studentsIds: List<Long>

relations:
  - academic_years(id) 1:N -> groups(academic_year_id)
  - academic_years(id) 1:N -> teaching_units(academic_year_id)
  - groups(id) N:1 -> academic_years(id)
  - teaching_units(id) N:1 -> academic_years(id)
  - requests(studentId) N:1 -> users(id) (Assumption: User entity exists)
  - requests(academicYearId) N:1 -> academic_years(id)
