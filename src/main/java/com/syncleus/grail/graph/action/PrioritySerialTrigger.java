package com.syncleus.grail.graph.action;

import com.tinkerpop.frames.annotations.gremlin.GremlinGroovy;
import com.tinkerpop.frames.modules.javahandler.*;
import com.tinkerpop.frames.modules.typedgraph.TypeValue;

@TypeValue("PrioritySerialTrigger")
@JavaHandlerClass(AbstractPrioritySerialTrigger.class)
public interface PrioritySerialTrigger extends ActionTrigger {
    @JavaHandler
    @Action("actionTrigger")
    @Override
    void trigger();

    //@GremlinGroovy(value="it.outE('triggers').order{it.a.triggerPriority<=>it.b.triggerPriority}._()")
    @JavaHandler
    Iterable<? extends PrioritySerialTriggerEdge> getPrioritizedTriggerEdges();
}
