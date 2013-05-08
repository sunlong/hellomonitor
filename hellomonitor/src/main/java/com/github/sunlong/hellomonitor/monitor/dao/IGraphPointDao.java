package com.github.sunlong.hellomonitor.monitor.dao;

import com.github.sunlong.hellomonitor.monitor.model.GraphPoint;
import org.springframework.data.repository.CrudRepository;

/**
 * User: sunlong
 * Date: 13-5-8
 * Time: 上午11:53
 */
public interface IGraphPointDao extends CrudRepository<GraphPoint, Integer> {
}
