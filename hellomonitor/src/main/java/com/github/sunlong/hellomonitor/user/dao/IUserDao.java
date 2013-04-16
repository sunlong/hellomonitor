package com.github.sunlong.hellomonitor.user.dao;

import com.github.sunlong.hellomonitor.user.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: sunlong
 * Date: 13-1-30
 * Time: 下午5:17
 */
public interface IUserDao extends PagingAndSortingRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);
}
