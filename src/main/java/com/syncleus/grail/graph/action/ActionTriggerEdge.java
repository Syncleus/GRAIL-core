package com.syncleus.grail.graph.action;

import com.syncleus.grail.graph.Node;
import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.typedgraph.*;

@TypeField("type")
@TypeValue("ActionTriggerEdge")
public interface ActionTriggerEdge extends EdgeFrame {
    @Property("triggerAction")
    String getTriggerAction();

    @Property("triggerAction")
    void setTriggerAction(String triggerAction);

    @InVertex
    Node getTarget();

    @OutVertex
    ActionTrigger getSource();
}
