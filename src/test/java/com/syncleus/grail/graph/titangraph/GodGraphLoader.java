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
package com.syncleus.grail.graph.titangraph;

import com.thinkaurelius.titan.core.attribute.Geoshape;
import org.apache.tinkerpop.gremlin.structure.*;
import org.apache.tinkerpop.gremlin.structure.util.ElementHelper;


/**
 * Example Graph factory that creates a graph based on roman mythology.
 */
public class GodGraphLoader {

    public static void load(final Graph graph) {

        // vertices

        Vertex saturn = graph.addVertex();
        saturn.property("name", "saturn");
        saturn.property("age", 10000);
        saturn.property("type", "titan");
        saturn.property("classType", "God");

        Vertex sky = graph.addVertex();
        ElementHelper.attachProperties(sky, "name", "sky", "type", "location", "other", "more useless info");

        Vertex sea = graph.addVertex();
        ElementHelper.attachProperties(sea, "name", "sea", "type", "location");

        Vertex jupiter = graph.addVertex();
        ElementHelper.attachProperties(jupiter, "name", "jupiter", "age", 5000, "type", "god", "classType", "God");

        Vertex neptune = graph.addVertex();
        ElementHelper.attachProperties(neptune, "name", "neptune", "age", 4500, "type", "god", "classType", "God");

        Vertex hercules = graph.addVertex();
        ElementHelper.attachProperties(hercules, "name", "hercules", "age", 30, "type", "demigod", "classType", "GodExtended");

        Vertex alcmene = graph.addVertex();
        ElementHelper.attachProperties(alcmene, "name", "alcmene", "age", 45, "type", "human", "classType", "God");

        Vertex pluto = graph.addVertex();
        ElementHelper.attachProperties(pluto, "name", "pluto", "age", 4000, "type", "god", "classType", "God");

        Vertex nemean = graph.addVertex();
        ElementHelper.attachProperties(nemean, "name", "nemean", "type", "monster", "classType", "God");

        Vertex hydra = graph.addVertex();
        ElementHelper.attachProperties(hydra, "name", "hydra", "type", "monster", "classType", "God");

        Vertex cerberus = graph.addVertex();
        ElementHelper.attachProperties(cerberus, "name", "cerberus", "type", "monster", "classType", "God");

        Vertex tartarus = graph.addVertex();
        ElementHelper.attachProperties(tartarus, "name", "tartarus", "type", "location", "classType", "God");

        // edges

        ElementHelper.attachProperties(jupiter.addEdge("father", saturn), "classType", "Father");
        jupiter.addEdge("lives", sky).property("reason", "loves fresh breezes");
        jupiter.addEdge("brother", neptune);
        jupiter.addEdge("brother", pluto);

        ElementHelper.attachProperties(neptune.addEdge("father", saturn), "classType", "Father");
        neptune.addEdge("lives", sea).property("reason", "loves waves");
        neptune.addEdge("brother", jupiter);
        neptune.addEdge("brother", pluto);

        ElementHelper.attachProperties(hercules.addEdge("father", jupiter), "classType", "FatherEdgeExtended");
        hercules.addEdge("lives", sky).property("reason", "loves heights");
        ElementHelper.attachProperties(hercules.addEdge("battled", nemean), "time", 1, "place", Geoshape.point(38.1f, 23.7f));
        ElementHelper.attachProperties(hercules.addEdge("battled", hydra), "time", 2, "place", Geoshape.point(37.7f, 23.9f));
        ElementHelper.attachProperties(hercules.addEdge("battled", cerberus), "time", 12, "place", Geoshape.point(39f, 22f));

        ElementHelper.attachProperties(pluto.addEdge("father", saturn), "classType", "Father");
        pluto.addEdge("brother", jupiter);
        pluto.addEdge("brother", neptune);
        pluto.addEdge("lives", tartarus).property("reason", "no fear of death");
        pluto.addEdge("pet", cerberus);

        cerberus.addEdge("lives", tartarus);
        ElementHelper.attachProperties(cerberus.addEdge("battled", alcmene), "time", 5, "place", Geoshape.point(68.1f, 13.3f));
    }
}