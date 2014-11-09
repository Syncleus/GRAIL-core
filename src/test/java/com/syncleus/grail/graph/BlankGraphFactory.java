package com.syncleus.grail.graph;

import com.thinkaurelius.titan.core.*;
import com.thinkaurelius.titan.graphdb.configuration.GraphDatabaseConfiguration;
import com.tinkerpop.frames.*;
import org.apache.commons.configuration.*;

import java.io.File;

import static com.thinkaurelius.titan.graphdb.configuration.GraphDatabaseConfiguration.INDEX_BACKEND_KEY;
import static com.thinkaurelius.titan.graphdb.configuration.GraphDatabaseConfiguration.STORAGE_DIRECTORY_KEY;

public final class BlankGraphFactory {
    public static FramedTransactionalGraph makeTitanGraph(final String location) {
        BaseConfiguration config = new BaseConfiguration();
        Configuration storage = config.subset(GraphDatabaseConfiguration.STORAGE_NAMESPACE);
        // configuring local backend
        storage.setProperty(GraphDatabaseConfiguration.STORAGE_BACKEND_KEY, "local");
        storage.setProperty(GraphDatabaseConfiguration.STORAGE_DIRECTORY_KEY, location);
        // configuring elastic search index
        Configuration index = storage.subset(GraphDatabaseConfiguration.INDEX_NAMESPACE).subset("search");
        index.setProperty(INDEX_BACKEND_KEY, "elasticsearch");
        index.setProperty("local-mode", true);
        index.setProperty("client-only", false);
        index.setProperty(STORAGE_DIRECTORY_KEY, "./target/TitanBackpropDB" + File.separator + "es");

        TitanGraph graph = TitanFactory.open(config);

        final FramedGraphFactory factory = new GrailGraphFactory();

        return factory.create(graph);
    }

    public static FramedTransactionalGraph makeTinkerGraph() {
        //final FramedGraphFactory factory = new FramedGraphFactory(typedModule, new GremlinGroovyModule(), new JavaHandlerModule());
        final FramedGraphFactory factory = new GrailGraphFactory();

        return factory.create(new MockTransactionalTinkerGraph());
    }
}
