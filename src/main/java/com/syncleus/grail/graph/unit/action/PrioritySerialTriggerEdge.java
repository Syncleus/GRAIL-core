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

import com.syncleus.ferma.annotations.InVertex;
import com.syncleus.ferma.annotations.OutVertex;
import com.syncleus.ferma.annotations.Property;

/**
 * A prioritized trigger edge. These are used when trigger edges need to be executed in sequential order by a
 * PrioritySerialTrigger.
 *
 * @since 0.1
 */
public interface PrioritySerialTriggerEdge extends ActionTriggerEdge {
    /**
     * Get the triggerPriority property, an integer value indicating the priority of this edge and its associated
     * action.
     *
     * @return an integer value representing the trigger priority.
     * @since 0.1
     */
    @Property("triggerPriority")
    Integer getTriggerPriority();

    /**
     * Set the triggerPriority property, an integer value indicating the priority of this edge and its associated
     * action.
     * @param triggerPriority the new priority value.
     * @since 0.1
     */
    @Property("triggerPriority")
    void setTriggerPriority(int triggerPriority);

    @Override
    @InVertex
    Object getTarget();

    @Override
    @OutVertex
    ActionTrigger getSource();
}
