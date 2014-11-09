package com.syncleus.grail.graph.action;

import com.syncleus.grail.graph.Node;
import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.typedgraph.TypeValue;

@TypeValue("PrioritySerialTriggerEdge")
public interface PrioritySerialTriggerEdge extends ActionTriggerEdge {
    @Property("triggerPriority")
    Integer getTriggerPriority();

    @Property("triggerPriority")
    void setTriggerPriority(int triggerAction);

    @InVertex
    Node getTarget();

    @OutVertex
    ActionTrigger getSource();
}
