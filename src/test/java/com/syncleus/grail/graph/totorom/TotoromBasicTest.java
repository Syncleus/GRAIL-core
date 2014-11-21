package com.syncleus.grail.graph.totorom;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassLoadingStrategy;
import net.bytebuddy.instrumentation.FixedValue;
import net.bytebuddy.instrumentation.method.matcher.MethodMatchers;
import org.jglue.totorom.FrameFactory;
import org.jglue.totorom.FramedGraph;
import org.jglue.totorom.TypeResolver;
import org.junit.Assert;
import org.junit.Test;

public class TotoromBasicTest {
    @Test
    public void testBasic() {

        Graph g = new TinkerGraph();
        FramedGraph fg = new FramedGraph(g);
        Person p1 = fg.addVertex(Person.class);
        p1.setName("Bryn");

        Person p2 = fg.addVertex(Person.class);
        p2.setName("Julia");
        Knows knows = p1.addKnows(p2);
        knows.setYears(15);

        Person bryn = fg.V().has("name", "Bryn").next(Person.class);


        Assert.assertEquals("Bryn", bryn.getName());
        Assert.assertEquals(15, bryn.getKnowsList().get(0).getYears());
    }

    @Test
    public void testJavaTyping() {
        Graph g = new TinkerGraph();
        FramedGraph fg = new FramedGraph(g, FrameFactory.Default, TypeResolver.Java);//Java type resolver
        //Also note FrameFactory.Default. Other options are CDI and Spring.

        Person p1 = fg.addVertex(Programmer.class);
        p1.setName("Bryn");

        Person p2 = fg.addVertex(Person.class);
        p2.setName("Julia");

        Person bryn = fg.V().has("name", "Bryn").next(Person.class);
        Person julia = fg.V().has("name", "Julia").next(Person.class);

        Assert.assertEquals(Programmer.class, bryn.getClass());
        Assert.assertEquals(Person.class, julia.getClass());
    }


    @Test
    public void testJavaTypingBytebuddy() throws InstantiationException, IllegalAccessException{
        Graph g = new TinkerGraph();
        FramedGraph fg = new FramedGraph(g, FrameFactory.Default, TypeResolver.Java);//Java type resolver
        //Also note FrameFactory.Default. Other options are CDI and Spring.

        Class<? extends Programmer> newProgrammerType = new ByteBuddy()
                .subclass(Programmer.class)
                .method(MethodMatchers.named("toString")).intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        Person p1 = fg.addVertex(newProgrammerType);
        p1.setName("Bryn");

        Assert.assertEquals(newProgrammerType, p1.getClass());
        Assert.assertEquals("Hello World!", p1.toString());
    }
}
