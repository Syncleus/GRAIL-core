package com.syncleus.grail.graph;

import com.tinkerpop.blueprints.Direction;

import java.lang.annotation.*;

/**
 * Adjacencies annotate getters and adders to represent a Vertex adjacent to a Vertex.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TypedAdjacency {
    /**
     * The label of the edges making the adjacency between the vertices.
     *
     * @return the edge label
     */
    String label();

    /**
     * The edge direction of the adjacency.
     *
     * @return the direction of the edges composing the adjacency
     */
    Direction direction() default Direction.OUT;
}
