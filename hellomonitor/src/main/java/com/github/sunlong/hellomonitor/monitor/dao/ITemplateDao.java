package com.github.sunlong.hellomonitor.monitor.dao;

import com.github.sunlong.hellomonitor.monitor.model.Template;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * User: sunlong
 * Date: 13-4-25
 * Time: 上午10:04
 */
public interface ITemplateDao extends CrudRepository<Template, Integer>, JpaSpecificationExecutor<Template> {
    Template findByName(String name);
}
