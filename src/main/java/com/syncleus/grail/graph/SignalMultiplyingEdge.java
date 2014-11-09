package com.syncleus.grail.graph;

import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.javahandler.*;
import com.tinkerpop.frames.modules.typedgraph.*;

@TypeField("type")
@TypeValue("Synapse")
@JavaHandlerClass(AbstractSignalMultiplyingEdge.class)
public interface SignalMultiplyingEdge extends Weighted, Signaler, EdgeFrame {
    @OutVertex
    Signaler getSource();

    @JavaHandler
    void propagate();
}