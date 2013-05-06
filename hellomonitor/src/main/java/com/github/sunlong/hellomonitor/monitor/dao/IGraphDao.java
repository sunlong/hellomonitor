package com.github.sunlong.hellomonitor.monitor.dao;

import com.github.sunlong.hellomonitor.monitor.model.Graph;
import org.springframework.data.repository.CrudRepository;

/**
 * User: sunlong
 * Date: 13-5-6
 * Time: 下午12:39
 */
public interface IGraphDao extends CrudRepository<Graph, Integer> {
}
