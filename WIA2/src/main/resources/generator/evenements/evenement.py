import requests
import xmltodict
import json
import random
from googletrans import Translator

translator = Translator()
r = requests.get('http://opendata.nicecotedazur.org/data/storage/f/DIRECTORY/talend/20201214/events_public.xml')
obj = xmltodict.parse(r.text)
evenements = json.dumps(obj, indent=4)
jsonEvents = json.loads(evenements)


def read(filePath):
    with open(filePath, 'r', encoding='utf-8') as utilisateur:
        data = json.load(utilisateur)
    return data


def add(destination, data, nb):
    for i in range(nb):
        obj = random.choice(jsonEvents['events']['event'])

        try:
            adress = obj['address']['address_content']
        except KeyError:
            adress = ''

        data['events'].append({
            "@id": "e:" + str(random.randint(7, 9999)),
            "@type": 'Event',
            "name_fr": obj['name_fr'],
            "start": obj['start'],
            "end": obj['end'],
            "adress": adress,
            "profile": obj['profiles']['profile'],
            "categories": obj['categories']['category'],
            "description": translator.translate(str(obj['descriptions']['description'][-1]['#text']), src='en',
                                                dest='fr').text,
            "image": obj['images']['image'],
            "latitude": float(obj['latitude']),
            "longitude": float(obj['longitude']),
            "users": []
        })

    with open(destination, 'w', encoding='utf-8') as file:
        json.dump(data, file, ensure_ascii=False)


if __name__ == '__main__':
    path = '../../database/evenements.jsonld'
    events = read(path)
    add('./test.jsonld', events, 1)
