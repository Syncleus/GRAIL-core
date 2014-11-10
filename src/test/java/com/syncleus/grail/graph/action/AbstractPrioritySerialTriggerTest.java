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

import com.syncleus.grail.graph.BlankGraphFactory;
import com.syncleus.grail.neural.ActivationNeuron;
import com.syncleus.grail.neural.activation.*;
import com.tinkerpop.frames.FramedTransactionalGraph;
import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.*;

public class AbstractPrioritySerialTriggerTest {
    @Test(expected = UndeclaredThrowableException.class )
    public void testBadArguments() {
        final FramedTransactionalGraph<?> graph = BlankGraphFactory.makeTinkerGraph();
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
        final FramedTransactionalGraph<?> graph = BlankGraphFactory.makeTinkerGraph();
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
        final FramedTransactionalGraph<?> graph = BlankGraphFactory.makeTinkerGraph();
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
}
