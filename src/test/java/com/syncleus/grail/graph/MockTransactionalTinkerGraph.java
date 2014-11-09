package com.syncleus.grail.graph;

import com.tinkerpop.blueprints.*;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import org.apache.commons.configuration.Configuration;

public class MockTransactionalTinkerGraph extends TinkerGraph implements TransactionalGraph {

    public MockTransactionalTinkerGraph(final Configuration configuration) {
        super(configuration);
    }

    public MockTransactionalTinkerGraph(final String directory, final FileType fileType) {
        super(directory, fileType);
    }

    public MockTransactionalTinkerGraph(final String directory) {
        super(directory);
    }

    public MockTransactionalTinkerGraph() {
    }

    @Override
    public void stopTransaction(final Conclusion conclusion) {
    }

    @Override
    public void commit() {
    }

    @Override
    public void rollback() {
    }
}
