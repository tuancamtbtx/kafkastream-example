package vn.dataplatform.transformer.dao;

import vn.dataplatform.transformer.entity.log.StationLogEntity;

import java.util.List;

public interface IBaseDao {
    void manyInsert(List<StationLogEntity> data);
}
