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
package com.syncleus.grail.graph.unit;

import com.syncleus.grail.graph.GrailGraph;
import com.syncleus.grail.graph.TinkerGrailGraphFactory;
import com.syncleus.grail.graph.unit.SignalMultiplyingEdge;
import com.syncleus.grail.graph.unit.SignalerVertex;
import junit.framework.Assert;
import org.junit.Test;

public class SignalMultiplyingEdgeTest {
    private static final double SOURCE_SIGNAL = 5.0;
    private static final double MULTIPLIER = 7.0;
    private static final double RESULT = 35.0;

    @Test
    public void testDoesMultiply() {
        final GrailGraph graph = new TinkerGrailGraphFactory().subgraph("0");

        // construct graph
        final SignalerVertex source = graph.addFramedVertex(SignalerVertex.class);
        final SignalerVertex target = graph.addFramedVertex(SignalerVertex.class);
        final SignalMultiplyingEdge multiplyingEdge = graph.addFramedEdge(source, target, "foo", SignalMultiplyingEdge.class);

        //set some inital values
        source.setSignal(SOURCE_SIGNAL);
        multiplyingEdge.setWeight(MULTIPLIER);

        //process the weight
        multiplyingEdge.propagate();

        //check to make sure the edge now has the correct signal
        Assert.assertTrue(checkResult(RESULT, multiplyingEdge.getSignal()));
    }

    private static boolean checkResult(final double firstValue, final double secondValue) {
        return (Math.abs(firstValue - secondValue) < 0.0000001);
    }
}
