package com.syncleus.grail.graph;

import com.tinkerpop.blueprints.*;
import com.tinkerpop.frames.*;
import com.tinkerpop.frames.modules.MethodHandler;
import com.tinkerpop.frames.modules.typedgraph.*;
import com.tinkerpop.gremlin.java.GremlinPipeline;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class TypedIncidenceMethodHandler implements MethodHandler<TypedIncidence> {

    private final Map<String, Set<String>> hierarchy;

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
        final Vertex vertex = (Vertex) element;

        if(annotation.label() == null)
            throw new IllegalStateException("method " + method.getName() + " label must be specified on @TypedIncidence annotation");

        if( ClassUtilities.isGetMethod(method) ) {
            if( arguments == null )
                throw new IllegalStateException("method " + method.getName() + " was annotated with @TypedIncidence but had no arguments.");
            else if( arguments.length == 1 ) {
                if( !(arguments[0] instanceof Class) )
                    throw new IllegalStateException("method " + method.getName() + " was annotated with @TypedIncidence, had a single argument, but that argument was not of the type Class");

                final Class type = (Class) arguments[0];

                if( method.getReturnType().isAssignableFrom(Iterable.class))
                    return TypedIncidenceMethodHandler.getEdges(type, annotation.direction(), annotation.label(), framedGraph, vertex);

                TypedIncidenceMethodHandler.checkReturnType(method, type);
                return TypedIncidenceMethodHandler.getEdge(type, annotation.direction(), annotation.label(), framedGraph, vertex);
            }
            else
                throw new IllegalStateException("method " + method.getName() + " was annotated with @TypedIncidence but had more than 1 arguments.");
        }
        else
            throw new IllegalStateException("method " + method.getName() + " was annotated with @TypedIncidence but did not begin with either of the following keywords: add, get");
    }

    private static Iterable getEdges(final Class type, final Direction direction, final String label, final FramedGraph<?> framedGraph, final Vertex vertex) {
        final TypeValue typeValue = TypedIncidenceMethodHandler.determineTypeValue(type);
        final TypeField typeField = TypedIncidenceMethodHandler.determineTypeField(type);
        switch(direction) {
        case BOTH:
            return framedGraph.frameEdges((Iterable<com.tinkerpop.blueprints.Edge>) new GremlinPipeline<Vertex, Vertex>(vertex).bothE(label).has(typeField.value(), typeValue.value()), type);
        case IN:
            return framedGraph.frameEdges((Iterable<com.tinkerpop.blueprints.Edge>) new GremlinPipeline<Vertex, Vertex>(vertex).inE(label).has(typeField.value(), typeValue.value()), type);
        //Assume out direction
        default:
            return framedGraph.frameEdges((Iterable<com.tinkerpop.blueprints.Edge>) new GremlinPipeline<Vertex, Vertex>(vertex).outE(label).has(typeField.value(), typeValue.value()), type);
        }

    }

    private static Object getEdge(final Class type, final Direction direction, final String label, final FramedGraph<?> framedGraph, final Vertex vertex) {
        final TypeValue typeValue = TypedIncidenceMethodHandler.determineTypeValue(type);
        final TypeField typeField = TypedIncidenceMethodHandler.determineTypeField(type);
        switch(direction) {
        case BOTH:
            return framedGraph.frame((com.tinkerpop.blueprints.Edge) new GremlinPipeline<Vertex, Vertex>(vertex).bothE(label).has(typeField.value(), typeValue.value()).next(), type);
        case IN:
            return framedGraph.frame((com.tinkerpop.blueprints.Edge) new GremlinPipeline<Vertex, Vertex>(vertex).inE(label).has(typeField.value(), typeValue.value()).next(), type);
        //Assume out direction
        default:
            return framedGraph.frame((com.tinkerpop.blueprints.Edge) new GremlinPipeline<Vertex, Vertex>(vertex).outE(label).has(typeField.value(), typeValue.value()).next(), type);
        }
    }

    private static void checkReturnType(final Method method, final Class type) {
        if( ! method.getReturnType().isAssignableFrom(type) )
            throw new IllegalArgumentException("The type is not a subtype of the return type.");
    }

    private static TypeValue determineTypeValue(final Class<?> type) {
        final TypeValue typeValue = type.getDeclaredAnnotation(TypeValue.class);
        if( typeValue == null )
            throw new IllegalArgumentException("The specified type does not have a TypeValue annotation");
        return typeValue;
    }

    private static TypeField determineTypeField(final Class<?> type) {
        TypeField typeField = type.getAnnotation(TypeField.class);
        if( typeField == null ) {
            Class<?>[] parents = type.getInterfaces();
            for( final Class<?> parent : parents ) {
                typeField = TypedIncidenceMethodHandler.determineTypeFieldRecursive(parent);
                if( typeField != null )
                    return typeField;
            }
            if( typeField == null )
                throw new IllegalArgumentException("The specified type does not have a parent with a typeField annotation.");
        }

        return typeField;
    }

    private static TypeField determineTypeFieldRecursive(final Class<?> type) {
        TypeField typeField = type.getAnnotation(TypeField.class);
        if( typeField == null ) {
            Class<?>[] parents = type.getInterfaces();
            for( final Class<?> parent : parents ) {
                typeField = TypedIncidenceMethodHandler.determineTypeFieldRecursive(parent);
                if( typeField != null )
                    return typeField;
            }
            return null;
        }
        return typeField;
    }
}
