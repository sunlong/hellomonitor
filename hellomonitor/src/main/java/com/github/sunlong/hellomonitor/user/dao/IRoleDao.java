package com.github.sunlong.hellomonitor.user.dao;

import com.github.sunlong.hellomonitor.user.model.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: sunlong
 * Date: 13-2-2
 * Time: 上午11:29
 */
public interface IRoleDao extends PagingAndSortingRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    Role findByName(String name);
}
