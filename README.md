# libraries

Des outils numériques pour les différents acteurs des bibliothèques de la ville Big City !

Projet réalisé dans le cadre du parcours développeur d'application Java d'OpenClassrooms.

Le présent dépôt représente le code source des composants développé de la Release 1


## Prérequis

- Java 8
- MySql 8.0.20
- Maven 3.3.9

## Déploiment

- Clonez le projet : 

```shell
git clone https://github.com/nicolasdotnet/libraries.git
```

le projet est composé de 3 modules : 

apiweb : Logique métier accécible à partir d'une Api Rest sécurisée
appweb : Application web pour les usagers. Client de l'apiweb
batch : Module batch pour la relance automatique par mails aux usagers n’ayant pas rendu les livres en fin de prêt. Client de l'apiweb également

### Déployer ApiWeb : 

- Créer une base de donnée dans MySql avec les paramétres suivants : 

Nom de la base  : db_libraries

Identifiant : root

Mot de passe : mysql

Pour une installation personnalisée, vous pouvez modifier les valeurs des clès du fichier ressources/application.properties du projet :  

```shell
spring.datasource.url=jdbc:mysql://localhost:3306/NOM_DATABASE?serverTimezone=UTC
spring.datasource.username=VOTRE_USERNAME
spring.datasource.password=VOTRE_PASSWORD
```
- Exécutez la commande maven dans votre terminal à la racine du projet ApiWeb :

```shell
mvn spring-boot:run
```
Spring Boot va charger les scripts SQL de création de la base de données (schema.sql) et du jeu de données de démo (data.sql) présent à la racine du répertoire ressources/ du projet.

### Déployer AppWeb :

- Exécutez la commande maven dans votre terminal à la racine du projet AppWeb :

```shell
mvn spring-boot:run
```

### Déployer le Batch : 

- Exécutez la commande maven dans votre terminal à la racine du projet Batch :

```shell
mvn spring-boot:run
```
Par défault, le batch excécute la tâche tous les jours à 23 h 30

Pour personnaliser l'heure, vous pouvez modifier les valeurs des clès du fichier ressources/application.properties du projet : 

```shell
cron.expression:0 Min Heure * * ?
``` 

## Lancement de l'application

- Aller sur un navigateur à l'adresse http://localhost:9090

Il existe un compte bibliothécaire et un compte usager dans la base démo :

### bibliothécaire : 

Identifiant : employe@mail.com
Mot de passe : 123

### usager :

Identifiant : usager@mail.com
Mot de passe : 123

## Consulter la documentation de ApiWeb : 

- Aller sur un navigateur à l'adresse http://localhost:8080/swagger-ui.html

