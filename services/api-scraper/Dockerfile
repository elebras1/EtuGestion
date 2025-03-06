# Utiliser l'image officielle Ubuntu
FROM ubuntu:20.04

# Ajouter cette ligne pour éviter l'interaction pendant l'installation
ENV DEBIAN_FRONTEND=noninteractive

# Installer tzdata pour configurer le fuseau horaire
RUN apt-get update && apt-get install -y tzdata
# Ajouter cette ligne pour définir le fuseau horaire sur Paris
RUN ln -fs /usr/share/zoneinfo/Europe/Paris /etc/localtime && dpkg-reconfigure --frontend noninteractive tzdata

# Mettre à jour les dépôts et installer les dépendances système nécessaires
RUN apt-get update && apt-get install -y \
    python3-pip \
    python3-dev \
    wget \
    libglib2.0-0 \
    libx11-xcb1 \
    libdbus-1-3 \
    libgdk-pixbuf2.0-0 \
    libnss3 \
    libxss1 \
    libasound2 \
    libxt6 \
    xdg-utils \
    firefox \
    xvfb \
    && apt-get clean

# Installer geckodriver
RUN wget https://github.com/mozilla/geckodriver/releases/download/v0.36.0/geckodriver-v0.36.0-linux64.tar.gz \
    && tar -xvzf geckodriver-v0.36.0-linux64.tar.gz \
    && mv geckodriver /usr/local/bin/ \
    && chmod +x /usr/local/bin/geckodriver \
    && rm geckodriver-v0.36.0-linux64.tar.gz

# Installer OpenJDK 21 (JDK)
RUN wget https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz \
    && tar -xvzf jdk-21_linux-x64_bin.tar.gz -C /usr/local \
    && mv /usr/local/jdk-21* /usr/local/jdk-21 \
    && rm jdk-21_linux-x64_bin.tar.gz
# Définir JAVA_HOME et mettre à jour PATH
ENV JAVA_HOME=/usr/local/jdk-21
ENV PATH=$JAVA_HOME/bin:$PATH

# Installer Maven
RUN apt-get update && apt-get install -y maven && apt-get clean
# Définir MAVEN_HOME et mettre à jour PATH pour Maven
ENV MAVEN_HOME=/usr/share/maven
ENV PATH=$MAVEN_HOME/bin:$PATH

# Définir le répertoire de travail dans le container
WORKDIR /api-scraper

COPY . .
EXPOSE 9090
# Installer les dépendances Python (selenium)
RUN pip3 install selenium

CMD ["sh", "-c", "mvn clean install && mvn exec:java"]