package com.syncleus.grail.graph;

import com.tinkerpop.frames.Property;

public interface LocationExtended extends Location {
    @Property("other")
    public String getOther();
}
