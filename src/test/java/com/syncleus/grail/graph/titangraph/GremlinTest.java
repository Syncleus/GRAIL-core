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

import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.groovy.Gremlin;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.*;
import com.tinkerpop.pipes.util.iterators.SingleIterator;
import org.junit.*;
import java.util.*;

public class GremlinTest {
    /**
     * This simple example shows how to find the name for all of the children of saturn.
     */
    @Test
    public void testGremlinFindChild() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final GremlinPipeline<Vertex,Vertex> pipe = new GremlinPipeline<Vertex,Vertex>();
        final Vertex saturnVertex = godGraph.getVertices("name", "saturn").iterator().next();
        final GremlinPipeline<Vertex,?> childsNamePipe = pipe.start(saturnVertex).in("father").property("name");
        final String childsName = childsNamePipe.next().toString();
        Assert.assertEquals(childsName, "jupiter");
    }

    /**
     * This simple gremlin examples shows how to retrieve the name for all the brothers of jupiter.
     */
    @Test
    public void testGremlinFindBrothers() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final Vertex jupiterVertex = godGraph.getVertices("name", "jupiter").iterator().next();
        final GremlinPipeline<Vertex,?> brotherNamesPipe = new GremlinPipeline<Vertex,Vertex>().start(jupiterVertex).out("brother").property("name");
        final List brotherNames = brotherNamesPipe.next(100);

        //we know jupiter only has two brothers
        Assert.assertEquals(brotherNames.size(), 2);

        //check each brothers name to make sure both of them matchone of the known brothers names
        Assert.assertTrue(("pluto".equals(brotherNames.get(0).toString())) || ("neptune".equals(brotherNames.get(0).toString())));
        Assert.assertTrue( ("pluto".equals(brotherNames.get(1).toString())) || ("neptune".equals(brotherNames.get(1).toString())) );
    }

    /**
     * This test finds the name of all brothers of jupiter begining with the letter p. Only pluto satisfies this condition.
     */
    @Test
    public void testGremlinFindBrothersFilterLetter() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final Vertex jupiterVertex = godGraph.getVertices("name", "jupiter").iterator().next();
        final GremlinPipeline brotherNamesPipe = new GremlinPipeline(jupiterVertex).out("brother").property("name").filter(new PipeFunction<String, Boolean>() {
            public Boolean compute(String argument) {
                return argument.startsWith("p");
            }
        });
        final List brotherNames = brotherNamesPipe.next(100);

        //we know jupiter only has two brothers
        Assert.assertEquals(brotherNames.size(), 1);

        //check each brothers name to make sure both of them matchone of the known brothers names
        Assert.assertEquals( "pluto", brotherNames.get(0).toString() );
    }

    /**
     * This test finds all the names of the brothers of jupiter and removes any occurances of the letter "o" from their
     * names.
     */
    @Test
    public void testGremlinFindBrothersRemoveLetter() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final Vertex jupiterVertex = godGraph.getVertices("name", "jupiter").iterator().next();
        final GremlinPipeline brotherNamesPipe = new GremlinPipeline(jupiterVertex).out("brother").property("name").transform(new PipeFunction<String, String>() {
            public String compute(String argument) {
                return argument.replaceAll("o", "");
            }
        });
        final List brotherNames = brotherNamesPipe.next(100);

        //we know jupiter only has two brothers
        Assert.assertEquals(brotherNames.size(), 2);

        //check each brothers name to make sure both of them matchone of the known brothers names
        Assert.assertTrue(("plut".equals(brotherNames.get(0).toString())) || ("neptune".equals(brotherNames.get(0).toString())));
        Assert.assertTrue(("plut".equals(brotherNames.get(1).toString())) || ("neptune".equals(brotherNames.get(1).toString())));
    }

    /**
     * This test finds all of jupiters brothers names, removes the letter o from their names, and then returns only the
     * names of the brothers which start with the letter p. This only matches ont he brother pluto returning the name
     * "plut"
     */
    @Test
    public void testGremlinFindBrothersRemoveAndFilterLetter() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final Vertex jupiterVertex = godGraph.getVertices("name", "jupiter").iterator().next();
        final GremlinPipeline brotherNamesPipe = new GremlinPipeline(jupiterVertex).out("brother").property("name").transform(new PipeFunction<String, String>() {
            public String compute(String argument) {
                return argument.replaceAll("o", "");
            }
        }).filter(new PipeFunction<String, Boolean>() {
            public Boolean compute(String argument) {
                return argument.startsWith("p");
            }
        });
        final List brotherNames = brotherNamesPipe.next(100);

        //we know jupiter only has two brothers
        Assert.assertEquals(brotherNames.size(), 1);

        //check each brothers name to make sure both of them matchone of the known brothers names
        Assert.assertEquals( "plut", brotherNames.get(0).toString() );
    }

    /**
     * This test retrieves the name of any children of saturn who meet the following condition: they themselves have
     * children who have battled nemean. Only jupiter should match this condition as he is the son of saturn and is also
     * the father of hercules, and hercules battled nemean.
     */
    @Test
    public void testGremlinBacktrack() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final Vertex saturnVertex = godGraph.getVertices("name", "saturn").iterator().next();
        final GremlinPipeline<Vertex,?> childNamePipe = new GremlinPipeline<Vertex,Vertex>(saturnVertex).in("father").as("mark_children").in("father").out("battled").has("name", "nemean").back("mark_children").property("name");
        final String childName = childNamePipe.next().toString();

        //we know jupiter only has two brothers
        Assert.assertEquals(childName, "jupiter");
    }

    /**
     * Traverses all of the children of the children of saturn in a depth-first manner, returning the names. In this
     * case only hercules should math
     */
    @Test
    public void testGremlinDepthFirst() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final Vertex saturnVertex = godGraph.getVertices("name", "saturn").iterator().next();
        final GremlinPipeline<Vertex,?> pipe = new GremlinPipeline<Vertex,Vertex>(saturnVertex).inE("father").outV().inE("father").outV().property("name");
        final String grandchildName = pipe.next().toString();

        //we know jupiter only has two brothers
        Assert.assertEquals(grandchildName, "hercules");
    }

    /**
     * Traverses all of the children of the children of saturn in a depth-first manner, returning the names.
     */
    @Test
    public void testGremlinBreadthFirst() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final Vertex saturnVertex = godGraph.getVertices("name", "saturn").iterator().next();
        final GremlinPipeline<Vertex,?> pipe = new GremlinPipeline<Vertex,Vertex>(saturnVertex).inE("father").gather().scatter().outV().gather().scatter().inE("father").gather().scatter().outV().property("name");
        final String grandchildName = pipe.next().toString();

        //we know jupiter only has two brothers
        Assert.assertEquals(grandchildName, "hercules");
    }

    /**
     * This test demonstrates how to use compiled gremlin scripts in java. It specifically finds all the brothers for
     * jupiter.
     */
    @Test
    public void testGremlinCompiled() {
        final TitanGraph godGraph = TitanGods.create("./target/TitanTestDB");
        final Vertex jupiterVertex = godGraph.getVertices("name", "jupiter").iterator().next();
        final Pipe pipe = Gremlin.compile("_().out('brother').name");
        pipe.setStarts(new SingleIterator<Vertex>(jupiterVertex));
        for(Object name : pipe) {
            Assert.assertTrue(("pluto".equals(name.toString())) || ("neptune".equals(name.toString())));
        }
    }
}
