package com.github.sunlong.hellomonitor.monitor.service;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.dao.IDataSourceDao;
import com.github.sunlong.hellomonitor.monitor.model.*;
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

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void createDataPoint(DataPoint dataPoint) throws AppException {
        DataSource dataSource = find(dataPoint.getDataSource().getId());
        dataSource.getDataPoints().add(dataPoint);
        dataSourceDao.save(dataSource);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Integer id) throws AppException {
        DataSource dataSource = find(id);
        dataSourceDao.delete(dataSource);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(DataSource dataSource) throws AppException {
        DataSource toUpdate = find(dataSource.getId());

        DataSource dataSourceByName = dataSourceDao.findByName(dataSource.getName());
        if(dataSourceByName != null && dataSourceByName != toUpdate){
            throw new AppException(MessageCode.DATA_SOURCE_EXIST_ERROR, dataSource.getName());
        }

        toUpdate.copy(dataSource);

        dataSourceDao.save(toUpdate);
    }
}
