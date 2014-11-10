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

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.frames.FramedGraphConfiguration;
import com.tinkerpop.frames.modules.Module;
import com.tinkerpop.frames.modules.typedgraph.TypeValue;

import java.util.*;

public class GrailModule implements Module {
    private final Map<String, Set<String>> hierarchy;

    public GrailModule(final Collection<? extends Class<?>> types) {
        this.hierarchy = GrailModule.constructTypedHierarchy(types);
    }

    @Override
    public Graph configure(final Graph baseGraph, final FramedGraphConfiguration config) {
        config.addMethodHandler(new TypedAdjacencyMethodHandler(this.hierarchy));
        config.addMethodHandler(new TypedIncidenceMethodHandler(this.hierarchy));
        return baseGraph;
    }

    private static Map<String, Set<String>> constructTypedHierarchy(final Collection<? extends Class<?>> types) {
        final Map<String, Set<String>> typedHierarchy = new HashMap<>(types.size());
        for(final Class<?> type : types ) {
            final TypeValue typeValue = GrailModule.determineTypeValue(type);
            if( typeValue == null )
                throw new IllegalArgumentException("One of the types passed into the GrailGraphFactory was not annotated with a @TypeValue");
            if( typeValue.value() == null )
                throw new IllegalArgumentException("One of the types has a @TypeValue annotation but that annotation doesnt have a value");

            if( typedHierarchy.containsKey(typeValue.value()) )
                continue;
            else
                typedHierarchy.put(typeValue.value(), new HashSet<String>(Collections.singleton(typeValue.value())));

            GrailModule.constructTypedHierarchyRecursive(typedHierarchy, type.getInterfaces(), new HashSet<String>(Collections.singleton(typeValue.value())));
        }

        return typedHierarchy;
    }

    private static void constructTypedHierarchyRecursive(final Map<String, Set<String>> hierarchy, final Class<?>[] parents, final Set<String> childrenSeen) {
        for( final Class<?> parent : parents ) {
            final TypeValue typeValue = GrailModule.determineTypeValue(parent);

            //this parent has a type value, so add all the children to it
            if( typeValue != null ) {
                if( typeValue.value() == null )
                    throw new IllegalArgumentException("One of the types has a @TypeValue annotation but that annotation doesnt have a value");
                Set<String> parentsChildren = hierarchy.get(typeValue.value());
                if( parentsChildren == null ) {
                    parentsChildren = new HashSet<String>(Collections.singleton(typeValue.value()));
                    hierarchy.put(typeValue.value(), parentsChildren);
                }
                parentsChildren.addAll(childrenSeen);
                final Set<String> newChildrenSeen = new HashSet<String>(childrenSeen);
                GrailModule.constructTypedHierarchyRecursive(hierarchy, parent.getInterfaces(), newChildrenSeen);
            }
            else
                GrailModule.constructTypedHierarchyRecursive(hierarchy, parent.getInterfaces(), childrenSeen);
        }
    }

    private static TypeValue determineTypeValue(final Class<?> type) {
        return type.getDeclaredAnnotation(TypeValue.class);
    }
}
