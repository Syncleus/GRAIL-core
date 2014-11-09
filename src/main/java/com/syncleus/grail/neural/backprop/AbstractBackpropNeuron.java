package com.syncleus.grail.neural.backprop;

import com.syncleus.grail.neural.*;
import com.tinkerpop.frames.modules.javahandler.Initializer;

public abstract class AbstractBackpropNeuron extends AbstractActivationNeuron implements BackpropNeuron {
    @Initializer
    public void init() {
        this.setLearningRate(0.0175);
        this.setDeltaTrain(0.0);
    }

    @Override
    public void backpropagate() {
        for (final BackpropSynapse synapse : this.getTargetEdges(BackpropSynapse.class)) {
            final BackpropNeuron target = synapse.getTarget();
            synapse.setWeight(synapse.getWeight() + (target.getDeltaTrain() * target.getLearningRate() * this.getSignal()));
        }

        double newDeltaTrain = 0.0;
        for (final BackpropSynapse synapse : this.getTargetEdges(BackpropSynapse.class)) {
            final BackpropNeuron target = synapse.getTarget();
            assert synapse.getWeight() != null;
            assert target.getDeltaTrain() != null;
            newDeltaTrain += (synapse.getWeight() * target.getDeltaTrain());
        }
        newDeltaTrain *= this.getActivationFunction().activateDerivative(this.getActivity());
        this.setDeltaTrain(newDeltaTrain);
    }
}
