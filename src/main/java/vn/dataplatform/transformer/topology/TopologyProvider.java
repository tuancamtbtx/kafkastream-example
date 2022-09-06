package vn.dataplatform.transformer.topology;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import vn.dataplatform.transformer.constants.MainConfig;
import vn.dataplatform.transformer.constants.StateStoreConstants;
import vn.dataplatform.transformer.topology.processor.IndexingSnapshotProcessorFactory;
import vn.dataplatform.transformer.topology.transform.StationLogMapper;

public class TopologyProvider implements Provider<Topology> {
    private final IndexingSnapshotProcessorFactory indexingSnapshotProcessorFactory;

    @Inject
    public TopologyProvider(IndexingSnapshotProcessorFactory indexingSnapshotProcessorFactory) {
        this.indexingSnapshotProcessorFactory = indexingSnapshotProcessorFactory;
    }

    @Override
    public Topology get() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        streamsBuilder.addStateStore(indexingChangelogStoreBuilder());
        KStream<String, String> aqiKStream = getAQILogKStream(streamsBuilder);
        aqiKStream.process(indexingSnapshotProcessorFactory::get,
                StateStoreConstants.SENSOR_SEND_LOG_STATE);
        return streamsBuilder.build();
    }

    private StoreBuilder<KeyValueStore<String, String>> indexingChangelogStoreBuilder() {
        return Stores.keyValueStoreBuilder(
                Stores.persistentKeyValueStore(StateStoreConstants.SENSOR_SEND_LOG_STATE),
                Serdes.String(), Serdes.String());
    }

    private KStream<String, String> getAQILogKStream(StreamsBuilder streamsBuilder) {
        return streamsBuilder.stream(MainConfig.KAFKA_TOPIC_AQI_LOG, Consumed.with(Serdes.String(), Serdes.String()))
                .filter((keyId, log) -> log != null)
                .mapValues(new StationLogMapper());

    }
}
