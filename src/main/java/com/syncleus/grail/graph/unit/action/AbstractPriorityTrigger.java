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
import java.util.Comparator;

/**
 * An action trigger which triggers its actions in serial and ordered by their priority. Each out trigger edge from this
 * node will be a PrioritySerialTriggerEdge which has a triggerPriority property. This property is an integer value
 * which determines the priority of the edge, a higher value means preferential priority over a lower value. When
 * triggered the actions associated with each edge will be triggered in series ordered by priority.
 *
 * @since 0.1
 */
public abstract class AbstractPriorityTrigger extends AbstractActionTrigger implements ActionTrigger {
    /**
     * Get all the prioritized trigger edges connecting to the target nodes to act on. These edges will be returned such
     * that they are sorted from highest priority to the lowest.
     *
     * @return An iterable collection of PrioritySerialTriggerEdges from highest to lowest priority.
     * @since 0.1
     */
    public Iterable<? extends PrioritySerialTriggerEdge> getPrioritizedTriggerEdges() {
        return this.outE("triggers").order(new Comparator<EdgeFrame>() {
            @Override
            public int compare(EdgeFrame tEdge, EdgeFrame t1) {
                final int priorityA = java.lang.Integer.parseInt(tEdge.getProperty("triggerPriority").toString());
                final int priorityB = java.lang.Integer.parseInt(t1.getProperty("triggerPriority").toString());
                if( priorityA == priorityB )
                    return 0;
                else if(priorityA < priorityB)
                    return 1;
                else
                    return -1;
            }
        }).frame(PrioritySerialTriggerEdge.class);
    }
}
