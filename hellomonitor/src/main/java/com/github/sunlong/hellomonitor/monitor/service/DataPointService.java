package com.github.sunlong.hellomonitor.monitor.service;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.dao.IDataPointDao;
import com.github.sunlong.hellomonitor.monitor.model.DataPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * User: sunlong
 * Date: 13-5-8
 * Time: 下午2:22
 */
@Service
@Transactional(readOnly = true)
public class DataPointService {
    @Resource
    private IDataPointDao dataPointDao;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Integer id) throws AppException {
        DataPoint graphPoint = find(id);
        dataPointDao.delete(graphPoint);
    }

    public DataPoint find(Integer id) throws AppException {
        DataPoint dataPoint = dataPointDao.findOne(id);
        if(dataPoint == null){
            throw new AppException(MessageCode.DATA_POINT_NOT_EXIST_ERROR, id);
        }
        return dataPoint;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(DataPoint dataPoint) throws AppException {
        DataPoint toUpdate = find(dataPoint.getId());
        DataPoint dataPointByName = dataPointDao.findByName(dataPoint.getName());
        if(dataPointByName != null && dataPointByName != toUpdate){
            throw new AppException(MessageCode.DATA_POINT_EXIST_ERROR, dataPoint.getName());
        }

        toUpdate.setName(dataPoint.getName());
        toUpdate.setType(dataPoint.getType());

        dataPointDao.save(toUpdate);
    }
}
