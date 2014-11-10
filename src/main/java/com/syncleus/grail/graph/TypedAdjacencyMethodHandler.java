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

import java.lang.reflect.Method;
import java.util.*;

public class TypedAdjacencyMethodHandler implements MethodHandler<TypedAdjacency> {

    private final Map<String, Set<String>> hierarchy;

    public TypedAdjacencyMethodHandler(final Map<String, Set<String>> hierarchy) {
        this.hierarchy = hierarchy;
    }

    @Override
    public Class<TypedAdjacency> getAnnotationType() {
        return TypedAdjacency.class;
    }

    @Override
    public Object processElement(final Object frame, final Method method, final Object[] arguments, final TypedAdjacency annotation, final FramedGraph<?> framedGraph, final Element element) {

        if( ! (element instanceof Vertex) )
            throw new IllegalStateException("element is not a type of Vertex " + element.getClass().getName());
        final Vertex vertex = (Vertex) element;

        assert annotation.label() != null;

        if( ClassUtilities.isAddMethod(method) ) {
            if( arguments == null )
                throw new IllegalStateException("method " + method.getName() + " was annotated with @TypedAdjacency but had no arguments.");
            else if( arguments.length == 1 ) {
                if( !(arguments[0] instanceof Class) )
                    throw new IllegalStateException("method " + method.getName() + " was annotated with @TypedAdjacency, had a single argument, but that argument was not of the type Class");

                final Class type = (Class) arguments[0];

                TypedAdjacencyMethodHandler.checkReturnType(method, type);

                return TypedAdjacencyMethodHandler.addNode(type, annotation.direction(), annotation.label(), framedGraph, vertex);
            }
            else
                throw new IllegalStateException("method " + method.getName() + " was annotated with @TypedAdjacency but had more than 1 arguments.");
        }
        else if( ClassUtilities.isGetMethod(method) ) {
            if( arguments == null )
                throw new IllegalStateException("method " + method.getName() + " was annotated with @TypedAdjacency but had no arguments.");
            else if( arguments.length == 1 ) {
                if( !(arguments[0] instanceof Class) )
                    throw new IllegalStateException("method " + method.getName() + " was annotated with @TypedAdjacency, had a single argument, but that argument was not of the type Class");

                final Class type = (Class) arguments[0];

                if( method.getReturnType().isAssignableFrom(Iterable.class))
                    return this.getNodes(type, annotation.direction(), annotation.label(), framedGraph, vertex);

                TypedAdjacencyMethodHandler.checkReturnType(method, type);
                return this.getNode(type, annotation.direction(), annotation.label(), framedGraph, vertex);
            }
            else
                throw new IllegalStateException("method " + method.getName() + " was annotated with @TypedAdjacency but had more than 1 arguments.");
        }
        else
            throw new IllegalStateException("method " + method.getName() + " was annotated with @TypedAdjacency but did not begin with either of the following keywords: add, get");
    }

    private Iterable getNodes(final Class type, final Direction direction, final String label, final FramedGraph<?> framedGraph, final Vertex vertex) {
        final TypeValue typeValue = ReflectionUtility.determineTypeValue(type);
        final TypeField typeField = ReflectionUtility.determineTypeField(type);
        final Set<String> allAllowedValues = this.hierarchy.get(typeValue.value());
        switch(direction) {
        case BOTH:
            return framedGraph.frameVertices((Iterable<Vertex>) new GremlinPipeline<Vertex, Vertex>(vertex).both(label).has(typeField.value(), Tokens.T.in, allAllowedValues), type);
        case IN:
            return framedGraph.frameVertices((Iterable<Vertex>) new GremlinPipeline<Vertex, Vertex>(vertex).in(label).has(typeField.value(), Tokens.T.in, allAllowedValues), type);
        //Assume out direction
        default:
            return framedGraph.frameVertices((Iterable<Vertex>) new GremlinPipeline<Vertex, Vertex>(vertex).out(label).has(typeField.value(), Tokens.T.in, allAllowedValues), type);
        }

    }

    private Object getNode(final Class type, final Direction direction, final String label, final FramedGraph<?> framedGraph, final Vertex vertex) {
        final TypeValue typeValue = ReflectionUtility.determineTypeValue(type);
        final TypeField typeField = ReflectionUtility.determineTypeField(type);
        final Set<String> allAllowedValues = this.hierarchy.get(typeValue.value());
        switch(direction) {
            case BOTH:
                return framedGraph.frame((Vertex) new GremlinPipeline<Vertex, Vertex>(vertex).both(label).has(typeField.value(), Tokens.T.in, allAllowedValues).next(), type);
            case IN:
                return framedGraph.frame((Vertex) new GremlinPipeline<Vertex, Vertex>(vertex).in(label).has(typeField.value(), Tokens.T.in, allAllowedValues).next(), type);
            //Assume out direction
            default:
                return framedGraph.frame((Vertex) new GremlinPipeline<Vertex, Vertex>(vertex).out(label).has(typeField.value(), Tokens.T.in, allAllowedValues).next(), type);
        }
    }

    private static Object addNode(final Class type, final Direction direction, final String label, final FramedGraph<?> framedGraph, final Vertex vertex) {
        ReflectionUtility.determineTypeValue(type);
        ReflectionUtility.determineTypeField(type);

        final Vertex newVertex = framedGraph.addVertex(null);
        final Object newNode = framedGraph.frame(newVertex, type);
        assert type.isInstance(newNode);

        switch(direction) {
            case BOTH:
                framedGraph.addEdge(null, vertex, newVertex, label);
                framedGraph.addEdge(null, newVertex, vertex, label);
                break;
            case IN:
                framedGraph.addEdge(null, newVertex, vertex, label);
                break;
            //Assume out direction
            default:
                framedGraph.addEdge(null, vertex, newVertex, label);
        }

        return newNode;
    }

    private static void checkReturnType(final Method method, final Class type) {
        if( ! method.getReturnType().isAssignableFrom(type) )
            throw new IllegalArgumentException("The type is not a subtype of the return type.");
    }
}
