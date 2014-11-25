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
import com.tinkerpop.blueprints.Direction;

public interface SimpleSignalNode extends SignalNode {
    @Adjacency(label="parent", direction= Direction.IN)
    <N extends SimpleSignalNode> Iterable<? extends N> getChildren(Class<? extends N> type);

    @Adjacency(label="parent", direction= Direction.OUT)
    <N extends SimpleSignalNode> Iterable<? extends N> getParents(Class<? extends N> type);

    @Adjacency(label="parent", direction= Direction.BOTH)
    <N extends SimpleSignalNode> Iterable<? extends N> getFamily(Class<? extends N> type);

    @Adjacency(label="parent", direction= Direction.BOTH)
    <N extends SimpleSignalNode> N addInbreed(Class<? extends N> type);

    @Adjacency(label="parent", direction= Direction.IN)
    <N extends SimpleSignalNode> N getChild(Class<? extends N> type);

    @Adjacency(label="parent", direction= Direction.OUT)
    <N extends SimpleSignalNode> N getParent(Class<? extends N> type);

    @Adjacency(label="parent", direction= Direction.BOTH)
    <N extends SimpleSignalNode> N getFamilyMember(Class<? extends N> type);

    @Incidence(label="parent", direction= Direction.IN)
    <E extends SignalMultiplyingEdge> Iterable<? extends E> getChildEdges(Class<? extends E> type);

    @Incidence(label="parent", direction= Direction.OUT)
    <E extends SignalMultiplyingEdge> Iterable<? extends E> getParentEdges(Class<? extends E> type);

    @Incidence(label="parent", direction= Direction.BOTH)
    <E extends SignalMultiplyingEdge> Iterable<? extends E> getFamilyEdges(Class<? extends E> type);

    @Incidence(label="parent", direction= Direction.IN)
    <E extends SignalMultiplyingEdge> E getChildEdge(Class<? extends E> type);

    @Incidence(label="parent", direction= Direction.OUT)
    <E extends SignalMultiplyingEdge> E getParentEdge(Class<? extends E> type);

    @Incidence(label="parent", direction= Direction.BOTH)
    <E extends SignalMultiplyingEdge> E getFamilyMemberEdge(Class<? extends E> type);
}
