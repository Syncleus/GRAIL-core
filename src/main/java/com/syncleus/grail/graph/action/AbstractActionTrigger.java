package com.syncleus.grail.graph.action;

import java.lang.reflect.Method;
import java.util.*;

public abstract class AbstractActionTrigger implements ActionTrigger {
    private final static Map<Class<?>, Map<String, Set<Method>>> ACTION_METHOD_CACHE = new HashMap<>();

    protected static final Map<String, Set<Method>> populateCache(final Class<?> parentClass) {
        Map<String, Set<Method>> actionMethods = AbstractActionTrigger.ACTION_METHOD_CACHE.get(parentClass);
        if( actionMethods != null )
            return actionMethods;
        actionMethods = new HashMap<String, Set<Method>>();
        AbstractActionTrigger.ACTION_METHOD_CACHE.put(parentClass, actionMethods);

        for(Class<?> triggerClass : parentClass.getInterfaces() ) {
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

        return Collections.unmodifiableMap(actionMethods);
    }
}
