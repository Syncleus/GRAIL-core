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
import com.syncleus.ferma.annotations.InVertex;
import com.syncleus.ferma.annotations.OutVertex;
import com.syncleus.ferma.annotations.Property;

/**
 * A graph edge which connects an action trigger to the node which it triggers actions on.
 *
 * @since 0.1
 */
public interface ActionTriggerEdge extends EdgeFrame {
    /**
     * Gets the name of the action to be triggered. The target node must have at least one valid method annotated as an
     * action with this name.
     *
     * @return The name of the action to trigger.
     * @since 0.1
     */
    @Property("triggerAction")
    String getTriggerAction();

    /**
     * Sets the name of the action to be triggered. The target node must have at least one valid method annotated as an
     * action with this name.
     *
     * @param triggerAction The new name for the action to be triggered.
     * @since 0.1
     */
    @Property("triggerAction")
    void setTriggerAction(String triggerAction);

    /**
     * The node to be triggered.
     *
     * @return the node to be triggered
     * @since 0.1
     */
    @InVertex
    Object getTarget();

    /**
     * The action trigger capable of triggering the target node.
     *
     * @return the ActionTrigger
     * @since 0.1
     */
    @OutVertex
    ActionTrigger getSource();
}
