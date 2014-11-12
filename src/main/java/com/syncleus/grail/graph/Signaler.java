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
package com.syncleus.grail.graph;

import com.tinkerpop.frames.Property;

/**
 * A signaler is any graph object which has a signal. A signal is just a double value that varies with time and is
 * represented as a property of the graph object.
 *
 * @since 0.1
 */
public interface Signaler {
    /**
     * Get the signal property of the graph object.
     * @return the signal.
     */
    @Property("signal")
    Double getSignal();

    /**
     * Set the signal property of the graph object.
     * @param signal the signal.
     */
    @Property("signal")
    void setSignal(double signal);
}
