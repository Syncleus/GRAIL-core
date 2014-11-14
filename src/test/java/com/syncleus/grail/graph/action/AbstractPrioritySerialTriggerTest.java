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
package com.syncleus.grail.graph.action;

import com.syncleus.grail.graph.*;
import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.Module;
import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.*;

public class AbstractPrioritySerialTriggerTest {
    private static final FramedGraphFactory FACTORY = new GrailGraphFactory(Collections.<Module>emptyList(),
            new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{
                    SimpleActionNode.class,
                    BadActionNode.class})));

    @Test
    public void testDoesTrigger() {
        final FramedTransactionalGraph<?> graph = FACTORY.create(new MockTransactionalTinkerGraph());

        // construct graph
        final SimpleActionNode actionNode = (SimpleActionNode) graph.addVertex(null, SimpleActionNode.class);
        final PrioritySerialTrigger trigger = (PrioritySerialTrigger) graph.addVertex(null, PrioritySerialTrigger.class);
        final PrioritySerialTriggerEdge triggerEdge = (PrioritySerialTriggerEdge) graph.addEdge(null, trigger.asVertex(), actionNode.asVertex(), "triggers", PrioritySerialTriggerEdge.class);
        triggerEdge.setTriggerAction("action");
        triggerEdge.setTriggerPriority(0);

        Assert.assertNull(actionNode.isDone());

        //process the weight
        trigger.trigger();

        Assert.assertTrue(actionNode.isDone());
    }

    @Test
    public void testDoesTriggerInOrder() {
        final FramedTransactionalGraph<?> graph = FACTORY.create(new MockTransactionalTinkerGraph());

        final List<String> triggerOrder = new ArrayList<String>(2);

        // construct graph
        final SimpleActionNode firstActionNode = (SimpleActionNode) graph.addVertex(null, SimpleActionNode.class);
        firstActionNode.setTriggerOrder(triggerOrder);
        final SimpleActionNode secondActionNode = (SimpleActionNode) graph.addVertex(null, SimpleActionNode.class);
        secondActionNode.setTriggerOrder(triggerOrder);
        final PrioritySerialTrigger trigger = (PrioritySerialTrigger) graph.addVertex(null, PrioritySerialTrigger.class);
        final PrioritySerialTriggerEdge firstTriggerEdge = (PrioritySerialTriggerEdge) graph.addEdge(null, trigger.asVertex(), firstActionNode.asVertex(), "triggers", PrioritySerialTriggerEdge.class);
        firstTriggerEdge.setTriggerAction("first");
        firstTriggerEdge.setTriggerPriority(1000);
        final PrioritySerialTriggerEdge secondTriggerEdge = (PrioritySerialTriggerEdge) graph.addEdge(null, trigger.asVertex(), secondActionNode.asVertex(), "triggers", PrioritySerialTriggerEdge.class);
        secondTriggerEdge.setTriggerAction("second");
        secondTriggerEdge.setTriggerPriority(0);

        Assert.assertTrue(triggerOrder.isEmpty());

        //process the weight
        trigger.trigger();

        //make sure we clear out the action node since the attribute is static.
        firstActionNode.setTriggerOrder(null);
        secondActionNode.setTriggerOrder(null);

        Assert.assertTrue(triggerOrder.size() == 2);
        Assert.assertTrue(triggerOrder.get(0).equals("first"));
        Assert.assertTrue(triggerOrder.get(1).equals("second"));
    }

    @Test
    public void testDoesTriggerInOrderReversed() {
        final FramedTransactionalGraph<?> graph = FACTORY.create(new MockTransactionalTinkerGraph());

        final List<String> triggerOrder = new ArrayList<String>(2);

        // construct graph
        final SimpleActionNode firstActionNode = (SimpleActionNode) graph.addVertex(null, SimpleActionNode.class);
        firstActionNode.setTriggerOrder(triggerOrder);
        final SimpleActionNode secondActionNode = (SimpleActionNode) graph.addVertex(null, SimpleActionNode.class);
        secondActionNode.setTriggerOrder(triggerOrder);
        final PrioritySerialTrigger trigger = (PrioritySerialTrigger) graph.addVertex(null, PrioritySerialTrigger.class);
        final PrioritySerialTriggerEdge secondTriggerEdge = (PrioritySerialTriggerEdge) graph.addEdge(null, trigger.asVertex(), secondActionNode.asVertex(), "triggers", PrioritySerialTriggerEdge.class);
        secondTriggerEdge.setTriggerAction("second");
        secondTriggerEdge.setTriggerPriority(0);
        final PrioritySerialTriggerEdge firstTriggerEdge = (PrioritySerialTriggerEdge) graph.addEdge(null, trigger.asVertex(), firstActionNode.asVertex(), "triggers", PrioritySerialTriggerEdge.class);
        firstTriggerEdge.setTriggerAction("first");
        firstTriggerEdge.setTriggerPriority(1000);

        Assert.assertTrue(triggerOrder.isEmpty());

        //process the weight
        trigger.trigger();

        //make sure we clear out the action node since the attribute is static.
        firstActionNode.setTriggerOrder(null);
        secondActionNode.setTriggerOrder(null);

        Assert.assertTrue(triggerOrder.size() == 2);
        Assert.assertTrue(triggerOrder.get(0).equals("first"));
        Assert.assertTrue(triggerOrder.get(1).equals("second"));
    }

    @Test(expected = UndeclaredThrowableException.class )
    public void testBadArguments() {
        final FramedTransactionalGraph<?> graph = FACTORY.create(new MockTransactionalTinkerGraph());
        final BadActionNode badNode = graph.addVertex(null, BadActionNode.class);
        final PrioritySerialTrigger trigger = graph.addVertex(null, PrioritySerialTrigger.class);
        final PrioritySerialTriggerEdge triggerEdge = graph.addEdge(null, trigger.asVertex(), badNode.asVertex(), "triggers", PrioritySerialTriggerEdge.class);
        triggerEdge.setTriggerPriority(0);
        triggerEdge.setTriggerAction("badArguments");
        try {
            trigger.trigger();
        }
        catch( final UndeclaredThrowableException caught ) {
            Assert.assertTrue(InvocationTargetException.class.equals(caught.getUndeclaredThrowable().getClass()));
            throw caught;
        }
    }

    @Test(expected = UndeclaredThrowableException.class)
    public void testBadAccess() {
        final FramedTransactionalGraph<?> graph = FACTORY.create(new MockTransactionalTinkerGraph());
        final BadActionNode badNode = graph.addVertex(null, BadActionNode.class);
        final PrioritySerialTrigger trigger = graph.addVertex(null, PrioritySerialTrigger.class);
        final PrioritySerialTriggerEdge triggerEdge = graph.addEdge(null, trigger.asVertex(), badNode.asVertex(), "triggers", PrioritySerialTriggerEdge.class);
        triggerEdge.setTriggerPriority(0);
        triggerEdge.setTriggerAction("badAccess");
        try {
            trigger.trigger();
        }
        catch( final UndeclaredThrowableException caught ) {
            Assert.assertTrue(InvocationTargetException.class.equals(caught.getUndeclaredThrowable().getClass()));
            throw caught;
        }
    }

    @Test(expected = UndeclaredThrowableException.class)
    public void testUnfoundAction() {
        final FramedTransactionalGraph<?> graph = FACTORY.create(new MockTransactionalTinkerGraph());
        final BadActionNode badNode = graph.addVertex(null, BadActionNode.class);
        final PrioritySerialTrigger trigger = graph.addVertex(null, PrioritySerialTrigger.class);
        final PrioritySerialTriggerEdge triggerEdge = graph.addEdge(null, trigger.asVertex(), badNode.asVertex(), "triggers", PrioritySerialTriggerEdge.class);
        triggerEdge.setTriggerPriority(0);
        triggerEdge.setTriggerAction("wontFindThis");
        try {
            trigger.trigger();
        }
        catch( final UndeclaredThrowableException caught ) {
            Assert.assertTrue(InvocationTargetException.class.equals(caught.getUndeclaredThrowable().getClass()));
            throw caught;
        }
    }

    private static boolean checkResult(final double firstValue, final double secondValue) {
        return (Math.abs(firstValue - secondValue) < 0.0000001);
    }
}
