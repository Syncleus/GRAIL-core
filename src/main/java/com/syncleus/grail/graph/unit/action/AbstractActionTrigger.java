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
package com.syncleus.grail.graph.unit.action;

import com.syncleus.grail.graph.AbstractGrailVertexFrame;

import java.lang.reflect.Method;
import java.util.*;

/**
 * This is the Java handler class associated with the ActionTrigger type. It doesnt do much, as it is expected to be
 * extended. It does however construct a reflection cache of Action annotations on previously seen classes.
 *
 * @since 0.1
 */
public abstract class AbstractActionTrigger extends AbstractGrailVertexFrame implements ActionTrigger {
    private static final Map<Class<?>, Map<String, Set<Method>>> ACTION_METHOD_CACHE = new HashMap<>();

    protected static final Map<String, Set<Method>> populateCache(final Class<?> parentClass) {
        Map<String, Set<Method>> actionMethods = AbstractActionTrigger.ACTION_METHOD_CACHE.get(parentClass);
        if( actionMethods != null )
            return actionMethods;
        actionMethods = new HashMap<>();
        AbstractActionTrigger.ACTION_METHOD_CACHE.put(parentClass, actionMethods);

        recursivePopulateCache(parentClass, actionMethods);

        return Collections.unmodifiableMap(actionMethods);
    }

    private static void recursivePopulateCache(final Class<?> clazz, final Map<String, Set<Method>> actionMethods) {
        if(clazz == null)
            return;

        cacheClass(clazz, actionMethods);
        for(final Class<?> triggerInterface : clazz.getInterfaces() )
            cacheClass(triggerInterface, actionMethods);

        recursivePopulateCache(clazz.getSuperclass(), actionMethods);
    }

    private static void cacheClass(final Class<?> triggerClass, final Map<String, Set<Method>> actionMethods) {
        final Method[] triggerMethods = triggerClass.getMethods();
        for (final Method triggerMethod : triggerMethods) {
            final Action actionAnnotation = triggerMethod.getDeclaredAnnotation(Action.class);
            if (actionAnnotation != null ) {
                if (triggerMethod.getParameterCount() > 0)
                    throw new IllegalStateException("A method annotated with @Action required parameters");

                Set<Method> methods = actionMethods.get(actionAnnotation.value());
                if( methods == null ) {
                    methods = new HashSet<Method>();
                    actionMethods.put(actionAnnotation.value(), methods);
                }

                methods.add(triggerMethod);
            }
        }
    }
}
