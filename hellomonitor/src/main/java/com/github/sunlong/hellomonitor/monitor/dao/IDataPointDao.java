package com.github.sunlong.hellomonitor.monitor.dao;

import com.github.sunlong.hellomonitor.monitor.model.DataPoint;
import org.springframework.data.repository.CrudRepository;

/**
 * User: sunlong
 * Date: 13-5-8
 * Time: 下午2:22
 */
public interface IDataPointDao extends CrudRepository<DataPoint, Integer> {
    DataPoint findByName(String name);
}
