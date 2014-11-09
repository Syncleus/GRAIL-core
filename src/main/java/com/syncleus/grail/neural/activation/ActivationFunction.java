package com.syncleus.grail.neural.activation;

public interface ActivationFunction {
    /**
     * The activation function.
     *
     * @param activity the neuron's current activity.
     * @return The result of the activation function. Usually a bound value
     * between 1 and -1 or 1 and 0. However this bound range is not
     * required.
     * @since 1.0
     */
    double activate(double activity);

    /**
     * The derivative of the activation function.
     *
     * @param activity The neuron's current activity.
     * @return The result of the derivative of the activation function.
     * @since 1.0
     */
    double activateDerivative(double activity);

    boolean isBound();

    double getUpperLimit();

    double getLowerLimit();
}
