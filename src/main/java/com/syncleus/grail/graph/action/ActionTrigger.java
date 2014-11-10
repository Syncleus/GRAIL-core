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

public interface ActionTrigger extends Node {
    @Action("actionTrigger")
    void trigger();

    @Adjacency(label="triggers")
    Iterable<? extends Node> getTriggers();

    @TypedAdjacency(label="triggers")
    <N extends Node> Iterable<? extends N> getTriggers(Class<? extends N> type);

    @Adjacency(label="triggers")
    void setTriggers(Iterable<? extends Node> targets);

    @Adjacency(label="triggers")
    void removeTrigger(Node target);

    @Adjacency(label="triggers")
    <N extends Node> N addTrigger(N target);

    @Adjacency(label="triggers")
    Node addTrigger();

    @TypedAdjacency(label="triggers")
    <N extends Node> N addTrigger(Class<? extends N> type);

    @Incidence(label = "triggers")
    Iterable<? extends ActionTriggerEdge> getTriggerEdges();

    @TypedIncidence(label="triggers")
    <E extends ActionTriggerEdge> Iterable<? extends E> getTriggerEdges(Class<? extends E> type);

    @Incidence(label = "triggers")
    <E extends ActionTriggerEdge> E addTriggerEdge(E target);

    @Incidence(label = "triggers")
    void removeTriggerEdge(ActionTriggerEdge target);
}
