package vn.dataplatform.transformer.dao;

import com.google.inject.Inject;
import in.zapr.druid.druidry.client.DruidClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.dataplatform.transformer.common.connector.druid.impl.DruidConnector;
import vn.dataplatform.transformer.entity.log.StationLogEntity;

import java.util.List;

public class StationLogDao implements IBaseDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationLogDao.class.getName());

    public final DruidConnector druidConnector;

    @Inject
    public StationLogDao(DruidConnector druidConnector) {
        this.druidConnector = druidConnector;
    }

    @Override
    public void manyInsert(List<StationLogEntity> data) {
        LOGGER.info("insert data: {}", data);
        DruidClient client = druidConnector.getClient();
//        client.
    }
}
