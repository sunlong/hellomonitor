package com.github.sunlong.hellomonitor.monitor.service;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.dao.IGraphDao;
import com.github.sunlong.hellomonitor.monitor.model.Graph;
import com.github.sunlong.hellomonitor.monitor.model.GraphPoint;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * User: sunlong
 * Date: 13-5-6
 * Time: 上午11:51
 */
@Service
@Transactional(readOnly = true)
public class GraphService {
    @Resource
    private IGraphDao graphDao;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void createGraphPoint(GraphPoint graphPoint) throws AppException {
        Graph graph = find(graphPoint.getGraph().getId());
        graph.getGraphPoints().add(graphPoint);
        graphDao.save(graph);
    }

    public Graph find(Integer id) throws AppException {
        Graph graph = graphDao.findOne(id);
        if(graph == null){
            throw new AppException(MessageCode.GRAPH_NOT_EXIST_ERROR, id);
        }
        Hibernate.initialize(graph.getGraphPoints());
        return graph;
    }
}
