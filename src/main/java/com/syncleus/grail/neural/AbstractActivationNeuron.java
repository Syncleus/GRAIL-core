package com.syncleus.grail.neural;

import com.syncleus.grail.neural.activation.*;
import com.syncleus.grail.graph.*;
import com.syncleus.grail.neural.backprop.*;
import com.tinkerpop.frames.modules.javahandler.Initializer;

public abstract class AbstractActivationNeuron implements ActivationNeuron {

    private ActivationFunction activationFunction;

    @Initializer
    public void init() {
        this.setActivationFunctionClass(HyperbolicTangentActivationFunction.class);
        this.setActivity(0.0);
    }

    protected ActivationFunction getActivationFunction() {
        final Class<? extends ActivationFunction> activationClass = this.getActivationFunctionClass();
        if( (this.activationFunction != null) && (this.activationFunction.getClass().equals(activationClass)) )
            return this.activationFunction;

        this.activationFunction = null;
        try {
            this.activationFunction = activationClass.newInstance();
        }
        catch( final InstantiationException caughtException ) {
            throw new IllegalStateException("activation function does not have a public default constructor", caughtException);
        }
        catch( final IllegalAccessException caughtException ) {
            throw new IllegalStateException("activation function does not have a public default constructor", caughtException);
        }

        return this.activationFunction;
    }

    @Override
    public void propagate() {
        this.setActivity(0.0);
        for (final SignalMultiplyingEdge currentSynapse : this.getSourceEdges(BackpropSynapse.class)) {
            currentSynapse.propagate();
            this.setActivity(this.getActivity() + currentSynapse.getSignal());
        }
        this.setSignal( this.getActivationFunction().activate(this.getActivity()) );
    }
}
