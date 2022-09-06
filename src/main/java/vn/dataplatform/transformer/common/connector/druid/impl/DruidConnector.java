package vn.dataplatform.transformer.common.connector.druid.impl;

import com.google.inject.Inject;
import in.zapr.druid.druidry.client.DruidClient;
import in.zapr.druid.druidry.client.DruidConfiguration;
import in.zapr.druid.druidry.client.DruidJerseyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.dataplatform.transformer.common.connector.druid.IDruidConnector;

public class DruidConnector implements IDruidConnector {
    private static final Logger LOGGER = LoggerFactory.getLogger(DruidConnector.class.getName());

    private final DruidConfiguration druidConfiguration;

    @Inject
    public DruidConnector(DruidConfiguration druidConfiguration) {
        this.druidConfiguration = druidConfiguration;
    }

    @Override
    public DruidConfiguration configurationBuilder() {
        LOGGER.info("Druid Configuration: {}", this.druidConfiguration);
        return this.druidConfiguration;
    }

    @Override
    public DruidClient getClient() {
        try {
            LOGGER.info("Get client Druid by config {}", this.druidConfiguration);
            return new DruidJerseyClient(druidConfiguration);
        } catch (Exception ex) {
            LOGGER.error("get client error, error message: {}", ex.getMessage(), ex);
            return null;
        }
    }
}
