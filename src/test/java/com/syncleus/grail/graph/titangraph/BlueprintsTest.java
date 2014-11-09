package com.syncleus.grail.graph.titangraph;

import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;
import org.junit.*;

public class BlueprintsTest {
    @Test
    public void testApp() {
        TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        Iterable<Vertex> skyVertices = godGraph.getVertices("name", "sky");
        Assert.assertTrue("no sky vertices found", skyVertices.iterator().hasNext());
    }
}
