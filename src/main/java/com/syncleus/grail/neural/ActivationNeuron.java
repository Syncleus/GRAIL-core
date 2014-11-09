package com.syncleus.grail.neural;

import com.syncleus.grail.neural.activation.ActivationFunction;
import com.syncleus.grail.graph.*;
import com.syncleus.grail.graph.action.Action;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.javahandler.*;
import com.tinkerpop.frames.modules.typedgraph.*;

@TypeValue("ActivationNeuron")
@JavaHandlerClass(AbstractActivationNeuron.class)
public interface ActivationNeuron extends SignalNode {
    @JavaHandler
    @Action("propagate")
    void propagate();

    @Property("activity")
    Double getActivity();

    @Property("activity")
    void setActivity(double activity);

    @Property("activationFunction")
    Class<? extends ActivationFunction> getActivationFunctionClass();

    @Property("activationFunction")
    void setActivationFunctionClass(Class<? extends ActivationFunction> activationFunctionClass);

    @Adjacency(label="signals", direction= Direction.IN)
    Iterable<? extends Node> getSources();

    @TypedAdjacency(label="signals", direction=Direction.IN)
    <N extends Node> Iterable<? extends N> getSources(Class<? extends N> type);

    @Adjacency(label="signals", direction=Direction.IN)
    void setSources(Iterable<? extends SignalNode> targets);

    @Adjacency(label="signals", direction=Direction.IN)
    void removeSource(SignalNode target);

    @Adjacency(label="signals", direction=Direction.IN)
    <N extends SignalNode> N addSource(N target);

    @Adjacency(label="signals", direction=Direction.IN)
    Signaler addSource();

    @TypedAdjacency(label="signals", direction=Direction.IN)
    <N extends SignalNode> N addSource(Class<? extends N> type);

    @Incidence(label = "signals", direction=Direction.IN)
    Iterable<? extends SignalMultiplyingEdge> getSourceEdges();

    @TypedIncidence(label="signals", direction=Direction.IN)
    <E extends SignalMultiplyingEdge> Iterable<? extends E> getSourceEdges(Class<? extends E> type);

    @Incidence(label = "signals", direction=Direction.IN)
    <E extends SignalMultiplyingEdge> E addSourceEdge(SignalMultiplyingEdge target);

    @Incidence(label = "signals", direction=Direction.IN)
    void removeSourceEdge(SignalMultiplyingEdge source);
}
