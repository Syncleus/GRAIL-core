package com.syncleus.grail.graph;

import com.tinkerpop.frames.EdgeFrame;
import com.tinkerpop.frames.modules.javahandler.*;

import java.util.Random;

public abstract class AbstractSignalMultiplyingEdge implements SignalMultiplyingEdge {
    private static final Random RANDOM = new Random();

    @Initializer
    public void init() {
        this.setWeight(((RANDOM.nextDouble() * 2.0) - 1.0) / 10.0);
    }

    @Override
    public void propagate() {
        this.setSignal(this.getSource().getSignal() * this.getWeight());
    }
}
