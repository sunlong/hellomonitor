package com.github.sunlong.hellomonitor.user.dao;

import com.github.sunlong.hellomonitor.user.model.Action;
import org.springframework.data.repository.CrudRepository;

/**
 * User: sunlong
 * Date: 13-2-20
 * Time: 下午5:31
 */
public interface IActionDao extends CrudRepository<Action, Integer> {
}
