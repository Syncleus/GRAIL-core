package com.syncleus.grail.graph.totorom;

import org.jglue.totorom.FramedVertex;

import java.util.List;

public class Person extends FramedVertex {

    public String getName() {
        return getProperty("name");
    }

    public void setName(String name) {
        setProperty("name", name); //Properties are simple method calls
    }

    public List<Knows> getKnowsList() {
        return outE("knows").toList(Knows.class); //Gremlin natively supported
    }

    public List<Person> getFriendsOfFriends() {
        return out("knows").out("knows").except(this).toList(Person.class); //Gremlin natively supported
    }

    public Knows addKnows(Person friend) {
        return addEdge("knows", friend, Knows.class); //Elements are automatically unwrapped
    }
}