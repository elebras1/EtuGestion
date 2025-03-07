# Prérequis (Si le .war n'est pas présent dans le répertoire target)
Avoir Maven d'installé sur sa machine pour effectuer la commande suivante.

## Emplacement dans la racine du projet (manager)
### Construction du package
Si le fichier `.war` n'est pas déjà présent, exécuter la commande suivante à la racine de l'API :
```sh
mvn clean package
```

## API
### Création de l'image Docker de l'API managers
Se placer à la racine du projet `manager` et exécuter la commande suivante :
```sh
docker build --no-cache -t managers-api .
```

### Hébergement de l'API `manager` et de MongoDB sous Docker
Utilisation du fichier `docker-compose.yml` pour lancer les services :
```sh
docker-compose up --build
```

## (Optionnel) Ajout des données génériques dans MongoDB
Vérifier si des données sont présentes en accédant à :
En local
```
http://localhost:8080/managers
```
Ou sous Docker
```
http://managers-api:8082/managers
```
Si aucune donnée n'est affichée, suivre les étapes suivantes.

## Database
### Création de la base de données `Manager` dans MongoDB

1. Se connecter à MongoDB dans Docker :
   ```sh
   docker exec -it mongodb bash
   ```
2. Accéder à MongoDB :
   ```sh
   mongosh
   ```
3. Créer la base de données `e22102349` :
   ```sh
   use e22102349
   ```
4. Créer la collection `manager` et insérer des données :
   ```sh
   db.Manager.insertMany([
     { id: 1, email: "alice.dupont@example.com", nom: "Dupont", prenom: "Alice" },
     { id: 2, email: "bob.martin@example.com", nom: "Martin", prenom: "Bob" },
     { id: 3, email: "charlie.durand@example.com", nom: "Durand", prenom: "Charlie" },
     { id: 4, email: "diane.leroy@example.com", nom: "Leroy", prenom: "Diane" },
     { id: 5, email: "edouard.perrin@example.com", nom: "Perrin", prenom: "Edouard" },
     { id: 6, email: "francois.bernard@example.com", nom: "Bernard", prenom: "François" },
     { id: 7, email: "gabrielle.moreau@example.com", nom: "Moreau", prenom: "Gabrielle" },
     { id: 8, email: "hugo.renard@example.com", nom: "Renard", prenom: "Hugo" },
     { id: 9, email: "isabelle.roux@example.com", nom: "Roux", prenom: "Isabelle" },
     { id: 10, email: "julien.faure@example.com", nom: "Faure", prenom: "Julien" }
   ]);
   ```

