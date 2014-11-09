package com.syncleus.grail.graph;

import com.tinkerpop.blueprints.*;
import com.tinkerpop.frames.*;
import com.tinkerpop.frames.annotations.gremlin.GremlinGroovy;
import com.tinkerpop.frames.modules.javahandler.*;
import com.tinkerpop.frames.modules.typedgraph.*;

@TypeField("classType")
@TypeValue("God")
public interface God {
    @Property("name")
    String getName();

    @Property("age")
    Integer getAge();

    @Property("type")
    String getType();

    @Adjacency(label="father")
    God getFather();

    @GremlinGroovy("it.in('father')")
    God getSon();

    @Adjacency(label="father", direction= Direction.IN)
    Iterable<? extends God> getSons();

    @TypedAdjacency(label="father", direction= Direction.IN)
    <N extends God> Iterable<? extends N> getSons(Class<? extends N> type);

    @TypedAdjacency(label="father", direction= Direction.IN)
    <N extends God> N getSon(Class<? extends N> type);

    @TypedAdjacency(label="father", direction=Direction.IN)
    <N extends God> N addSon(Class<? extends N> type);

    @TypedIncidence(label="father", direction= Direction.IN)
    <N extends FatherEdge> Iterable<? extends N> getSonEdges(Class<? extends N> type);

    @TypedIncidence(label="father", direction= Direction.IN)
    <N extends FatherEdge> N getSonEdge(Class<? extends N> type);

    @TypedIncidence(label="father", direction=Direction.IN)
    <N extends FatherEdge> N addSonEdge(Class<? extends N> type);

    @Adjacency(label="lives")
    Location getHome();

    @JavaHandler
    boolean isAgeEven();

    public abstract class Impl implements JavaHandlerContext<Vertex>, God {

        public boolean isAgeEven() {
            return this.getAge() % 2 == 0;
        }
    }
}
