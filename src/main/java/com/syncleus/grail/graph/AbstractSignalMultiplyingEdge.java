/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/
package com.syncleus.grail.graph;

import com.syncleus.ferma.VertexFrame;
import java.util.Random;

/**
 * This is the Java handler class associated with the SignalMultiplyingEdge type. It ensures initial weights are set
 * to a random value as well as defines how to propagate the signal.
 * 
 * @since 0.1
 */
public abstract class AbstractSignalMultiplyingEdge extends AbstractGrailEdgeFrame implements SignalMultiplyingEdge {
    private static final Random RANDOM = new Random();
    private static final double RANGE = 2.0;
    private static final double OFFSET = -1.0;
    private static final double SCALE = 0.1;

    /**
     * Initializes newly constructed elements with default values.
     *
     * @since 0.1
     */
    @Override
    public void init() {
        this.setWeight(((AbstractSignalMultiplyingEdge.RANDOM.nextDouble() * AbstractSignalMultiplyingEdge.RANGE) + AbstractSignalMultiplyingEdge.OFFSET) * AbstractSignalMultiplyingEdge.SCALE);
    }

    @Override
    public void propagate() {
        this.setSignal(this.getSource().getSignal() * this.getWeight());
    }
}
