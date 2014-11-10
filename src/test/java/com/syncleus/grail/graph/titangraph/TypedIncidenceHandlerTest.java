/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/
package com.syncleus.grail.graph.titangraph;

import com.syncleus.grail.graph.*;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.Module;
import com.tinkerpop.frames.modules.gremlingroovy.GremlinGroovyModule;
import org.junit.*;

import java.util.*;

public class TypedIncidenceHandlerTest {
    private static final Set<Class<?>> TEST_TYPES = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{God.class, FatherEdge.class, GodExtended.class, FatherEdgeExtended.class}));

    @Test
    public void testGetSonEdges() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final FramedGraphFactory factory = new GrailGraphFactory(Collections.<Module>emptyList(), TypedIncidenceHandlerTest.TEST_TYPES);

        final FramedGraph<?> framedGraph = factory.create(godGraph);

        final Iterable<God> gods = (Iterable<God>) framedGraph.getVertices("name", "jupiter", God.class);
        final God father = gods.iterator().next();
        Assert.assertEquals(father.getName(), "jupiter");

        final Iterable<? extends FatherEdge> childEdges = father.getSonEdges(FatherEdge.class);
        final FatherEdge childEdge = childEdges.iterator().next();
        Assert.assertEquals(childEdge.getSon().getName(), "hercules");
        Assert.assertTrue(childEdge.getSon() instanceof GodExtended);
    }

    @Test
    public void testGetSonEdgesExtended() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final FramedGraphFactory factory = new GrailGraphFactory(Collections.<Module>emptyList(), TypedIncidenceHandlerTest.TEST_TYPES);

        final FramedGraph<?> framedGraph = factory.create(godGraph);

        final Iterable<God> gods = (Iterable<God>) framedGraph.getVertices("name", "jupiter", God.class);
        final God father = gods.iterator().next();
        Assert.assertEquals(father.getName(), "jupiter");

        final Iterable<? extends FatherEdge> childEdges = father.getSonEdges(FatherEdgeExtended.class);
        final FatherEdge childEdge = childEdges.iterator().next();
        Assert.assertEquals(childEdge.getSon().getName(), "hercules");
        Assert.assertTrue(childEdge.getSon() instanceof GodExtended);
    }

    @Test
    public void testGetSonEdge() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final FramedGraphFactory factory = new GrailGraphFactory(Collections.<Module>emptyList(), TypedIncidenceHandlerTest.TEST_TYPES);

        final FramedGraph framedGraph = factory.create(godGraph);

        final Iterable<God> gods = (Iterable<God>) framedGraph.getVertices("name", "jupiter", God.class);
        final God father = gods.iterator().next();
        Assert.assertEquals(father.getName(), "jupiter");

        final FatherEdge childEdge = father.getSonEdge(FatherEdge.class);
        Assert.assertEquals(childEdge.getSon().getName(), "hercules");
        Assert.assertTrue(childEdge.getSon() instanceof GodExtended);
    }
}
