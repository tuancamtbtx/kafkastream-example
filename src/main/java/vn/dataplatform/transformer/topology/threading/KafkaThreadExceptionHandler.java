package vn.dataplatform.transformer.topology.threading;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.dataplatform.transformer.constants.MainConfig;
import vn.dataplatform.transformer.entity.config.KafkaStreamConfig;

public class KafkaThreadExceptionHandler implements Thread.UncaughtExceptionHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaThreadExceptionHandler.class.getName());

    private final int kafkaStreamNumThread;
    private int kafkaExceptionCnt;

    @Inject
    public KafkaThreadExceptionHandler(KafkaStreamConfig kafkaStreamConfig) {
        kafkaExceptionCnt = 0;
        kafkaStreamNumThread = Integer.parseInt(kafkaStreamConfig.getStreamProperties().get(MainConfig.NUM_STREAM_THREADS_CONFIG).toString());
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        kafkaExceptionCnt++;
        if (kafkaExceptionCnt >= kafkaStreamNumThread) {
            LOGGER.error("All stream threads were killed, should exit app so app can be restart by supervisor", e);
            System.exit(1);
        }
    }
}
