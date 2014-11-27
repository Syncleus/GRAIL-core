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

import com.syncleus.grail.graph.action.ActionTriggerEdge;
import com.syncleus.grail.graph.action.PrioritySerialTrigger;
import com.syncleus.grail.graph.action.PrioritySerialTriggerEdge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface GrailGraphFactory {
    public static final Set<Class<?>> BUILT_IN_TYPES = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{
            SignalMultiplyingEdge.class,
            PrioritySerialTrigger.class,
            ActionTriggerEdge.class,
            PrioritySerialTriggerEdge.class}));

    GrailGraphFactory getParent();
    <N> N getId();
    GrailGraph subgraph(Object id);
}
