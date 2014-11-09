package com.syncleus.grail.neural.activation;

public class HyperbolicTangentActivationFunction implements ActivationFunction {
    private static final double UPPER_LIMIT = 1.0;
    private static final double LOWER_LIMIT = -1.0;

    /**
     * The hyperbolic tangent activation function.
     *
     * @param activity the neuron's current activity.
     * @return The result of the hyperbolic tangent activation function bound
     * between -1 and 1.
     * @since 1.0
     */
    @Override
    public double activate(final double activity) {
        return Math.tanh(activity);
    }

    /**
     * The derivative of the hyperbolic tangent activation function.
     *
     * @param activity The neuron's current activity.
     * @return The result of the derivative of the hyperbolic tangent activation
     * function.
     * @since 1.0
     */
    @Override
    public double activateDerivative(final double activity) {
        return 1.0 - Math.pow(this.activate(activity), 2.0);
    }

    @Override
    public boolean isBound() {
        return true;
    }

    @Override
    public double getUpperLimit() {
        return UPPER_LIMIT;
    }

    @Override
    public double getLowerLimit() {
        return LOWER_LIMIT;
    }
}
