package com.syncleus.grail.graph;

import com.tinkerpop.frames.*;
import com.tinkerpop.frames.annotations.gremlin.GremlinGroovy;

public interface Location extends VertexFrame {
    @Property("name")
    public String getName();

    @Property("type")
    public String getType();

    @GremlinGroovy("it.in('lives')")
    public Iterable<God> getResidents();
}
