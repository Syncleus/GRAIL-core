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

import com.syncleus.grail.graph.Node;
import com.tinkerpop.blueprints.*;
import com.tinkerpop.frames.modules.javahandler.JavaHandlerContext;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.util.structures.Pair;

import java.lang.reflect.*;
import java.util.*;

public abstract class AbstractPrioritySerialTrigger extends AbstractActionTrigger implements PrioritySerialTrigger, JavaHandlerContext {
    @Override
    public void trigger() {
        for( final PrioritySerialTriggerEdge triggerEdge : this.getPrioritizedTriggerEdges() ) {
            final String actionName = triggerEdge.getTriggerAction();

            final Node triggerObject = triggerEdge.getTarget();

            final Class<?> parentClass = triggerObject.getClass();

            final Map<String, Set<Method>> actionMethods = AbstractPrioritySerialTrigger.populateCache(parentClass);

            final Set<Method> triggerMethods = actionMethods.get(actionName);
            if( triggerMethods == null || triggerMethods.isEmpty() )
                throw new IllegalStateException("A ActionTrigger was configured to trigger an action which does not exist on the current object");

            for( final Method triggerMethod : triggerMethods ) {
                try {
                    triggerMethod.invoke(triggerObject, null);
                }
                catch (final IllegalAccessException caught) {
                    throw new IllegalStateException("Tried to trigger an action method but can not access", caught);
                }
                catch (final InvocationTargetException caught) {
                    throw new IllegalStateException("Tried to trigger an action method but can not access", caught);
                }
            }
        }
    }


    @Override
    public Iterable<? extends PrioritySerialTriggerEdge> getPrioritizedTriggerEdges() {
        final GremlinPipeline<Vertex,Edge> prioritiesTriggersPipe = new GremlinPipeline<Vertex,Vertex>().start(this.asVertex()).outE("triggers").order(new PipeFunction<Pair<Edge, Edge>, Integer>() {
            @Override
            public Integer compute(final Pair<Edge, Edge> argument) {
                final int priorityA = java.lang.Integer.parseInt(argument.getA().getProperty("triggerPriority").toString());
                final int priorityB = java.lang.Integer.parseInt(argument.getB().getProperty("triggerPriority").toString());
                if( priorityA == priorityB )
                    return 0;
                else if(priorityA < priorityB)
                    return 1;
                else
                    return -1;
            }
        })._();
        return this.frameEdges(prioritiesTriggersPipe, PrioritySerialTriggerEdge.class);
    }
}
