package com.syncleus.grail.neural.activation;

public class SineActivationFunction implements ActivationFunction {
    private static final double UPPER_LIMIT = 1.0;
    private static final double LOWER_LIMIT = -1.0;

    /**
     * The sine activation function.
     *
     * @param activity the neuron's current activity.
     * @return The result of the sine activation function bound between -1 and 1.
     * @since 1.0
     */
    @Override
    public double activate(final double activity) {
        return Math.sin(activity);
    }

    /**
     * The derivative of the sine activation function.
     *
     * @param activity The neuron's current activity.
     * @return The result of the derivative of the sine activation function.
     * @since 1.0
     */
    @Override
    public double activateDerivative(final double activity) {
        return Math.cos(activity);
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
