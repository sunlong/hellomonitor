package com.github.sunlong.hellomonitor.monitor.dao;

import com.github.sunlong.hellomonitor.monitor.model.Device;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * User: sunlong
 * Date: 13-4-23
 * Time: 上午11:08
 */
public interface IDeviceDao extends CrudRepository<Device, Integer>, JpaSpecificationExecutor<Device> {
    Device findByIp(String ip);

    Device findByName(String name);
}
