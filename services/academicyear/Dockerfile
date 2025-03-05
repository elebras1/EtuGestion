# Utiliser une image de base Java (OpenJDK)
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier jar de ton application Spring Boot dans le conteneur
COPY build/libs/demo-1.1.jar /app/demo-1.1.jar

# Exposer le port sur lequel ton application Spring Boot va écouter
EXPOSE 8083

# Commande pour exécuter ton application Spring Boot
ENTRYPOINT ["java", "-jar", "demo-1.1.jar"]
