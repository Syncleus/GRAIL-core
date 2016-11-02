package com.syncleus.grail.graph;

import com.syncleus.ferma.FramedGraph;
import org.apache.tinkerpop.gremlin.process.computer.*;

import java.util.Set;

public class SumTanhComputer<V> implements VertexFrameProgram<V, Double> {
    private static final int MAX_ITERATIONS = 1000;
    private final FramedGraph framedGraph;

    public SumTanhComputer(FramedGraph framedGraph) {
        this.framedGraph = framedGraph;
    }

    @Override
    public boolean terminate(Memory memory) {
        return memory.getIteration() > MAX_ITERATIONS;
    }

    @Override
    public FramedGraph getFramedGraph() {
        return this.framedGraph;
    }

    @Override
    public void execute(V vertex, Messenger<Double> messenger, Memory memory) {

    }

    @Override
    public void setup(Memory memory) {

    }

    @Override
    public Set<MessageScope> getMessageScopes(Memory memory) {
        return null;
    }

    @Override
    public GraphComputer.ResultGraph getPreferredResultGraph() {
        return null;
    }

    @Override
    public GraphComputer.Persist getPreferredPersist() {
        return null;
    }

    @Override
    public VertexProgram<Double> clone() {
        try {
            final SumTanhComputer clone = (SumTanhComputer) super.clone();
            return clone;
        } catch (final CloneNotSupportedException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
