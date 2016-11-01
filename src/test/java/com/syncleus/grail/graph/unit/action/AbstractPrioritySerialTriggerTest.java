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
package com.syncleus.grail.graph.unit.action;

import com.syncleus.grail.graph.*;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class AbstractPrioritySerialTriggerTest {
    private static final Set<Class<?>> TEST_TYPES = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{
            SimpleActionNode.class,
            BadActionNode.class,
            AbstractSimpleActionNode.class,
            SerialPriorityTrigger.class}));

    @Test
    public void testDoesTrigger() {
        final GrailGraph<TinkerGraph> graph = new TinkerGrailGraphFactory(TEST_TYPES).subgraph("0");

        // construct graph
        final SimpleActionNode actionNode = graph.addFramedVertex(AbstractSimpleActionNode.class);
        final AbstractPriorityTrigger trigger = graph.addFramedVertex(SerialPriorityTrigger.class);
        final PrioritySerialTriggerEdge triggerEdge = graph.addFramedEdge( trigger,  actionNode, "triggers", PrioritySerialTriggerEdge.class);
        triggerEdge.setTriggerAction("action");
        triggerEdge.setTriggerPriority(0);

        Assert.assertNull(actionNode.getDone());

        //process the weight
        trigger.trigger();

        Assert.assertTrue(actionNode.getDone());
    }

    @Test
    public void testDoesTriggerInOrder() {
        final GrailGraph<TinkerGraph> graph = new TinkerGrailGraphFactory(TEST_TYPES).subgraph("0");

        final List<String> triggerOrder = new ArrayList<String>(2);

        // construct graph
        final SimpleActionNode firstActionNode = graph.addFramedVertex(AbstractSimpleActionNode.class);
        firstActionNode.setTriggerOrder(triggerOrder);
        final SimpleActionNode secondActionNode = graph.addFramedVertex(AbstractSimpleActionNode.class);
        secondActionNode.setTriggerOrder(triggerOrder);
        final AbstractPriorityTrigger trigger = graph.addFramedVertex(SerialPriorityTrigger.class);
        final PrioritySerialTriggerEdge firstTriggerEdge = graph.addFramedEdge( trigger,  firstActionNode, "triggers", PrioritySerialTriggerEdge.class);
        firstTriggerEdge.setTriggerAction("first");
        firstTriggerEdge.setTriggerPriority(1000);
        final PrioritySerialTriggerEdge secondTriggerEdge = graph.addFramedEdge( trigger,  secondActionNode, "triggers", PrioritySerialTriggerEdge.class);
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
        final GrailGraph<TinkerGraph> graph = new TinkerGrailGraphFactory(TEST_TYPES).subgraph("0");

        final List<String> triggerOrder = new ArrayList<String>(2);

        // construct graph
        final SimpleActionNode firstActionNode = graph.addFramedVertex(AbstractSimpleActionNode.class);
        firstActionNode.setTriggerOrder(triggerOrder);
        final SimpleActionNode secondActionNode = graph.addFramedVertex(AbstractSimpleActionNode.class);
        secondActionNode.setTriggerOrder(triggerOrder);
        final AbstractPriorityTrigger trigger = graph.addFramedVertex(SerialPriorityTrigger.class);
        final PrioritySerialTriggerEdge secondTriggerEdge = graph.addFramedEdge( trigger,  secondActionNode, "triggers", PrioritySerialTriggerEdge.class);
        secondTriggerEdge.setTriggerAction("second");
        secondTriggerEdge.setTriggerPriority(0);
        final PrioritySerialTriggerEdge firstTriggerEdge = graph.addFramedEdge( trigger,  firstActionNode, "triggers", PrioritySerialTriggerEdge.class);
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

    @Test
    public void testDoesTriggerInOrderSameOrdering() {
        final GrailGraph<TinkerGraph> graph = new TinkerGrailGraphFactory(TEST_TYPES).subgraph("0");

        final List<String> triggerOrder = new ArrayList<String>(2);

        // construct graph
        final SimpleActionNode firstActionNode = graph.addFramedVertex(AbstractSimpleActionNode.class);
        firstActionNode.setTriggerOrder(triggerOrder);
        final SimpleActionNode secondActionNode = graph.addFramedVertex(AbstractSimpleActionNode.class);
        secondActionNode.setTriggerOrder(triggerOrder);
        final SimpleActionNode thirdActionNode = graph.addFramedVertex(AbstractSimpleActionNode.class);
        thirdActionNode.setTriggerOrder(triggerOrder);
        final AbstractPriorityTrigger trigger = graph.addFramedVertex(SerialPriorityTrigger.class);
        final PrioritySerialTriggerEdge firstTriggerEdge = graph.addFramedEdge( trigger,  firstActionNode, "triggers", PrioritySerialTriggerEdge.class);
        firstTriggerEdge.setTriggerAction("first");
        firstTriggerEdge.setTriggerPriority(1000);
        final PrioritySerialTriggerEdge secondTriggerEdge = graph.addFramedEdge( trigger,  secondActionNode, "triggers", PrioritySerialTriggerEdge.class);
        secondTriggerEdge.setTriggerAction("second");
        secondTriggerEdge.setTriggerPriority(1000);
        final PrioritySerialTriggerEdge thirdTriggerEdge = graph.addFramedEdge( trigger,  thirdActionNode, "triggers", PrioritySerialTriggerEdge.class);
        thirdTriggerEdge.setTriggerAction("third");
        thirdTriggerEdge.setTriggerPriority(0);

        Assert.assertTrue(triggerOrder.isEmpty());

        //process the weight
        trigger.trigger();

        //make sure we clear out the action node since the attribute is static.
        firstActionNode.setTriggerOrder(null);
        secondActionNode.setTriggerOrder(null);
        thirdActionNode.setTriggerOrder(null);

        Assert.assertTrue(triggerOrder.size() == 3);
        Assert.assertTrue(triggerOrder.get(0).equals("first") || triggerOrder.get(0).equals("second"));
        Assert.assertTrue(triggerOrder.get(1).equals("first") || triggerOrder.get(1).equals("second"));
        Assert.assertTrue(triggerOrder.get(2).equals("third"));
    }

    @Test(expected = IllegalStateException.class )
    public void testBadArguments() {
        final GrailGraph<TinkerGraph> graph = new TinkerGrailGraphFactory(TEST_TYPES).subgraph("0");

        final BadActionNode badNode = graph.addFramedVertex(AbstractBadActionNode.class);
        final AbstractPriorityTrigger trigger = graph.addFramedVertex(SerialPriorityTrigger.class);
        final PrioritySerialTriggerEdge triggerEdge = graph.addFramedEdge( trigger,  badNode, "triggers", PrioritySerialTriggerEdge.class);
        triggerEdge.setTriggerPriority(0);
        triggerEdge.setTriggerAction("badArguments");
        trigger.trigger();
    }

    @Test(expected = IllegalStateException.class)
    public void testBadAccess() {
        final GrailGraph<TinkerGraph> graph = new TinkerGrailGraphFactory(TEST_TYPES).subgraph("0");

        final BadActionNode badNode = graph.addFramedVertex(AbstractBadActionNode.class);
        final AbstractPriorityTrigger trigger = graph.addFramedVertex(SerialPriorityTrigger.class);
        final PrioritySerialTriggerEdge triggerEdge = graph.addFramedEdge( trigger,  badNode, "triggers", PrioritySerialTriggerEdge.class);
        triggerEdge.setTriggerPriority(0);
        triggerEdge.setTriggerAction("badAccess");
        trigger.trigger();
    }

    @Test(expected = IllegalStateException.class)
    public void testUnfoundAction() {
        final GrailGraph<TinkerGraph> graph = new TinkerGrailGraphFactory(TEST_TYPES).subgraph("0");

        final BadActionNode badNode = graph.addFramedVertex(AbstractBadActionNode.class);
        final AbstractPriorityTrigger trigger = graph.addFramedVertex(SerialPriorityTrigger.class);
        final PrioritySerialTriggerEdge triggerEdge = graph.addFramedEdge( trigger,  badNode, "triggers", PrioritySerialTriggerEdge.class);
        triggerEdge.setTriggerPriority(0);
        triggerEdge.setTriggerAction("wontFindThis");
        trigger.trigger();
    }

    private static boolean checkResult(final double firstValue, final double secondValue) {
        return (Math.abs(firstValue - secondValue) < 0.0000001);
    }
}
