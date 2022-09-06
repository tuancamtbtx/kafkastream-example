package vn.dataplatform.transformer.topology.processor;

import com.google.inject.Inject;
import org.apache.kafka.streams.processor.Cancellable;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.dataplatform.transformer.common.utils.XRateLimitUtils;
import vn.dataplatform.transformer.constants.StateStoreConstants;
import vn.dataplatform.transformer.dao.IBaseDao;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IndexingSnapshotProcessor implements Processor<String, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexingSnapshotProcessor.class.getName());
    private KeyValueStore<String, String> suppressSnapshotStore;

    private int changelogCnt;
    private Cancellable punctuateCancellable;
    private int lastFlushSize;
    private long lastFlushTs;
    private final IBaseDao stationDao;

    @Inject
    public IndexingSnapshotProcessor(IBaseDao stationDao) {
        this.stationDao = stationDao;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void init(ProcessorContext context) {
        changelogCnt = 0;
        suppressSnapshotStore = (KeyValueStore<String, String>)
                context.getStateStore(StateStoreConstants.SENSOR_SEND_LOG_STATE);
        punctuateCancellable = context.schedule(Duration.ofSeconds(90), PunctuationType.WALL_CLOCK_TIME, timestamp -> flushAllSuppressBulkSnapshot("TTL"));
        lastFlushTs = 0;
        lastFlushSize = 0;
        context.commit();
    }

    @Override
    public void process(String key, String value) {
        suppressSnapshotIndex(key,value);
    }

    @Override
    public void close() {
        punctuateCancellable.cancel();
    }

    private void suppressSnapshotIndex(String keyRaw, String newChangelog) {
        suppressSnapshotStore.put(keyRaw, newChangelog);
        changelogCnt++;
        if (changelogCnt >= 10) {
            stationDao.manyInsert(Collections.emptyList());
            LOGGER.info("full data {}", 10);
        }
    }
    private void flushAllSuppressBulkSnapshot(String type) {
        LOGGER.info("streaming type job:  {}", type);
        KeyValueIterator<String, String> iterator = suppressSnapshotStore.all();
        Map<String, String> changelogMap = new HashMap<>();
        while (iterator.hasNext()) {
            String key = iterator.peekNextKey();
            String value = getChangelogFromKey(key);
            if (value == null) {
                suppressSnapshotStore.delete(key);
                iterator.next();
                continue;
            }
            changelogMap.put(key, value);
            if (changelogMap.size() >= 10) {
                shouldSleepThenBulkIndex(changelogMap);
                changelogMap = new HashMap<>();
            }
            iterator.next();
        }
        iterator.close();
        if (changelogMap.size() > 0) {
            shouldSleepThenBulkIndex(changelogMap);
        }
        changelogCnt = 0;
    }
    /**
     * Get product payload changelog from local state
     *
     * @param productId product id
     * @return product changelog
     */
    private String getChangelogFromKey(String productId) {
        String snapshotProduct = suppressSnapshotStore.get(productId);
        if (snapshotProduct == null) {
            LOGGER.warn("null snapshot {}", productId);
            return null;
        }
        return snapshotProduct;
    }

    private void shouldSleepThenBulkIndex(Map<String, String> changelogMap) {
        shouldSleepBeforeFlush();
        lastFlushSize = changelogMap.size();
        lastFlushTs = System.currentTimeMillis();
//        sinkConnector.load(changelogMap,"flash_deals");
        deleteProcessedChangelogFromStateStore(changelogMap.keySet());
    }

    private void shouldSleepBeforeFlush() {
        long sleepTime = XRateLimitUtils.getShouldSleepTimeBeforeIndex(System.currentTimeMillis(), lastFlushTs, lastFlushSize, 10);
        LOGGER.info("sleep time {}", sleepTime);
        if (sleepTime > 50) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                LOGGER.error("Error sleeping before index", e);
                Thread.currentThread().interrupt();
            }
        }
    }
    /**
     * Delete processed changelog from state store for at least once guarantee
     *
     * @param deletingKeySet list keys waits for deleting
     */
    private void deleteProcessedChangelogFromStateStore(Set<String> deletingKeySet) {
        LOGGER.info("Remove list key: " + deletingKeySet);
        deletingKeySet.forEach(key -> suppressSnapshotStore.delete(key));
    }

}
