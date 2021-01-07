# PS7-20-21-WIA2

## PolyVilleActive

PolyVilleActive est à destination de la ville, de ses habitants et commerçants et a pour objectif de  mettre  en  relation  les  acteurs  de  la  ville.  L’essentiel  est  d’informer  des  activités,  des promotions, des dates à ne pas rater, de la création de nouveaux commerces de proximité, des places de stationnement et des transports en commun
![demo](https://im7.ezgif.com/tmp/ezgif-7-7542f37f1450.gif)

## Sommaire
- [Installation](#Installation)
- [Documentation](#Documentation)
- [Utilisation](#Utilisation)
- [Equipe](#Equipe)

# Installation

- Base de donnée

```bash
cd fuseki
fuseki-server --loc dataset /data_polyville
```

- Back end

```bash
cd WIA2
mvn spring-boot:run
```

- Front end

```bash
cd WIA2/src/main/resources/static/
npm install
npm run serve
```

# Documentation
SPARQL : https://www.w3.org/TR/rdf-sparql-query/

JENA FUSEKI : https://jena.apache.org

SPRING BOOT :https://spring.io/projects/spring-boot

# Utilisation
Accéder/modifier/supprimer la base de donnée via l'interface du serveur JENA
```url
http://localhost:3030/
```
![Apache Jena](https://i.imgur.com/gTLrqzy.png)
Pour lancer le front
```url
http://localhost:8080/
```


# Equipe
- [Elise Chamberlin](https://github.com/Elise-Chamberlin)
- [Asaréel Dadiouari](https://github.com/AsareelDadiouari)
- [Frédéric Météreau](https://github.com/MetereauFrederic)
- [Lucie Morant](https://github.com/LucieMorant)

![Polytech_logo](http://unice.fr/polytechnice/fr/contenus-riches/images/logos/logo-uns-pns)
