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

import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.javahandler.*;
import com.tinkerpop.frames.modules.typedgraph.*;

/**
 * A graph edge which multiplies the signal from the source node by the edges weight. It then stores the result in the
 * signal for this edge.
 *
 * @since 0.1
 */
@TypeField("type")
@TypeValue("Synapse")
@JavaHandlerClass(AbstractSignalMultiplyingEdge.class)
public interface SignalMultiplyingEdge extends Weighted, Signaler, EdgeFrame {
    /**
     * The node connected to the source end of this edge.
     *
     * @return the source node.
     * @since 0.1
     */
    @OutVertex
    Signaler getSource();

    /**
     * This method will propagate the signal from the source node into this edges signal. It will multiply the source
     * node's signal by this edges weight and set that value as this edge's new signal.
     *
     * @since 0.1
     */
    @JavaHandler
    void propagate();
}
