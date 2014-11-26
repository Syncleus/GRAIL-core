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

import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.TypeResolver;
import com.syncleus.ferma.annotations.AnnotationFrameFactory;
import com.syncleus.grail.graph.action.*;
import com.tinkerpop.blueprints.Graph;

import java.util.*;

/**
 * The GrailGraph represents a TinkerPop graph compatible with the GRAIL library typing. Using this Graph instead of the
 * built-in TinkerPop Graph ensures that the proper class typing is instantiated and annotations are enabled.
 *
 * @since 0.1
 */
public class GrailFramedGraph extends FramedGraph {
    private static final Set<Class<?>> BUILT_IN_TYPES = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{
                                                                          SignalMultiplyingEdge.class,
                                                                          PrioritySerialTrigger.class,
                                                                          ActionTriggerEdge.class,
                                                                          PrioritySerialTriggerEdge.class}));
    public GrailFramedGraph(Graph delegate) {
        super(delegate, BUILT_IN_TYPES);
    }

    public GrailFramedGraph(Graph delegate, Collection<? extends Class<?>> annotatedTypes) {
        super(delegate, combineCollections(annotatedTypes));
    }

    private static final Collection<? extends Class<?>> combineCollections(final Collection<? extends Class<?>> annotatedTypes) {
        Set<Class<?>> combined = new HashSet<>(BUILT_IN_TYPES);
        combined.addAll(annotatedTypes);
        return combined;
    }
}
