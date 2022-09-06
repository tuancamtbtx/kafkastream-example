package vn.dataplatform.transformer.common.connector.druid;

import in.zapr.druid.druidry.client.DruidClient;
import in.zapr.druid.druidry.client.DruidConfiguration;

public interface IDruidConnector {
    DruidConfiguration configurationBuilder();

    DruidClient getClient();
}
