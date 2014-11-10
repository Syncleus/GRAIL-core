package com.syncleus.grail.graph.titangraph;

import com.syncleus.grail.graph.*;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.Module;
import org.junit.*;

import java.util.*;

public class TypedAdjacencyHandlerTest {
    private static final Set<Class<?>> TEST_TYPES = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{God.class, FatherEdge.class, GodExtended.class}));
    private static final Set<Class<?>> EXCEPTION_TEST_TYPES = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{ExceptionGod.class, FatherEdge.class, GodExtended.class}));

    @Test
    public void testGetSons() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final FramedGraphFactory factory = new GrailGraphFactory(Collections.<Module>emptyList(), TypedAdjacencyHandlerTest.TEST_TYPES);

        final FramedGraph<?> framedGraph = factory.create(godGraph);

        final Iterable<God> gods = (Iterable<God>) framedGraph.getVertices("name", "jupiter", God.class);
        final God father = gods.iterator().next();
        Assert.assertEquals(father.getName(), "jupiter");

        final Iterable<? extends God> children = father.getSons(God.class);
        final God child = children.iterator().next();
        Assert.assertEquals(child.getName(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);
    }

    @Test
    public void testGetSonsNoLabel() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final FramedGraphFactory factory = new GrailGraphFactory(Collections.<Module>emptyList(), TypedAdjacencyHandlerTest.EXCEPTION_TEST_TYPES);

        final FramedGraph<?> framedGraph = factory.create(godGraph);

        final Iterable<ExceptionGod> gods = (Iterable<ExceptionGod>) framedGraph.getVertices("name", "jupiter", ExceptionGod.class);
        final ExceptionGod father = gods.iterator().next();
        Assert.assertEquals(father.getName(), "jupiter");

        final Iterable<? extends ExceptionGod> children = father.getNoLabel(ExceptionGod.class);
        Assert.assertTrue(!children.iterator().hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetSonsNoArgument() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final FramedGraphFactory factory = new GrailGraphFactory(Collections.<Module>emptyList(), TypedAdjacencyHandlerTest.EXCEPTION_TEST_TYPES);

        final FramedGraph<?> framedGraph = factory.create(godGraph);

        final Iterable<ExceptionGod> gods = (Iterable<ExceptionGod>) framedGraph.getVertices("name", "jupiter", ExceptionGod.class);
        final ExceptionGod father = gods.iterator().next();
        Assert.assertEquals(father.getName(), "jupiter");

        final Iterable<? extends ExceptionGod> children = father.getSons();
        Assert.assertTrue(!children.iterator().hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetSonsExtraArgument() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final FramedGraphFactory factory = new GrailGraphFactory(Collections.<Module>emptyList(), TypedAdjacencyHandlerTest.EXCEPTION_TEST_TYPES);

        final FramedGraph<?> framedGraph = factory.create(godGraph);

        final Iterable<ExceptionGod> gods = (Iterable<ExceptionGod>) framedGraph.getVertices("name", "jupiter", ExceptionGod.class);
        final ExceptionGod father = gods.iterator().next();
        Assert.assertEquals(father.getName(), "jupiter");

        final Iterable<? extends ExceptionGod> children = father.getSons(ExceptionGod.class, "bad stuff will now happen");
        Assert.assertTrue(!children.iterator().hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetSonsWrongArgument() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final FramedGraphFactory factory = new GrailGraphFactory(Collections.<Module>emptyList(), TypedAdjacencyHandlerTest.EXCEPTION_TEST_TYPES);

        final FramedGraph<?> framedGraph = factory.create(godGraph);

        final Iterable<ExceptionGod> gods = (Iterable<ExceptionGod>) framedGraph.getVertices("name", "jupiter", ExceptionGod.class);
        final ExceptionGod father = gods.iterator().next();
        Assert.assertEquals(father.getName(), "jupiter");

        final Iterable<? extends ExceptionGod> children = father.getSons("bad stuff will now happen");
        Assert.assertTrue(!children.iterator().hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetSonsWrongPrefix() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final FramedGraphFactory factory = new GrailGraphFactory(Collections.<Module>emptyList(), TypedAdjacencyHandlerTest.EXCEPTION_TEST_TYPES);

        final FramedGraph<?> framedGraph = factory.create(godGraph);

        final Iterable<ExceptionGod> gods = (Iterable<ExceptionGod>) framedGraph.getVertices("name", "jupiter", ExceptionGod.class);
        final ExceptionGod father = gods.iterator().next();
        Assert.assertEquals(father.getName(), "jupiter");

        final Iterable<? extends ExceptionGod> children = father.badSons(ExceptionGod.class);
        Assert.assertTrue(!children.iterator().hasNext());
    }

    @Test
    public void testGetSonsBuiltIn() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final FramedGraphFactory factory = new GrailGraphFactory(Collections.<Module>emptyList(), TypedAdjacencyHandlerTest.TEST_TYPES);

        final FramedGraph<?> framedGraph = factory.create(godGraph);

        final Iterable<God> gods = (Iterable<God>) framedGraph.getVertices("name", "jupiter", God.class);
        final God father = gods.iterator().next();
        Assert.assertEquals(father.getName(), "jupiter");

        final Iterable children = father.getSons();
        final Object childObject = children.iterator().next();
        Assert.assertTrue(childObject instanceof God);
        final God child = (God) childObject;
        Assert.assertEquals(child.getName(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);
    }

    @Test
    public void testGetSon() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final FramedGraphFactory factory = new GrailGraphFactory(Collections.<Module>emptyList(), TypedAdjacencyHandlerTest.TEST_TYPES);

        final FramedGraph framedGraph = factory.create(godGraph);

        final Iterable<God> gods = (Iterable<God>) framedGraph.getVertices("name", "jupiter", God.class);
        final God father = gods.iterator().next();
        Assert.assertEquals(father.getName(), "jupiter");

        final God child = father.getSon(God.class);
        Assert.assertEquals(child.getName(), "hercules");
        Assert.assertTrue(child instanceof GodExtended);
    }

    @Test
    public void testAddSon() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final FramedGraphFactory factory = new GrailGraphFactory(Collections.<Module>emptyList(), TypedAdjacencyHandlerTest.TEST_TYPES);

        final FramedGraph framedGraph = factory.create(godGraph);

        final Iterable<God> gods = (Iterable<God>) framedGraph.getVertices("name", "jupiter", God.class);
        final God father = gods.iterator().next();
        Assert.assertEquals(father.getName(), "jupiter");

        final God child = father.addSon(God.class);
        Assert.assertNull(child.getName());
    }
}
