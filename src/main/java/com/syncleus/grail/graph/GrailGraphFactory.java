package com.syncleus.grail.graph;

import com.syncleus.grail.graph.action.*;
import com.syncleus.grail.neural.ActivationNeuron;
import com.syncleus.grail.neural.backprop.*;
import com.tinkerpop.frames.FramedGraphFactory;
import com.tinkerpop.frames.modules.Module;
import com.tinkerpop.frames.modules.gremlingroovy.GremlinGroovyModule;
import com.tinkerpop.frames.modules.javahandler.*;
import com.tinkerpop.frames.modules.typedgraph.*;

import java.util.*;

public class GrailGraphFactory extends FramedGraphFactory {
    private static final Set<Class<?>> BUILT_IN_TYPES = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{
                                                                          SignalMultiplyingEdge.class,
                                                                          BackpropNeuron.class,
                                                                          BackpropSynapse.class,
                                                                          PrioritySerialTrigger.class,
                                                                          ActionTriggerEdge.class,
                                                                          PrioritySerialTriggerEdge.class,
                                                                          ActivationNeuron.class}));

    public GrailGraphFactory() {
        super(GrailGraphFactory.constructModules(Collections.<Module>emptySet()));
    }

    public GrailGraphFactory(final Collection<? extends Module> modules) {
        super(GrailGraphFactory.constructModules(modules));
    }

    public GrailGraphFactory(final Collection<? extends Module> modules, final Collection<? extends Class<?>> types) {
        super(GrailGraphFactory.constructModules(modules, types));
    }

    private static Module constructTypedModule(final Collection<? extends Class<?>> types) {
        final TypedGraphModuleBuilder typedModuleBuilder = new TypedGraphModuleBuilder();
        for(final Class<?> type : types )
            typedModuleBuilder.withClass(type);

        return typedModuleBuilder.build();
    }

    private static Module[] constructModules(final Collection<? extends Module> modules) {
        return GrailGraphFactory.combineModules(modules, new GrailModule(GrailGraphFactory.BUILT_IN_TYPES), GrailGraphFactory.constructTypedModule(GrailGraphFactory.BUILT_IN_TYPES), new GremlinGroovyModule(), GrailGraphFactory.constructHandlerModule());
    }

    private static Module[] constructModules(final Collection<? extends Module> modules, final Collection<? extends Class<?>> additionalTypes) {
        final Set<Class<?>> types = new HashSet<Class<?>>(GrailGraphFactory.BUILT_IN_TYPES);
        types.addAll(additionalTypes);
        return GrailGraphFactory.combineModules(modules, new GrailModule(types), GrailGraphFactory.constructTypedModule(types), new GremlinGroovyModule(), GrailGraphFactory.constructHandlerModule());
    }

    private static JavaHandlerModule constructHandlerModule() {
        final JavaHandlerModule module = new JavaHandlerModule();
        return module;
    }

    private static Module[] combineModules(final Collection<? extends Module> modules, Module... additionalModules) {
        final Module[] allModules = Arrays.copyOf(additionalModules, modules.size() + additionalModules.length);
        int moduleIndex = additionalModules.length;
        for(final Module module : modules ) {
            allModules[moduleIndex] = module;
            moduleIndex++;
        }
        return allModules;
    }
}
