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

import com.tinkerpop.frames.modules.typedgraph.*;

final class ReflectionUtility {
    private ReflectionUtility() {
    }

    public static TypeValue determineTypeValue(final Class<?> type) {
        final TypeValue typeValue = type.getDeclaredAnnotation(TypeValue.class);
        if( typeValue == null )
            throw new IllegalArgumentException("The specified type does not have a TypeValue annotation");
        return typeValue;
    }

    public static TypeField determineTypeField(final Class<?> type) {
        TypeField typeField = type.getAnnotation(TypeField.class);
        if( typeField == null ) {
            final Class<?>[] parents = type.getInterfaces();
            for( final Class<?> parent : parents ) {
                typeField = determineTypeFieldRecursive(parent);
                if( typeField != null )
                    return typeField;
            }

            //we know typeField must still be null since we didnt return a value yet
            throw new IllegalArgumentException("The specified type does not have a parent with a typeField annotation.");
        }

        return typeField;
    }

    public static TypeField determineTypeFieldRecursive(final Class<?> type) {
        TypeField typeField = type.getAnnotation(TypeField.class);
        if( typeField == null ) {
            final Class<?>[] parents = type.getInterfaces();
            for( final Class<?> parent : parents ) {
                typeField = determineTypeFieldRecursive(parent);
                if( typeField != null )
                    return typeField;
            }
            return null;
        }
        return typeField;
    }
}
