package com.github.sunlong.hellomonitor.monitor.dao;

import com.github.sunlong.hellomonitor.monitor.model.DataSource;
import org.springframework.data.repository.CrudRepository;

/**
 * User: sunlong
 * Date: 13-4-26
 * Time: 下午3:18
 */
public interface IDataSourceDao extends CrudRepository<DataSource, Integer> {
    DataSource findByName(String name);
}
