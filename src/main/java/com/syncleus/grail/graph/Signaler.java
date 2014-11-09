package com.syncleus.grail.graph;

import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.modules.typedgraph.TypeField;

public interface Signaler {
    @Property("signal")
    Double getSignal();

    @Property("signal")
    void setSignal(double signal);
}
