package com.syncleus.grail.graph.totorom;

import org.jglue.totorom.FramedEdge;

public class Knows extends FramedEdge {

    public void setYears(int years) {
        setProperty("years", years);
    }

    public int getYears() {
        return getProperty("years");
    }
}