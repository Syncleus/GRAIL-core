package com.syncleus.grail.graph;

import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.VertexFrame;
import org.apache.tinkerpop.gremlin.process.computer.Memory;
import org.apache.tinkerpop.gremlin.process.computer.Messenger;
import org.apache.tinkerpop.gremlin.process.computer.VertexProgram;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public interface VertexFrameProgram<V, M> extends VertexProgram<M> {
    @Override
    default void execute(Vertex vertex, Messenger<M> messenger, Memory memory) {
        final VertexFrame framed = this.getFramedGraph().frameElement(vertex, VertexFrame.class);
        this.execute((V) framed, messenger, memory);
    }

    FramedGraph getFramedGraph();

    void execute(V vertex, Messenger<M> messenger, Memory memory);
}
