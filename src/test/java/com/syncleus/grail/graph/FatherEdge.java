package com.syncleus.grail.graph;

import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.typedgraph.*;

@TypeField("classType")
@TypeValue("Father")
public interface FatherEdge extends EdgeFrame {
    @InVertex
    God getFather();

    @OutVertex
    God getSon();
}
