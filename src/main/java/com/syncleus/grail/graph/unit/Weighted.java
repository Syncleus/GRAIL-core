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
package com.syncleus.grail.graph.unit;

import com.syncleus.ferma.annotations.Property;

/**
 * A weighted graph object. This interface represents any object in a graph with a weight property. A weight is simply
 * a double value that may or may not change over time.
 *
 * @since 0.1
 */
public interface Weighted {
    /**
     * Get the weight property for this object.
     *
     * @return the weight property.
     * @since 0.1
     */
    @Property("weight")
    Double getWeight();

    /**
     * Set the weight property for this object.
     *
     * @param weight new weight to set.
     * @since 0.1
     */
    @Property("weight")
    void setWeight(double weight);
}
