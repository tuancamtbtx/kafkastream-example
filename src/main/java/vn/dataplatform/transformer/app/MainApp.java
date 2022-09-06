package vn.dataplatform.transformer.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.dataplatform.transformer.common.connector.module.ConnectorModule;
import vn.dataplatform.transformer.common.kafka.KafkaStreamStarter;
import vn.dataplatform.transformer.common.module.ConfigModule;
import vn.dataplatform.transformer.common.module.KafkaStreamModule;
import vn.dataplatform.transformer.dao.module.DaoModule;
import vn.dataplatform.transformer.server.HServer;
import vn.dataplatform.transformer.topology.module.TopologyModule;

public class MainApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class.getName());

    public static void main(String[] args) {
        try {
            HServer.start();
            Injector injector = Guice.createInjector(
                    new KafkaStreamModule(),
                    new ConfigModule(),
                    new TopologyModule(),
                    new ConnectorModule(),
                    new DaoModule()
            );
            KafkaStreamStarter kafkaStreamStarter = injector.getInstance(KafkaStreamStarter.class);
            kafkaStreamStarter.start();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

    }
}
