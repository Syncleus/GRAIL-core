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

import com.syncleus.ferma.annotations.Adjacency;
import com.syncleus.ferma.annotations.Incidence;
import com.syncleus.ferma.annotations.Property;
import com.tinkerpop.blueprints.*;

public interface God {
    @Property("name")
    String getName();

    @Property("age")
    Integer getAge();

    @Property("type")
    String getType();

    @Adjacency(label="father")
    God getFather();

    @Adjacency(label="father", direction = Direction.IN)
    God getSon();

    @Adjacency(label="father", direction= Direction.IN)
    Iterable<? extends God> getSons();

    @Adjacency(label="father", direction= Direction.IN)
    <N extends God> Iterable<? extends N> getSons(Class<? extends N> type);

    @Adjacency(label="father", direction= Direction.IN)
    <N extends God> N getSon(Class<? extends N> type);

    @Adjacency(label="father", direction=Direction.IN)
    <N extends God> N addSon(Class<? extends N> type);

    @Incidence(label="father", direction= Direction.IN)
    <N extends FatherEdge> Iterable<? extends N> getSonEdges(Class<? extends N> type);

    @Incidence(label="father", direction= Direction.IN)
    <N extends FatherEdge> N getSonEdge(Class<? extends N> type);

    @Incidence(label="father", direction=Direction.IN)
    <N extends FatherEdge> N addSonEdge(Class<? extends N> type);

    @Adjacency(label="lives")
    Location getHome();
}
