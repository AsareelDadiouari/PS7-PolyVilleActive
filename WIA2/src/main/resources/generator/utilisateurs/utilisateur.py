import json
import random

nameList = []
lastNameList = []
typeList = ['Visiteur', 'Non-habitant', 'Habitant']
roleList = ['Employé', 'Commerçant', 'Organisateur', 'Admistration']
interestList = ['Musique', 'Danse', 'Nature', 'Oiseau', 'Divers', 'Théâtre', 'Fêtes et animations']


def read(filePath):
    with open(filePath, 'r', encoding='utf-8') as utilisateur:
        data = json.load(utilisateur)
    return data


def add(destination, data, nb):
    for i in range(nb):
        with open('./prenom.txt', 'r') as prenom:
            firstname = random.choice(list(prenom))
            prenom.close()

        with open('./nom.txt', 'r') as nom:
            lastname = random.choice(list(nom))
            nom.close()

        nombreOccurenceInterest = random.randint(1, 7)
        listOfInterests = set([])

        for l in range(nombreOccurenceInterest):
            listOfInterests.add(random.choice(interestList))

        data['users'].append({
            "@id": "u:" + str(random.randint(7, 9999)),
            "@type": "User",
            "firstname": firstname,
            "lastname": lastname,
            "type": random.choice(typeList),
            "role": random.choice(roleList),
            "interests": list(listOfInterests),
            "events": [''],
            "groups": [''],
        })

    with open(destination, 'w', encoding='utf-8') as file:
        json.dump(data, file, ensure_ascii=False)


if __name__ == '__main__':
    path = '../../database/utilisateurs.jsonld'
    utilisateurs = read(path)
    add('./test.jsonld', utilisateurs, 1)
