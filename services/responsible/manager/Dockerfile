# utilise l'image officielle de tomcat
FROM tomcat:10.0-jdk17

# supprime l'application par défautls
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# copie le fichier war dans la racine de webapps
COPY target/managers-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# expose le port 8080
EXPOSE 8080

# démarre tomcat
CMD ["catalina.sh", "run"]