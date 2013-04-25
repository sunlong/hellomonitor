package com.github.sunlong.hellomonitor.monitor.dao;

import com.github.sunlong.hellomonitor.monitor.model.DeviceClass;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * User: sunlong
 * Date: 13-4-24
 * Time: 上午9:32
 */
public interface IDeviceClassDao extends CrudRepository<DeviceClass, Integer>, JpaSpecificationExecutor<DeviceClass> {
    DeviceClass findByName(String name);
}
