package vn.dataplatform.transformer.common.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import in.zapr.druid.druidry.client.DruidConfiguration;
import vn.dataplatform.transformer.constants.MainConfig;
import vn.dataplatform.transformer.entity.config.DruidIndexConfig;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    @Provides
    DruidConfiguration druidConfiguration() {
        return DruidConfiguration.builder()
                .host(MainConfig.DRUID_HOST)
                .port(8082).build();
    }
    @Provides
    DruidIndexConfig druidIndexConfig() {
        return new DruidIndexConfig();
    }

}
