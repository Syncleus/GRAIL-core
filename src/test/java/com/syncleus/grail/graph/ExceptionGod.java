package com.syncleus.grail.graph;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.modules.typedgraph.*;

@TypeField("classType")
@TypeValue("God")
public interface ExceptionGod {
    @Property("name")
    String getName();

    @TypedAdjacency(label="")
    <N extends ExceptionGod> Iterable<? extends N> getNoLabel(Class<? extends N> type);

    @TypedAdjacency(label="Father", direction = Direction.IN)
    <N extends ExceptionGod> Iterable<? extends N> getSons();

    @TypedAdjacency(label="Father", direction = Direction.IN)
    <N extends ExceptionGod> Iterable<? extends N> getSons(Class<? extends N> type, String badStuff);

    @TypedAdjacency(label="Father", direction = Direction.IN)
    <N extends ExceptionGod> Iterable<? extends N> getSons(String badStuff);

    @TypedAdjacency(label="Father", direction = Direction.IN)
    <N extends ExceptionGod> Iterable<? extends N> badSons(Class<? extends N> type);

    @TypedIncidence(label="")
    <N extends FatherEdge> Iterable<? extends N> getNoLabelEdges(Class<? extends N> type);

    @TypedIncidence(label="Father", direction = Direction.IN)
    <N extends FatherEdge> Iterable<? extends N> getSonEdges(Class<? extends N> type);

    @TypedIncidence(label="Father", direction = Direction.IN)
    <N extends FatherEdge> Iterable<? extends N> badSonEdges(Class<? extends N> type);
}
