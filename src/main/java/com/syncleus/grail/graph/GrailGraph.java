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

import com.syncleus.ferma.DelegatingFramedTransactionalGraph;
import com.syncleus.ferma.ReflectionCache;
import com.tinkerpop.blueprints.TransactionalGraph;

/**
 * The GrailGraph represents a TinkerPop graph compatible with the GRAIL library typing. Using this Graph instead of the
 * built-in TinkerPop Graph ensures that the proper class typing is instantiated and annotations are enabled.
 *
 * @since 0.1
 */
public class GrailGraph extends DelegatingFramedTransactionalGraph implements GrailGraphFactory {


    private final Object id;
    private final GrailGraphFactory parentGraphFactory;

    public GrailGraph(final TransactionalGraph delegate, final GrailGraphFactory parentGraphFactory, final Object id) {
        super(delegate, true, BUILT_IN_TYPES);
        this.id = id;
        this.parentGraphFactory = parentGraphFactory;
    }

    public GrailGraph(TransactionalGraph delegate, ReflectionCache reflections, GrailGraphFactory parentGraphFactory, Object id) {
        super(delegate, reflections, true, true);
        this.id = id;
        this.parentGraphFactory = parentGraphFactory;
    }


    @Override
    public GrailGraphFactory getParent() {
        return this.parentGraphFactory;
    }

    @Override
    public <N> N getId() {
        return (N) this.id;
    }

    @Override
    public GrailGraph subgraph(Object id) {
        if( id == null )
            throw new IllegalArgumentException("id can not be null");

        return this.parentGraphFactory.subgraph(this.id.toString() + "." + id.toString());
    }
}
