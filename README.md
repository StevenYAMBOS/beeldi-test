# TEST TECHNIQUE BEELDI (Steven YAMBOS)
## POSTE : _Développeur Android (Kotlin) (F/H/X) en alternance_

Beelding est une application qui affiche des équipements et permet de consulter leurs informations.

- Langage: Kotlin
- IDE : Android Studio

## Rappel des consignes

Le but de ce test est de créer une application simple. Cette application doit nous permettre d'évaluer l'état de vos connaisances sur la plateforme Android. Aucune limite de temps n'est imposé pour réaliser ce test (dans la limite du raisonable). Il n'y a donc aucune raison de se presser. Favorisez la qualité à la quantité.
Vous devrez générer un nouveau projet ou reprendre le projet existant, et utiliser le fichier "google-services.json" pour la connexion avec la base de données firebase
Vous êtes libre d’utiliser les libraries externes de votre choix si nécessaire. À vous également de choisir l'architeture que vous pensez la plus adaptée. Attendez vous à être questionné sur ces choix.
L'application devra comporter deux écrans, aucun système d'authentification n'est requis. L'UI de l'application ne vous est pas imposée, laissez parler votre créativité. Cependant ce n'est pas l'élément principal sur lequel vous serez jugé, privilégiez la qualité du code et la pertinence de vos choix techniques.

## Réussites

##### _Fonctionnalités_

- Barre de recherche cliquable
- Filtre de recherche fonctionnel (majuscules et minuscules)
- RecyclerView fonctionnel
- RecyclerView avec un itemClickListener fonctionnel (renvoie vers la page de l'élément cliqué)
- Splash screen fonctionnel (dure 2 secondes)
- Application qui compile

##### _Design_
- Mise en place d'un design simple et compréhensible
- Couleurs de Beeldi utilisées pour l'exercice
- Splash screen adéquat

## Difficultés
- **Connexion à Firebase** :
Il était demandé dans la consigne de générer un nouveau projet ou de reprendre le projet existant et d'utiliser le fichier **"google-services.json"** pour la connexion à la base de données Firebase. J'ai repris le projet existant, cependant je ne sais pas comment réaliser la connexion à la base de données Firebase à partir de celui-ci.
J'ai effectué un changement de la ligne **"classpath 'com.google.gms:google-services:4.0.10'"** à **"classpath 'com.google.gms:google-services:4.3.15'"** dans le fichier **build.gradle (Project)**.
J'ai également installé le **SDK de la "Real Time Database"** pour essayer d'accéder à la base de données Firebase, mais je n'arrive pas à accéder au projet Firebase à partir du fichier google-services.json. J'ai essayé de le faire pour récupérer les données partagées des fichiers .CSV, notamment les images.
Les images des équipements sont donc statiques (logo de Beeldi).

- **Conversion CSV to JSON**:
Étant donné que je n'ai pas pu accéder à la base de données Firebase, j'ai pensé **convertir** les données des documents partagés en **JSON** et les utiliser pour les afficher dans le RecyclerView. Les fichiers .csv partagés contiennent les liens des images à utiliser. Cependant, n'ayant pas accès à la base de données, l'accès aux images est refusé.

- **Échappement des chaînes de caractères** :
J'ai rencontré des problèmes d'échappement de chaînes de caractères dans mon fichier **strings.xml** malgré différentes solutions apportées (par exemple : \ ).

- Informations à afficher :
Je n'ai pas compris quelles informations afficher pour les **points de contrôle** des équipements.
Je n'ai pas compris comment accéder à la base de données Firebase à partir du fichier google-services.json.

## Auteur

- Steven YAMBOS - https://github.com/StevenYAMBOS
- Twitter - https://twitter.com/StevenYambos
- LinkedIn - https://www.linkedin.com/in/steven-yambos-kotlin-7392361b2/

