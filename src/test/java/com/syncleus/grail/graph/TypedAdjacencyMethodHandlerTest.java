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
package com.syncleus.grail.graph;

import com.syncleus.grail.graph.action.*;
import com.tinkerpop.blueprints.*;
import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.Module;
import junit.framework.*;
import org.junit.*;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.*;

public class TypedAdjacencyMethodHandlerTest {
    private static final TypedAdjacencyMethodHandler HANDLER = new TypedAdjacencyMethodHandler(null);
    private static final Method GET_SON_METHOD;
    private static final TypedAdjacency TYPED_ANNOTATION;
    private static final Object MOCK_FRAME = new Object();
    private static final Element MOCK_ELEMENT = new MockElement();
    private static final Element MOCK_VERTEX = new MockVertex();
    private static final FramedGraphFactory FACTORY = new GrailGraphFactory(Collections.<Module>emptyList(),
            new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{
                    SimpleSignalNode.class})));

    static {
        try {
            GET_SON_METHOD = God.class.getMethod("getSon", Class.class);
        }
        catch( final NoSuchMethodException caught ) {
            throw new IllegalStateException(caught);
        }

        TYPED_ANNOTATION = GET_SON_METHOD.getAnnotation(TypedAdjacency.class);
    }

    @Test(expected = IllegalStateException.class)
    public void testNoVertex() {
        final FramedGraph framedGraph = BlankGraphFactory.makeTinkerGraph();

        HANDLER.processElement(MOCK_FRAME, GET_SON_METHOD, new Object[]{God.class}, TYPED_ANNOTATION, framedGraph, MOCK_ELEMENT);
    }

    @Test(expected = IllegalStateException.class)
    public void testWrongArgument() {
        final FramedGraph framedGraph = BlankGraphFactory.makeTinkerGraph();

        HANDLER.processElement(MOCK_FRAME, GET_SON_METHOD, new Object[]{new Object()}, TYPED_ANNOTATION, framedGraph, MOCK_VERTEX);
    }

    @Test(expected = IllegalStateException.class)
    public void testToManyArgument() {
        final FramedGraph framedGraph = BlankGraphFactory.makeTinkerGraph();

        HANDLER.processElement(MOCK_FRAME, GET_SON_METHOD, new Object[]{new Object(), new Object()}, TYPED_ANNOTATION, framedGraph, MOCK_VERTEX);
    }

    @Test(expected = IllegalStateException.class)
    public void testNullArguments() {
        final FramedGraph framedGraph = BlankGraphFactory.makeTinkerGraph();

        HANDLER.processElement(MOCK_FRAME, GET_SON_METHOD, null, TYPED_ANNOTATION, framedGraph, MOCK_VERTEX);
    }

    @Test
    public void testGetBothNodes() {
        final FramedTransactionalGraph<?> graph = FACTORY.create(new MockTransactionalTinkerGraph());

        // construct graph
        final SimpleSignalNode father = (SimpleSignalNode) graph.addVertex(null, SimpleSignalNode.class);
        final SimpleSignalNode child = (SimpleSignalNode) graph.addVertex(null, SimpleSignalNode.class);
        final SimpleSignalNode grandchild = (SimpleSignalNode) graph.addVertex(null, SimpleSignalNode.class);
        final SignalMultiplyingEdge fatherEdge = (SignalMultiplyingEdge) graph.addEdge(null, child.asVertex(), father.asVertex(), "parent", SignalMultiplyingEdge.class);
        final SignalMultiplyingEdge childEdge = (SignalMultiplyingEdge) graph.addEdge(null, grandchild.asVertex(), child.asVertex(), "parent", SignalMultiplyingEdge.class);

        Assert.assertEquals(getIteratorSize(child.getParents(SimpleSignalNode.class).iterator()), 1);
        Assert.assertEquals(getIteratorSize(child.getFamily(SimpleSignalNode.class).iterator()), 2);
    }

    @Test
    public void testGetBothNode() {
        final FramedTransactionalGraph<?> graph = FACTORY.create(new MockTransactionalTinkerGraph());

        // construct graph
        final SimpleSignalNode father = (SimpleSignalNode) graph.addVertex(null, SimpleSignalNode.class);
        final SimpleSignalNode child = (SimpleSignalNode) graph.addVertex(null, SimpleSignalNode.class);
        final SimpleSignalNode grandchild = (SimpleSignalNode) graph.addVertex(null, SimpleSignalNode.class);
        final SignalMultiplyingEdge fatherEdge = (SignalMultiplyingEdge) graph.addEdge(null, child.asVertex(), father.asVertex(), "parent", SignalMultiplyingEdge.class);
        final SignalMultiplyingEdge childEdge = (SignalMultiplyingEdge) graph.addEdge(null, grandchild.asVertex(), child.asVertex(), "parent", SignalMultiplyingEdge.class);

        Assert.assertNotNull(child.getParent(SimpleSignalNode.class));
        Assert.assertTrue(child.getParent(SimpleSignalNode.class) instanceof SimpleSignalNode);
        Assert.assertNotNull(child.getFamilyMember(SimpleSignalNode.class));
        Assert.assertTrue(child.getFamilyMember(SimpleSignalNode.class) instanceof SimpleSignalNode);
    }

    @Test
    public void testAddBothNode() {
        final FramedTransactionalGraph<?> graph = FACTORY.create(new MockTransactionalTinkerGraph());

        // construct graph
        SimpleSignalNode child = (SimpleSignalNode) graph.addVertex(Integer.valueOf(1), SimpleSignalNode.class);
        final SimpleSignalNode inbreedChild = child.addInbreed(SimpleSignalNode.class);

        Assert.assertEquals(2, getIteratorSize(inbreedChild.getFamily(SimpleSignalNode.class).iterator()));
        Assert.assertEquals(1, getIteratorSize(inbreedChild.getParents(SimpleSignalNode.class).iterator()));
        Assert.assertEquals(1, getIteratorSize(inbreedChild.getChildren(SimpleSignalNode.class).iterator()));
        Assert.assertEquals(2, getIteratorSize(graph.getVertices().iterator()));
        Assert.assertEquals(2, getIteratorSize(child.getFamily(SimpleSignalNode.class).iterator()));
        Assert.assertEquals(1, getIteratorSize(child.getParents(SimpleSignalNode.class).iterator()));
    }

    private static int getIteratorSize(Iterator<?> iterator) {
        int count = 0;
        while(iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }
}
