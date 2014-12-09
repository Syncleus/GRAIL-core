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

import com.syncleus.ferma.EdgeFrame;
import java.lang.reflect.*;
import java.util.*;

/**
 * This is the Java handler class associated with the ActionTrigger type. It defines how the Trigger is to be executed.
 * In this case all triggers are executed in serial within the current thread.
 *
 * @since 0.1
 */
public abstract class SerialPriorityTrigger extends AbstractPriorityTrigger {
    /**
     * This will initiate the execution of the action's triggered by this node. The trigger method is also annotated as
     * an action with label actionTrigger. This allows for multiple ActionTrigger classes to be chained together.
     * Specifically this class executes the triggers in serial by order of the edge's priority value property. All the
     * actions are executed in series in the existing thread.
     *
     * @since 0.1
     */
    @Override
    public void trigger() {
        for( final PrioritySerialTriggerEdge triggerEdge : this.getPrioritizedTriggerEdges() ) {
            final String actionName = triggerEdge.getTriggerAction();

            final Object triggerObject = triggerEdge.getTarget();

            final Class<?> parentClass = triggerObject.getClass();

            final Map<String, Set<Method>> actionMethods = SerialPriorityTrigger.populateCache(parentClass);

            final Set<Method> triggerMethods = actionMethods.get(actionName);
            if( triggerMethods == null || triggerMethods.isEmpty() )
                throw new IllegalStateException("A ActionTrigger was configured to trigger an action which does not exist on the current object");

            for( final Method triggerMethod : triggerMethods ) {
                try {
                    triggerMethod.invoke(triggerObject, null);
                }
                catch (final IllegalAccessException caught) {
                    caught.printStackTrace();
                    throw new IllegalStateException("Tried to trigger an action method but can not access", caught);
                }
                catch (final InvocationTargetException caught) {
                    caught.printStackTrace();
                    throw new IllegalStateException("Tried to trigger an action method but can not access", caught);
                }
            }
        }
    }
}
