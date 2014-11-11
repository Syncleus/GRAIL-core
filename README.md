# GRAIL
_Graph Represented Algorithm Implementation Layer_

GRAIL is an Algorithm Implementation layer that allows arbitrary algorithms to be backed be backed, and executed from,
a graph database. The backing graph database can be either an on-disk server instance or a purely local in-memory
instance. This allows general tools to be interfaced to accomplish an assortment of extensions such as: distributed
processing, visualizations, indexing, graph traversal, SPARQL queries and much more.

GRAIL uses a native tinkerpop stack, this means it can support virtually every graph database available to you. A few
examples of supported Graph Databases are as follows:

* Neo4j
* Titan
* OrientDB
* DEX
* TinkerGraph

TinkerPop also provides several tools which can be used to work with GRAIL implemented Algorithms including:

* **Furnace** - Graph traversal utilities
* **Frames** - An object to graph mapping similar to ORM for relational databases
* **Gremlin** - A graph query langauge
* **Blueprints** - A standard graph API

Finally, depending on the choice of graph database backend it is possible to utilize any of the following features
if provided by the backend.

* ACID and BASE consistency models
* Distributed data, replication, high availability, and fault tolerance
* Apache Hadoop Integration providing graph analytics, reporting, and ETL features
* Indexing and search utilities including: ElasticSearch, Solr, and Lucene 
