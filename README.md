# GRAIL
_Graph Represented Algorithm Implementation Layer_

[![Build Status](http://builds.syncleus.com/buildStatus/icon?job=GRAIL Core)](http://builds.syncleus.com/job/GRAIL%20Core/)

GRAIL is an algorithm implementation layer that allows arbitrary algorithms to be backed, and executed, from a graph
database. The backing graph database can be either an on-disk server instance or a purely local in-memory instance. This
allows generic tools to be interfaced to accomplish an assortment of extensions such as: distributed processing,
visualizations, indexing, graph traversal, [SPARQL](http://en.wikipedia.org/wiki/SPARQL) queries, and much more.

GRAIL uses a native [TinkerPop](http://www.tinkerpop.com) stack, this means it can support virtually every graph
database available to you. A few examples of supported Graph Databases are as follows.

* [Titan](http://thinkaurelius.github.io/titan/)
* [Neo4j](http://neo4j.com)
* [OrientDB](http://www.orientechnologies.com/orientdb/)
* [MongoDB](http://www.mongodb.org)
* [Oracle NoSQL](http://www.oracle.com/us/products/database/nosql/overview/index.html)
* [TinkerGraph](https://github.com/tinkerpop/blueprints/wiki/TinkerGraph)

TinkerPop also provides several tools which can be used to work with GRAIL backed algorithms.

* **Furnace** - Graph analysis utilities
* **Pipes** - A data-flow framework for splitting, merging, filtering, and transforming of data
* **Gremlin** - A graph query language
* **Blueprints** - A standard graph API

Finally, depending on the choice of a graph database backend, it is possible to utilize any of the following features.

* Elastic and linear scaling capabilities
* [ACID](http://en.wikipedia.org/wiki/ACID) and [BASE](http://en.wikipedia.org/wiki/Eventual_consistency) consistency
models
* Distributed data, replication, high availability, and fault tolerance
* [Faunus](https://github.com/thinkaurelius/faunus) Gremlin-based distributed graph analytics, backed by an Hadoop
cluster.
* Direct [Apache Hadoop](http://hadoop.apache.org) integration providing graph analytics, reporting, and ETL features
* Indexing and search utilities including: [ElasticSearch](http://www.elasticsearch.org/overview/elasticsearch),
[Solr](http://lucene.apache.org/solr/), and [Lucene](http://lucene.apache.org)

A Titan backend can support all the above features.

## Obtaining the Source

The official source repository for GRAIL is located on the Syncleus Gerrit instance and can be cloned using the
following command.

```
git clone http://gerrit.syncleus.com/GRAIL-core
```

We also maintain a GitHub clone of the official repository which can be found
[here](https://github.com/Syncleus/GRAIL-core). Finally Syncleus also hosts an instance of GitLab which has a
clone of the repository which can be found [here](http://gitlab.syncleus.com/syncleus/GRAIL-core).
