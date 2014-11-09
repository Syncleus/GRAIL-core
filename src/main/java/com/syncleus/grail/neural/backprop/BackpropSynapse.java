package com.syncleus.grail.neural.backprop;

import com.syncleus.grail.graph.SignalMultiplyingEdge;
import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.javahandler.JavaHandlerClass;
import com.tinkerpop.frames.modules.typedgraph.TypeValue;

@TypeValue("BackpropSynapse")
public interface BackpropSynapse extends SignalMultiplyingEdge {
    @InVertex
    BackpropNeuron getTarget();

    @OutVertex
    BackpropNeuron getSource();
}
