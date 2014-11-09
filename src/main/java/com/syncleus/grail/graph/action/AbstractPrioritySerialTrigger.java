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

            Map<String, Set<Method>> actionMethods = AbstractPrioritySerialTrigger.populateCache(parentClass);

            final Set<Method> triggerMethods = actionMethods.get(actionName);
            if( triggerMethods == null || triggerMethods.size() <= 0 )
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
                else if(priorityA > priorityB)
                    return 1;
                else
                    return -1;
            }
        })._();
        return this.frameEdges(prioritiesTriggersPipe, PrioritySerialTriggerEdge.class);
    }
}
