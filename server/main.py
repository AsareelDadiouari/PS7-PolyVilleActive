import warnings
import subprocess
from flask import *
import rdflib

g = rdflib.Graph()
g.load('../RDF/LieuShema.rdf')

qres = g.query(
    """SELECT DISTINCT ?s ?p ?o
    WHERE {
        ?s ?p ?o.
    }
    """)

warnings.filterwarnings("ignore")

app = Flask(__name__)


@app.route('/rdf', methods=['GET'])
def get_rdr():
    obj = []
    for row in qres:
        obj.append(row)

    return jsonify(obj), 201


if __name__ == '__main__':
    app.run(debug=True)