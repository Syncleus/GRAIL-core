package com.syncleus.grail.graph;

import com.tinkerpop.frames.annotations.gremlin.GremlinGroovy;
import com.tinkerpop.frames.modules.typedgraph.TypeValue;

@TypeValue("GodExtended")
public interface GodExtended extends God {
    @GremlinGroovy("it.in('father').in('father')")
    God getGrandson();
}
