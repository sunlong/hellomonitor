package com.github.sunlong.hellomonitor.monitor.service;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.dao.IDataSourceDao;
import com.github.sunlong.hellomonitor.monitor.model.DataPoint;
import com.github.sunlong.hellomonitor.monitor.model.DataSource;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * User: sunlong
 * Date: 13-4-26
 * Time: 下午3:18
 */
@Service
@Transactional(readOnly = true)
public class DataSourceService {
    @Resource
    private IDataSourceDao dataSourceDao;

    public DataSource find(Integer id) throws AppException {
        DataSource dataSource = dataSourceDao.findOne(id);
        if(dataSource == null){
            throw new AppException(MessageCode.DATA_SOURCE_NOT_EXIST_ERROR, id);
        }
        Hibernate.initialize(dataSource.getDataPoints());
        return dataSource;
    }

    public void createDataPoint(DataPoint dataPoint) throws AppException {
        DataSource dataSource = find(dataPoint.getDataSource().getId());
        dataSource.getDataPoints().add(dataPoint);
        dataSourceDao.save(dataSource);
    }
}
