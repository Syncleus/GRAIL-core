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
