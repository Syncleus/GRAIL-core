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

import com.syncleus.ferma.ReflectionCache;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

import java.util.*;

public class TinkerGrailGraphFactory implements GrailGraphFactory {
    private final Map<Object, GrailGraph> graphs = new HashMap<>();
    private final ReflectionCache reflections;

    public TinkerGrailGraphFactory() {
        this.reflections = new ReflectionCache(BUILT_IN_TYPES);
    }

    public TinkerGrailGraphFactory(final Collection<? extends Class<?>> annotatedTypes) {
        this.reflections = new ReflectionCache(combineCollections(annotatedTypes));
    }

    public TinkerGrailGraphFactory(final ReflectionCache reflections) {
        this.reflections = reflections;
    }

    @Override
    public GrailGraphFactory getParent() {
        return null;
    }

    @Override
    public <N> N getId() {
        return null;
    }

    @Override
    public GrailGraph subgraph(Object id) {
        GrailGraph graph = this.graphs.get(id);
        if( graph == null ) {
            graph = new GrailGraph(new MockTransactionalGraph(new TinkerGraph()), this.reflections, this, id);
            this.graphs.put(id, graph);
        }
        return graph;
    }

    private static final Collection<? extends Class<?>> combineCollections(final Collection<? extends Class<?>> annotatedTypes) {
        Set<Class<?>> combined = new HashSet<>(BUILT_IN_TYPES);
        combined.addAll(annotatedTypes);
        return combined;
    }
}
