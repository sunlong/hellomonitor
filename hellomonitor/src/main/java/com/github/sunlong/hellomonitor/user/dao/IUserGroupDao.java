package com.github.sunlong.hellomonitor.user.dao;

import com.github.sunlong.hellomonitor.user.model.UserGroup;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * User: sunlong
 * Date: 13-2-5
 * Time: 上午9:03
 */
public interface IUserGroupDao extends CrudRepository<UserGroup, Integer>, JpaSpecificationExecutor<UserGroup> {
    UserGroup findByName(String name);
}
