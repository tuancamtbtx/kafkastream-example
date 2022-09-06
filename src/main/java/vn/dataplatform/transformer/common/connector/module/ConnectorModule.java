package vn.dataplatform.transformer.common.connector.module;

import com.google.inject.AbstractModule;
import vn.dataplatform.transformer.common.connector.druid.IDruidConnector;
import vn.dataplatform.transformer.common.connector.druid.impl.DruidConnector;

public class ConnectorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IDruidConnector.class).to(DruidConnector.class);
    }

}
