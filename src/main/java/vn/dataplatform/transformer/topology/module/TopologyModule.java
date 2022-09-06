package vn.dataplatform.transformer.topology.module;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.apache.kafka.streams.Topology;
import vn.dataplatform.transformer.topology.TopologyProvider;
import vn.dataplatform.transformer.topology.processor.IndexingSnapshotProcessor;
import vn.dataplatform.transformer.topology.processor.IndexingSnapshotProcessorFactory;

public class TopologyModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Topology.class).toProvider(TopologyProvider.class);
        install(new FactoryModuleBuilder()
                .implement(IndexingSnapshotProcessor.class, IndexingSnapshotProcessor.class)
                .build(IndexingSnapshotProcessorFactory.class));
    }
}
