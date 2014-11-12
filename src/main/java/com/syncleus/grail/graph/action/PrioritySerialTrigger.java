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

import com.tinkerpop.frames.annotations.gremlin.GremlinGroovy;
import com.tinkerpop.frames.modules.javahandler.*;
import com.tinkerpop.frames.modules.typedgraph.TypeValue;

/**
 * An action trigger which triggers its actions in serial and ordered by their priority. Each out trigger edge from this
 * node will be a PrioritySerialTriggerEdge which has a triggerPriority property. This property is an integer value
 * which determines the priority of the edge, a higher value means preferential priority over a lower value. When
 * triggered the actions associated with each edge will be triggered in series ordered by priority.
 *
 * @since 0.1
 */
@TypeValue("PrioritySerialTrigger")
@JavaHandlerClass(AbstractPrioritySerialTrigger.class)
public interface PrioritySerialTrigger extends ActionTrigger {
    @JavaHandler
    @Action("actionTrigger")
    @Override
    void trigger();

    /**
     * Get all the prioritized trigger edges connecting to the target nodes to act on. These edges will be returned such
     * that they are sorted from highest priority to the lowest.
     *
     * @return An iterable collection of PrioritySerialTriggerEdges from highest to lowest priority.
     * @since 0.1
     */
    @JavaHandler
    Iterable<? extends PrioritySerialTriggerEdge> getPrioritizedTriggerEdges();
}
