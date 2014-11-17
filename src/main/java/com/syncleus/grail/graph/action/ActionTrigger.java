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

/**
 * An ActionTrigger is a graph node which coordinates the firing of action methods across a graph. Action triggers have
 * trigger edges which define the node to be triggered, and the name of the action on the node which is to be triggered.
 * ActionTriggers can be implemented to define various schemes for triggering actions including the order in which
 * actions are triggered as well as if those actions can be triggered in parallel or in serial. ActionTrigger classes
 * can also be chained together to create more elaborate patterns.
 *
 * @since 0.1
 */
public interface ActionTrigger extends Node {
    /**
     * This will initiate the execution of the action's triggered by this node. The trigger method is also annotated as
     * an action with label actionTrigger. This allows for multiple ActionTrigger classes to be chained together.
     *
     * @since 0.1
     */
    @Action("actionTrigger")
    void trigger();

    /**
     * All the nodes in the graph triggered by this Node.
     *
     * @return an iterable collection of nodes to be triggered.
     * @since 0.1
     */
    @Adjacency(label = "triggers")
    Iterable<? extends Node> getTriggers();

    /**
     * All the nodes in the graph of the specified type which are triggered by this node.
     *
     * @param type The type to be retrieved, only triggered nodes of this type of its subtypes will be returned.
     * @param <N> The class type.
     * @return an iterable collection of nodes to be triggered of the specified type.
     */
    @TypedAdjacency(label = "triggers")
    <N extends Node> Iterable<? extends N> getTriggers(Class<? extends N> type);

    /**
     * Remove a node from being triggered. This will remove the trigger edge connecting to the specified node.
     *
     * @param target node which is to be removed.
     * @since 0.1
     */
    @Adjacency(label = "triggers")
    void removeTrigger(Node target);

    /**
     * All edges in the graph which connect to nodes being triggered by this node.
     *
     * @return an iterable collection of trigger edges.
     * @since 0.1
     */
    @Incidence(label = "triggers")
    Iterable<? extends ActionTriggerEdge> getTriggerEdges();

    /**
     * All edges in the graph which connect to nodes being triggered by this node. However this method will only return
     * edges that are of the specified type or a subclass of the type.
     *
     * @param type The class of edges to retrieve
     * @param <E> The class type parameter.
     * @return An iterable collection of ActionTriggerEdges
     * @since 0.1
     */
    @TypedIncidence(label = "triggers")
    <E extends ActionTriggerEdge> Iterable<? extends E> getTriggerEdges(Class<? extends E> type);

    /**
     * Adds a new edge indicating that the target node should be triggered by this node.
     *
     * @param target The new ActionTriggerEdge to add to the graph.
     * @param <E> The type of ActionTriggerEdge
     * @return the same value as the target which was pased in.
     * @since 0.1
     */
    @Incidence(label = "triggers")
    <E extends ActionTriggerEdge> E addTriggerEdge(E target);

    /**
     * Removes the specified AcionTriggerEdge from the graph. The target of the edge will no longer be triggered by this
     * node.
     *
     * @param target The ActionTriggerEdge to remove from the graph.
     * @since 0.1
     */
    @Incidence(label = "triggers")
    void removeTriggerEdge(ActionTriggerEdge target);
}
