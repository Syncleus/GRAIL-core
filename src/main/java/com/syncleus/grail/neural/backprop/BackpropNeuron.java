package com.syncleus.grail.neural.backprop;

import com.syncleus.grail.graph.*;
import com.syncleus.grail.graph.action.Action;
import com.syncleus.grail.neural.*;
import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.javahandler.*;
import com.tinkerpop.frames.modules.typedgraph.TypeValue;

@TypeValue("BackpropNeuron")
@JavaHandlerClass(AbstractBackpropNeuron.class)
public interface BackpropNeuron extends ActivationNeuron {
    @JavaHandler
    @Action("backpropagate")
    void backpropagate();

    @Property("learningRate")
    Double getLearningRate();

    @Property("learningRate")
    void setLearningRate(double learningRate);

    @Property("deltaTrain")
    Double getDeltaTrain();

    @Property("deltaTrain")
    void setDeltaTrain(double deltaTrain);

    @Adjacency(label="signals")
    Iterable<? extends BackpropNeuron> getTargets();

    @TypedAdjacency(label="signals")
    <N extends BackpropNeuron> Iterable<? extends N> getTargets(Class<? extends N> type);

    @Adjacency(label="signals")
    void setTargets(Iterable<? extends BackpropNeuron> targets);

    @Adjacency(label="signals")
    void removeTarget(BackpropNeuron target);

    @Adjacency(label="signals")
    <N extends BackpropNeuron> N addTarget(N target);

    @Adjacency(label="signals")
    BackpropNeuron addTarget();

    @TypedAdjacency(label="signals")
    <N extends BackpropNeuron> N addTarget(Class<? extends N> type);

    @Incidence(label = "signals")
    Iterable<? extends BackpropSynapse> getTargetEdges();

    @TypedIncidence(label="signals")
    <E extends BackpropSynapse> Iterable<? extends E> getTargetEdges(Class<? extends E> type);

    @Incidence(label = "signals")
    <E extends BackpropSynapse> E addTargetEdge(E target);

    @Incidence(label = "signals")
    void removeTargetEdge(BackpropSynapse target);

}
