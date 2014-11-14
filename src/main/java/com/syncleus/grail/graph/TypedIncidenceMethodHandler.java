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

import com.tinkerpop.blueprints.*;
import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.MethodHandler;
import com.tinkerpop.frames.modules.typedgraph.*;
import com.tinkerpop.gremlin.Tokens;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import java.lang.reflect.*;
import java.util.*;

/**
 * A TinkerPop method handler that implemented the TypedIncidence Annotation.
 *
 * @since 0.1
 */
public class TypedIncidenceMethodHandler implements MethodHandler<TypedIncidence> {

    private final Map<String, Set<String>> hierarchy;

    /**
     * Creates a new method handler with the specified hierarchy map. The hierarchy map has as a key all the TypeValue's
     * value property from all the classes known to the typing engine. The value associated with each key is a set of
     * TypeValue value parameters for all the children classes which also have a TypeValue. For convenience purposes the
     * key will always be present as a single element in the set. This is because a class is always a type of itself.
     *
     * @param hierarchy the TypeValue hierarchy of all classes known to the typing engine.
     * @since 0.1
     */
    public TypedIncidenceMethodHandler(final Map<String, Set<String>> hierarchy) {
        this.hierarchy = hierarchy;
    }

    @Override
    public Class<TypedIncidence> getAnnotationType() {
        return TypedIncidence.class;
    }

    @Override
    public Object processElement(final Object frame, final Method method, final Object[] arguments, final TypedIncidence annotation, final FramedGraph<?> framedGraph, final Element element) {

        if( ! (element instanceof Vertex) )
            throw new IllegalStateException("element is not a type of Vertex " + element.getClass().getName());

        assert annotation.label() != null;

        if( ClassUtilities.isGetMethod(method) ) {
            if( arguments == null )
                throw new IllegalStateException(method.getName() + " was annotated with @TypedIncidence but had no arguments.");
            else if( arguments.length == 1 ) {
                if( !(arguments[0] instanceof Class) )
                    throw new IllegalStateException(method.getName() + " was annotated with @TypedIncidence, had a single argument, but that argument was not of the type Class");

                final Class type = (Class) arguments[0];

                final Vertex vertex = (Vertex) element;

                if( method.getReturnType().isAssignableFrom(Iterable.class))
                    return this.getEdges(type, annotation.direction(), annotation.label(), framedGraph, vertex);

                TypedIncidenceMethodHandler.checkReturnType(method, type);
                return this.getEdge(type, annotation.direction(), annotation.label(), framedGraph, vertex);
            }
            else
                throw new IllegalStateException(method.getName() + " was annotated with @TypedIncidence but had more than 1 arguments.");
        }
        else
            throw new IllegalStateException(method.getName() + " was annotated with @TypedIncidence but did not begin with either of the following keywords: add, get");
    }

    private Iterable getEdges(final Class type, final Direction direction, final String label, final FramedGraph<?> framedGraph, final Vertex vertex) {
        final TypeValue typeValue = ReflectionUtility.determineTypeValue(type);
        final TypeField typeField = ReflectionUtility.determineTypeField(type);
        final Set<String> allAllowedValues = this.hierarchy.get(typeValue.value());
        switch(direction) {
        case BOTH:
            return framedGraph.frameEdges((Iterable<Edge>) new GremlinPipeline<Vertex, Vertex>(vertex).bothE(label).has(typeField.value(), Tokens.T.in, allAllowedValues), type);
        case IN:
            return framedGraph.frameEdges((Iterable<Edge>) new GremlinPipeline<Vertex, Vertex>(vertex).inE(label).has(typeField.value(), Tokens.T.in, allAllowedValues), type);
        //Assume out direction
        default:
            return framedGraph.frameEdges((Iterable<Edge>) new GremlinPipeline<Vertex, Vertex>(vertex).outE(label).has(typeField.value(), Tokens.T.in, allAllowedValues), type);
        }

    }

    private Object getEdge(final Class type, final Direction direction, final String label, final FramedGraph<?> framedGraph, final Vertex vertex) {
        final TypeValue typeValue = ReflectionUtility.determineTypeValue(type);
        final TypeField typeField = ReflectionUtility.determineTypeField(type);
        final Set<String> allAllowedValues = this.hierarchy.get(typeValue.value());
        switch(direction) {
        case BOTH:
            return framedGraph.frame((Edge) new GremlinPipeline<Vertex, Vertex>(vertex).bothE(label).has(typeField.value(), Tokens.T.in, allAllowedValues).next(), type);
        case IN:
            return framedGraph.frame((Edge) new GremlinPipeline<Vertex, Vertex>(vertex).inE(label).has(typeField.value(), Tokens.T.in, allAllowedValues).next(), type);
        //Assume out direction
        default:
            return framedGraph.frame((Edge) new GremlinPipeline<Vertex, Vertex>(vertex).outE(label).has(typeField.value(), Tokens.T.in, allAllowedValues).next(), type);
        }
    }

    private static void checkReturnType(final Method method, final Class type) {
        if( ! method.getReturnType().isAssignableFrom(type) )
            throw new IllegalArgumentException("The type is not a subtype of the return type.");
    }
}
