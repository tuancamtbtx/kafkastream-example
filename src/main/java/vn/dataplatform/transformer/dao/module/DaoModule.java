package vn.dataplatform.transformer.dao.module;

import com.google.inject.AbstractModule;
import vn.dataplatform.transformer.dao.IBaseDao;
import vn.dataplatform.transformer.dao.StationLogDao;

public class DaoModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IBaseDao.class).to(StationLogDao.class);
    }
}
