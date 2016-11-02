package com.syncleus.grail.graph;

import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

public class ComputerTest {
    @Test
    public void testCustomModules() {
        final GrailGraph<TinkerGraph> g = new TinkerGrailGraphFactory().subgraph("0");
    }
}
