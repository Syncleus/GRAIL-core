package com.syncleus.grail.neural.backprop;

import com.syncleus.grail.neural.activation.*;
import com.syncleus.grail.graph.*;
import com.tinkerpop.frames.*;
import org.junit.*;
import java.util.*;

public class SimpleXor2InputTest {
    @Test
    public void testXor() {
        final FramedTransactionalGraph<?> graph = BlankGraphFactory.makeTinkerGraph();

        final List<BackpropNeuron> newInputNeurons = new ArrayList<BackpropNeuron>(2);
        newInputNeurons.add(SimpleXor2InputTest.createNeuron(graph, "input"));
        newInputNeurons.add(SimpleXor2InputTest.createNeuron(graph, "input"));
        final List<BackpropNeuron> newHiddenNeurons = new ArrayList<BackpropNeuron>(4);
        newHiddenNeurons.add(SimpleXor2InputTest.createNeuron(graph, "hidden"));
        newHiddenNeurons.add(SimpleXor2InputTest.createNeuron(graph, "hidden"));
        newHiddenNeurons.add(SimpleXor2InputTest.createNeuron(graph, "hidden"));
        newHiddenNeurons.add(SimpleXor2InputTest.createNeuron(graph, "hidden"));
        final BackpropNeuron newOutputNeuron = SimpleXor2InputTest.createNeuron(graph, "output");
        newOutputNeuron.setActivationFunctionClass(HyperbolicTangentActivationFunction.class);
        newOutputNeuron.setLearningRate(0.09);

        //connect all input neurons to hidden neurons
        for( BackpropNeuron inputNeuron : newInputNeurons ) {
            //make sure all input neurons use tanH activation function
            inputNeuron.setActivationFunctionClass(HyperbolicTangentActivationFunction.class);
            inputNeuron.setLearningRate(0.09);
            for( BackpropNeuron hiddenNeuron : newHiddenNeurons ) {
                graph.addEdge(null, inputNeuron.asVertex(), hiddenNeuron.asVertex(), "signals", BackpropSynapse.class);
            }
        }
        //connect all hidden neurons to the output neuron
        for( BackpropNeuron hiddenNeuron : newHiddenNeurons ) {
            graph.addEdge(null, hiddenNeuron.asVertex(), newOutputNeuron.asVertex(), "signals", BackpropSynapse.class);

            //all hidden neurons shoudl use tanh activation function
            hiddenNeuron.setActivationFunctionClass(HyperbolicTangentActivationFunction.class);
            hiddenNeuron.setLearningRate(0.09);

            //create bias neuron
            final BackpropNeuron biasNeuron = SimpleXor2InputTest.createNeuron(graph, "bias");
            biasNeuron.setSignal(1.0);
            biasNeuron.setActivationFunctionClass(HyperbolicTangentActivationFunction.class);
            biasNeuron.setLearningRate(0.09);
            graph.addEdge(null, biasNeuron.asVertex(), hiddenNeuron.asVertex(), "signals", BackpropSynapse.class);
        }
        //create bias neuron for output neuron
        final BackpropNeuron biasNeuron = SimpleXor2InputTest.createNeuron(graph, "bias");
        biasNeuron.setSignal(1.0);
        biasNeuron.setLearningRate(0.09);
        biasNeuron.setActivationFunctionClass(HyperbolicTangentActivationFunction.class);
        graph.addEdge(null, biasNeuron.asVertex(), newOutputNeuron.asVertex(), "signals", BackpropSynapse.class);
        graph.commit();

        for(int i = 0; i < 1000; i++) {
            SimpleXor2InputTest.train(graph, -1.0, 1.0, 1.0);
            SimpleXor2InputTest.train(graph, 1.0, -1.0, 1.0);
            SimpleXor2InputTest.train(graph, 1.0, 1.0, -1.0);
            SimpleXor2InputTest.train(graph, -1.0, -1.0, -1.0);
        }
        Assert.assertTrue(SimpleXor2InputTest.propagate(graph, 1.0, 1.0) < 0.0);
        Assert.assertTrue(SimpleXor2InputTest.propagate(graph, -1.0, -1.0) < 0.0);
        Assert.assertTrue(SimpleXor2InputTest.propagate(graph, 1.0, -1.0) > 0.0);
        Assert.assertTrue(SimpleXor2InputTest.propagate(graph, -1.0, 1.0) > 0.0);
    }

    private static final ActivationFunction activationFunction = new SineActivationFunction();

    private static void train(final FramedTransactionalGraph<?> graph, final double input1, final double input2, final double expected) {
        SimpleXor2InputTest.propagate(graph, input1, input2);

        final Iterator<BackpropNeuron> outputNeurons = graph.getVertices("layer", "output", BackpropNeuron.class).iterator();
        final BackpropNeuron outputNeuron = outputNeurons.next();
        Assert.assertTrue(!outputNeurons.hasNext());
        outputNeuron.setDeltaTrain((expected - outputNeuron.getSignal()) * activationFunction.activateDerivative(outputNeuron.getActivity()) );
        graph.commit();

        final Iterator<BackpropNeuron> hiddenNeurons = graph.getVertices("layer", "hidden", BackpropNeuron.class).iterator();
        hiddenNeurons.next().backpropagate();
        hiddenNeurons.next().backpropagate();
        hiddenNeurons.next().backpropagate();
        hiddenNeurons.next().backpropagate();
        Assert.assertTrue(!hiddenNeurons.hasNext());
        graph.commit();

        final Iterator<BackpropNeuron> inputNeurons = graph.getVertices("layer", "input", BackpropNeuron.class).iterator();
        inputNeurons.next().backpropagate();
        inputNeurons.next().backpropagate();
        Assert.assertTrue(!inputNeurons.hasNext());
        graph.commit();

        final Iterator<BackpropNeuron> biasNeurons = graph.getVertices("layer", "bias", BackpropNeuron.class).iterator();
        biasNeurons.next().backpropagate();
        biasNeurons.next().backpropagate();
        biasNeurons.next().backpropagate();
        biasNeurons.next().backpropagate();
        biasNeurons.next().backpropagate();
        Assert.assertTrue(!biasNeurons.hasNext());
        graph.commit();
    }

    private static double propagate(final FramedTransactionalGraph<?> graph, final double input1, final double input2) {
        final Iterator<BackpropNeuron> inputNeurons = graph.getVertices("layer", "input", BackpropNeuron.class).iterator();
        inputNeurons.next().setSignal(input1);
        inputNeurons.next().setSignal(input2);
        Assert.assertTrue(!inputNeurons.hasNext());
        graph.commit();

        final Iterator<BackpropNeuron> hiddenNeurons = graph.getVertices("layer", "hidden", BackpropNeuron.class).iterator();
        hiddenNeurons.next().propagate();
        hiddenNeurons.next().propagate();
        hiddenNeurons.next().propagate();
        hiddenNeurons.next().propagate();
        Assert.assertTrue(!hiddenNeurons.hasNext());
        graph.commit();

        final Iterator<BackpropNeuron> outputNeurons = graph.getVertices("layer", "output", BackpropNeuron.class).iterator();
        final BackpropNeuron outputNeuron = outputNeurons.next();
        Assert.assertTrue(!outputNeurons.hasNext());
        outputNeuron.propagate();
        graph.commit();
        return outputNeuron.getSignal();
    }

    private static BackpropNeuron createNeuron(final FramedGraph<?> graph, final String layer) {
        final BackpropNeuron neuron = graph.addVertex(null, BackpropNeuron.class);
        neuron.asVertex().setProperty("layer", layer);
        return neuron;
    }
}
