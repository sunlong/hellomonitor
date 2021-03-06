package com.github.sunlong.hellomonitor.monitor.service;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.dao.IGraphPointDao;
import com.github.sunlong.hellomonitor.monitor.model.GraphPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * User: sunlong
 * Date: 13-5-8
 * Time: 上午11:53
 */
@Service
@Transactional(readOnly = true)
public class GraphPointService {
    @Resource
    private IGraphPointDao graphPointDao;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Integer id) throws AppException {
        GraphPoint graphPoint = find(id);
        graphPointDao.delete(graphPoint);
    }

    public GraphPoint find(Integer id) throws AppException {
        GraphPoint graphPoint = graphPointDao.findOne(id);
        if(graphPoint == null){
            throw new AppException(MessageCode.GRAPH_POINT_NOT_EXIST_ERROR, id);
        }
        return graphPoint;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(GraphPoint graphPoint) throws AppException {
        GraphPoint toUpdate = find(graphPoint.getId());
        GraphPoint graphPointByName = graphPointDao.findByName(graphPoint.getName());
        if(graphPointByName != null && graphPointByName!=toUpdate){
            throw new AppException(MessageCode.GRAPH_POINT_EXIST_ERROR, graphPoint.getName());
        }

        toUpdate.setName(graphPoint.getName());
        toUpdate.setStacked(graphPoint.getStacked());
        toUpdate.setLineWidth(graphPoint.getLineWidth());
        toUpdate.setColor(graphPoint.getColor());
        toUpdate.setType(graphPoint.getType());

        graphPointDao.save(toUpdate);
    }
}
