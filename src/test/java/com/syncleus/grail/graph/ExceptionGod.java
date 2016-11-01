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
import org.apache.tinkerpop.gremlin.structure.Direction;

public interface ExceptionGod {
    @Property("name")
    String getName();

    @Adjacency(label="")
    <N extends ExceptionGod> Iterable<? extends N> getNoLabel(Class<? extends N> type);

    @Adjacency(label="Father", direction = Direction.IN)
    <N extends ExceptionGod> Iterable<? extends N> getSons();

    @Adjacency(label="Father", direction = Direction.IN)
    <N extends ExceptionGod> Iterable<? extends N> getSons(Class<? extends N> type, String badStuff);

    @Adjacency(label="Father", direction = Direction.IN)
    <N extends ExceptionGod> Iterable<? extends N> getSons(String badStuff);

    @Adjacency(label="father", direction=Direction.IN)
    <N extends God> N addSon();

    @Adjacency(label="father", direction=Direction.IN)
    <N extends God> N addSon(String badArg);

    @Adjacency(label="father", direction=Direction.IN)
    <N extends God> N addSon(String badArg, String worseArg);

    @Adjacency(label="Father", direction = Direction.IN)
    <N extends ExceptionGod> Iterable<? extends N> badSons(Class<? extends N> type);

    @Incidence(label="")
    <N extends FatherEdge> Iterable<? extends N> getNoLabelEdges(Class<? extends N> type);

    @Incidence(label="Father", direction = Direction.IN)
    <N extends FatherEdge> Iterable<? extends N> getSonEdges(Class<? extends N> type);

    @Incidence(label="Father", direction = Direction.IN)
    <N extends FatherEdge> Iterable<? extends N> badSonEdges(Class<? extends N> type);
}
