# formation-dta
Projets réalisés pendant la formation DTA

## [pizzeria-console-imperative](pizzeria-console-imperative)
Cette application permet de manipuler une entité Pizza.

Le menu affiché :
```
***** Pizzeria Administration *****
0. Lister les pizzas
1. Ajouter une pizza
2. Modifier une pizza
3. Supprimer une pizza
4. Quitter
```

Une **approche procédurale** de la programmation est utilisé pour cette application.

## [pizzeria-console-objet](pizzeria-console-objet)
Cetta application réécrit la même console d'administration de pizzeria en appliquant un **développement orienté objet** (encapsulation, héritage, polymorphisme, ...).

## [pizzeria-console-objet-java8-maven-multimodule](pizzeria-console-objet-java8-maven-multimodule) [![Build Status](http://ns377570.ip-5-196-89.eu:8080/job/Etienne%20Bohain%20-%20Pizzeria%20-%20Build/badge/icon)](http://ns377570.ip-5-196-89.eu:8080/job/Etienne%20Bohain%20-%20Pizzeria%20-%20Build/)
Version améliorée de la console d'administration de pizzeria avec les nouveautés Java 8 (Streams, Lambdas, ...).

Implémentation d'autres DAO (Data Access Object) : par fichiers, par base de données avec JDBC et base de données avec JPA.

Utilisation de Maven :
- Séparation des différentes parties du projet (DOA, Model, Console) dans des modules différents.
- Utilisation de fichiers de configuration pour la gestion du choix de DAO, et pour les accès aux bases de données.

Implémentation de tests unitaires.

Le menu affiché :
```
***** Pizzeria Administration *****
0. Lister les pizzas
1. Ajouter une pizza
2. Modifier une pizza
3. Supprimer une pizza
4. Lister les pizzas groupées par catégorie
5. Afficher la pizza au tarif le plus élevé
6. (Base de données) Importer les données
99. Quitter
```
